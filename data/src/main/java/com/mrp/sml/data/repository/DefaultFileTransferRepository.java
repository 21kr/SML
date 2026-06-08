package com.mrp.sml.data.repository;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.model.FileMetadata;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.ResumeState;
import com.mrp.sml.domain.repository.TransferChunk;
import com.mrp.sml.domain.repository.TransferExecutionStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultFileTransferRepository implements FileTransferRepository {

    private static final int TRANSFER_PORT = 8988;
    private static final int HANDSHAKE_PORT = 8989;
    private static final int CHUNK_SIZE = 1048576;
    private static final int BUFFER_SIZE = 256 * 1024;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 350L;
    private static final int SOCKET_TIMEOUT_MS = 30000;
    private static final int AES_GCM_NONCE_LENGTH = 12;
    private static final int AES_GCM_TAG_LENGTH = 128;

    private static final byte TYPE_METADATA = 1;
    private static final byte TYPE_ACCEPT = 2;
    private static final byte TYPE_REJECT = 3;
    private static final byte TYPE_FILE_START = 4;
    private static final byte TYPE_CHUNK = 5;
    private static final byte TYPE_ACK = 6;
    private static final byte TYPE_FILE_DONE = 7;
    private static final byte TYPE_ALL_DONE = 8;
    private static final byte TYPE_RESUME_QUERY = 9;
    private static final byte TYPE_RESUME_RESPONSE = 10;

    private final DispatchersProvider dispatchersProvider;
    private final TransferHistoryRepository transferHistoryRepository;

    private final CopyOnWriteArrayList<TransferProgressListener> progressListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<TransferStatusListener> statusListeners = new CopyOnWriteArrayList<>();

    private volatile TransferRequest lastTransferRequest;
    private volatile boolean cancelled;
    private volatile Socket activeSocket;
    private volatile ServerSocket activeServerSocket;
    private volatile byte[] sessionKey;

    private final SecureRandom secureRandom = new SecureRandom();

    @Inject
    public DefaultFileTransferRepository(
            DispatchersProvider dispatchersProvider,
            TransferHistoryRepository transferHistoryRepository
    ) {
        this.dispatchersProvider = dispatchersProvider;
        this.transferHistoryRepository = transferHistoryRepository;
    }

    @Override
    public void observeTransferProgress(TransferProgressListener listener) {
        progressListeners.addIfAbsent(listener);
        listener.onProgressUpdated(new TransferProgress(0L, 0L, 0.0, 0f));
    }

    @Override
    public void removeTransferProgressObserver(TransferProgressListener listener) {
        progressListeners.remove(listener);
    }

    @Override
    public void observeTransferStatus(TransferStatusListener listener) {
        statusListeners.addIfAbsent(listener);
        listener.onStatusUpdated(new TransferStatusUpdate(TransferExecutionStatus.IDLE, "Idle"));
    }

    @Override
    public void removeTransferStatusObserver(TransferStatusListener listener) {
        statusListeners.remove(listener);
    }

    @Override
    public void sendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken) {
        cancelled = false;
        lastTransferRequest = TransferRequest.forSend(sourcePaths, destinationAddress, sessionToken);
        deriveSessionKey(sessionToken);
        dispatchersProvider.ioExecutor().execute(() -> runWithRetry(
                TransferExecutionStatus.SENDING,
                "Sending files",
                () -> performSendFiles(sourcePaths, destinationAddress),
                "Files sent"
        ));
    }

    @Override
    public void receiveFiles(String destinationDirectoryPath, String sessionToken) {
        cancelled = false;
        lastTransferRequest = TransferRequest.forReceive(destinationDirectoryPath, sessionToken);
        deriveSessionKey(sessionToken);
        dispatchersProvider.ioExecutor().execute(() -> runWithRetry(
                TransferExecutionStatus.RECEIVING,
                "Waiting for incoming files",
                () -> performReceiveFiles(destinationDirectoryPath),
                "Files received"
        ));
    }

    @Override
    public void cancelTransfer() {
        cancelled = true;
        closeActiveSockets();
        postStatus(new TransferStatusUpdate(TransferExecutionStatus.FAILED, "Transfer cancelled by user."));
    }

    @Override
    public void resumeLastTransfer() {
        TransferRequest request = lastTransferRequest;
        if (request == null) {
            postStatus(new TransferStatusUpdate(TransferExecutionStatus.FAILED, "No transfer to resume."));
            return;
        }
        if (request.type == TransferType.SEND) {
            sendFiles(request.sourcePaths, request.destinationAddress, request.sessionToken);
        } else {
            receiveFiles(request.destinationDirectoryPath, request.sessionToken);
        }
    }

    @Override
    public void sendMetadata(List<String> sourcePaths, String destinationAddress, String sessionToken) {
        deriveSessionKey(sessionToken);
        dispatchersProvider.ioExecutor().execute(() -> {
            try {
                performSendMetadata(sourcePaths, destinationAddress);
            } catch (IOException e) {
                postStatus(new TransferStatusUpdate(TransferExecutionStatus.FAILED, "Metadata send failed: " + e.getMessage()));
            }
        });
    }

    @Override
    public FileMetadata receiveMetadata(String sessionToken) {
        deriveSessionKey(sessionToken);
        try {
            return performReceiveMetadata();
        } catch (IOException e) {
            postStatus(new TransferStatusUpdate(TransferExecutionStatus.FAILED, "Metadata receive failed: " + e.getMessage()));
            return null;
        }
    }

    @Override
    public void acceptTransfer(String sessionToken) {
        dispatchersProvider.ioExecutor().execute(() -> {
            try {
                performAcceptReject(true, sessionToken);
            } catch (IOException ignored) {
            }
        });
    }

    @Override
    public void rejectTransfer(String sessionToken) {
        dispatchersProvider.ioExecutor().execute(() -> {
            try {
                performAcceptReject(false, sessionToken);
            } catch (IOException ignored) {
            }
        });
    }

    @Override
    public void sendChunk(String filePath, String destinationAddress, int chunkIndex, int chunkSize, String sessionToken) {
    }

    @Override
    public boolean receiveChunkAck(String sessionToken, int chunkIndex) {
        return false;
    }

    private void performSendFiles(List<String> sourcePaths, String destinationAddress) throws IOException {
        List<File> files = validateFiles(sourcePaths);

        try (ServerSocket serverSocket = new ServerSocket(TRANSFER_PORT)) {
            serverSocket.setReuseAddress(true);
            activeServerSocket = serverSocket;
            postStatus(new TransferStatusUpdate(TransferExecutionStatus.SENDING, "Waiting for receiver to connect..."));

            Socket socket = serverSocket.accept();
            socket.setSoTimeout(SOCKET_TIMEOUT_MS);
            activeSocket = socket;

            try (Socket ignored = socket;
                 DataOutputStream output = new DataOutputStream(
                         new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE));
                 DataInputStream input = new DataInputStream(
                         new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE))) {

                FileMetadata metadata = buildMetadata(files);
                sendProtocolData(output, input, metadata, files);

                for (TransferRecord record : saveTransferRecords(files, TransferDirection.SENT)) {
                    transferHistoryRepository.saveTransferRecord(record);
                }
            }
        } finally {
            activeServerSocket = null;
            activeSocket = null;
        }
    }

    private void performReceiveFiles(String destinationDirectoryPath) throws IOException {
        File destinationDirectory = validateDestinationDirectory(destinationDirectoryPath);

        String senderAddress = resolveSenderAddress();
        if (senderAddress == null) {
            throw new IOException("Cannot resolve sender address");
        }

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(senderAddress, TRANSFER_PORT), 10000);
        socket.setSoTimeout(SOCKET_TIMEOUT_MS);
        activeSocket = socket;

        try (Socket ignored = socket;
             DataInputStream input = new DataInputStream(
                     new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE));
             DataOutputStream output = new DataOutputStream(
                     new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE))) {

            receiveProtocolData(input, output, destinationDirectory);
        } finally {
            activeSocket = null;
        }
    }

    private void sendProtocolData(DataOutputStream output, DataInputStream input,
                                  FileMetadata metadata, List<File> files) throws IOException {
        long totalBytes = 0;
        List<Long> fileSizes = new ArrayList<>();
        for (File file : files) {
            fileSizes.add(file.length());
            totalBytes += file.length();
        }

        byte[] metadataBytes = metadata.toJson().getBytes(StandardCharsets.UTF_8);
        output.writeByte(TYPE_METADATA);
        output.writeInt(metadataBytes.length);
        output.write(metadataBytes);
        output.flush();

        byte response = input.readByte();
        if (response == TYPE_REJECT) {
            throw new IOException("Receiver rejected the transfer.");
        }
        if (response != TYPE_ACCEPT) {
            throw new IOException("Unexpected response from receiver.");
        }

        long transferred = 0L;
        long start = System.currentTimeMillis();

        for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
            File file = files.get(fileIndex);
            long fileSize = fileSizes.get(fileIndex);
            String sha256 = bytesToHex(computeSha256(file));

            throwIfCancelled();
            output.writeByte(TYPE_FILE_START);
            output.writeInt(fileIndex);
            output.writeUTF(file.getName());
            output.writeLong(fileSize);
            output.writeUTF(sha256);
            output.flush();

            int totalChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);
            try (FileInputStream fileInput = new FileInputStream(file)) {
                for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                    throwIfCancelled();
                    int currentChunkSize = (int) Math.min(CHUNK_SIZE, fileSize - (long) chunkIndex * CHUNK_SIZE);
                    boolean isLast = (chunkIndex == totalChunks - 1);

                    output.writeByte(TYPE_CHUNK);
                    output.writeInt(fileIndex);
                    output.writeInt(chunkIndex);
                    output.writeInt(currentChunkSize);
                    output.writeBoolean(isLast);

                    byte[] nonce = new byte[AES_GCM_NONCE_LENGTH];
                    secureRandom.nextBytes(nonce);
                    output.write(nonce);

                    try (javax.crypto.CipherOutputStream cipherOutput = createEncryptingStream(output, nonce)) {
                        byte[] buffer = new byte[Math.min(BUFFER_SIZE, currentChunkSize)];
                        int remaining = currentChunkSize;
                        while (remaining > 0) {
                            int toRead = Math.min(buffer.length, remaining);
                            int read = fileInput.read(buffer, 0, toRead);
                            if (read < 0) break;
                            cipherOutput.write(buffer, 0, read);
                            remaining -= read;
                        }
                        cipherOutput.flush();
                    }

                    output.flush();
                    transferred += currentChunkSize;
                    postProgress(progressFromBytes(transferred, totalBytes, start));

                    byte ackType = input.readByte();
                    int ackChunkIndex = input.readInt();
                    if (ackType != TYPE_ACK || ackChunkIndex != chunkIndex) {
                        throw new IOException("ACK mismatch at chunk " + chunkIndex);
                    }
                }
            }

            output.writeByte(TYPE_FILE_DONE);
            output.writeInt(fileIndex);
            output.flush();
        }

        output.writeByte(TYPE_ALL_DONE);
        output.flush();
    }

    private void receiveProtocolData(DataInputStream input, DataOutputStream output,
                                     File destinationDirectory) throws IOException {
        byte msgType = input.readByte();
        if (msgType != TYPE_METADATA) {
            throw new IOException("Expected metadata, got type: " + msgType);
        }
        int metaLength = input.readInt();
        byte[] metaBytes = new byte[metaLength];
        input.readFully(metaBytes);
        String metaJson = new String(metaBytes, StandardCharsets.UTF_8);
        FileMetadata metadata = FileMetadata.fromJson(metaJson);

        List<FileMetadata.FileEntry> entries = metadata.getFiles();
        long totalBytes = 0;
        for (FileMetadata.FileEntry entry : entries) {
            totalBytes += entry.getSize();
        }

        output.writeByte(TYPE_ACCEPT);
        output.flush();

        long transferred = 0L;
        long start = System.currentTimeMillis();
        List<File> receivedFiles = new ArrayList<>();

        for (int fileIndex = 0; fileIndex < entries.size(); fileIndex++) {
            FileMetadata.FileEntry entry = entries.get(fileIndex);

            byte fileStartType = input.readByte();
            if (fileStartType != TYPE_FILE_START) {
                throw new IOException("Expected FILE_START, got type: " + fileStartType);
            }
            int idx = input.readInt();
            String fileName = input.readUTF();
            long fileSize = input.readLong();
            String expectedSha256 = input.readUTF();

            File outputFile = new File(destinationDirectory, sanitizeFileName(fileName));
            int totalChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);
            MessageDigest sha256Digest;
            try {
                sha256Digest = MessageDigest.getInstance("SHA-256");
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new IOException("SHA-256 not available", e);
            }

            try (FileOutputStream fileOutput = new FileOutputStream(outputFile)) {
                for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                    throwIfCancelled();

                    byte chunkType = input.readByte();
                    if (chunkType != TYPE_CHUNK) {
                        throw new IOException("Expected CHUNK, got type: " + chunkType);
                    }
                    int fIdx = input.readInt();
                    int cIdx = input.readInt();
                    int chunkSize = input.readInt();
                    boolean isLast = input.readBoolean();

                    byte[] nonce = new byte[AES_GCM_NONCE_LENGTH];
                    input.readFully(nonce);

                    Cipher cipher = createDecryptingCipher(nonce);
                    byte[] encryptedChunk = new byte[chunkSize + AES_GCM_TAG_LENGTH / 8];
                    int totalRead = 0;
                    while (totalRead < encryptedChunk.length) {
                        int read = input.read(encryptedChunk, totalRead, encryptedChunk.length - totalRead);
                        if (read < 0) break;
                        totalRead += read;
                    }

                    byte[] decrypted;
                    try {
                        decrypted = cipher.doFinal(encryptedChunk);
                    } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
                        throw new IOException("Decryption failed for chunk " + cIdx, e);
                    }
                    fileOutput.write(decrypted);
                    sha256Digest.update(decrypted);

                    transferred += chunkSize;
                    postProgress(progressFromBytes(transferred, totalBytes, start));

                    output.writeByte(TYPE_ACK);
                    output.writeInt(cIdx);
                    output.flush();
                }
                fileOutput.flush();
            }

            String actualSha256 = bytesToHex(sha256Digest.digest());
            if (!expectedSha256.equals(actualSha256)) {
                if (!outputFile.delete()) {
                }
                throw new IOException("SHA-256 mismatch for " + fileName);
            }

            byte fileDoneType = input.readByte();
            if (fileDoneType != TYPE_FILE_DONE) {
                throw new IOException("Expected FILE_DONE, got type: " + fileDoneType);
            }

            receivedFiles.add(outputFile);
        }

        byte allDoneType = input.readByte();
        if (allDoneType != TYPE_ALL_DONE) {
            throw new IOException("Expected ALL_DONE, got type: " + allDoneType);
        }

        for (File file : receivedFiles) {
            transferHistoryRepository.saveTransferRecord(new TransferRecord(
                    0L,
                    file.getName(),
                    file.length(),
                    "application/octet-stream",
                    TransferDirection.RECEIVED,
                    TransferStatus.COMPLETED,
                    System.currentTimeMillis()
            ));
        }
    }

    private void performSendMetadata(List<String> sourcePaths, String destinationAddress) throws IOException {
        List<File> files = validateFiles(sourcePaths);
        FileMetadata metadata = buildMetadata(files);
        String metaJson = metadata.toJson();

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(destinationAddress, TRANSFER_PORT), 10000);
        socket.setSoTimeout(SOCKET_TIMEOUT_MS);
        activeSocket = socket;

        try (DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE))) {
            byte[] metaBytes = metaJson.getBytes(StandardCharsets.UTF_8);
            output.writeByte(TYPE_METADATA);
            output.writeInt(metaBytes.length);
            output.write(metaBytes);
            output.flush();
        } finally {
            activeSocket = null;
        }
    }

    private FileMetadata performReceiveMetadata() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(TRANSFER_PORT)) {
            serverSocket.setReuseAddress(true);
            activeServerSocket = serverSocket;
            Socket socket = serverSocket.accept();
            socket.setSoTimeout(SOCKET_TIMEOUT_MS);
            activeSocket = socket;

            try (DataInputStream input = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE))) {
                byte msgType = input.readByte();
                if (msgType != TYPE_METADATA) {
                    throw new IOException("Expected metadata");
                }
                int metaLength = input.readInt();
                byte[] metaBytes = new byte[metaLength];
                input.readFully(metaBytes);
                String metaJson = new String(metaBytes, StandardCharsets.UTF_8);
                return FileMetadata.fromJson(metaJson);
            }
        } finally {
            activeServerSocket = null;
            activeSocket = null;
        }
    }

    private void performAcceptReject(boolean accept, String sessionToken) throws IOException {
        TransferRequest request = lastTransferRequest;
        if (request == null) return;

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(request.destinationAddress, TRANSFER_PORT), 10000);
        socket.setSoTimeout(SOCKET_TIMEOUT_MS);

        try (DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE))) {
            output.writeByte(accept ? TYPE_ACCEPT : TYPE_REJECT);
            output.flush();
        } finally {
            socket.close();
        }
    }

    private byte[] computeSha256(File file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int read;
                while ((read = input.read(buffer)) >= 0) {
                    digest.update(buffer, 0, read);
                }
            }
            return digest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IOException("SHA-256 not available", e);
        }
    }

    private void deriveSessionKey(String sessionToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String material = sessionToken == null ? "" : sessionToken;
            sessionKey = digest.digest(material.getBytes(StandardCharsets.UTF_8));
        } catch (java.security.NoSuchAlgorithmException e) {
            sessionKey = new byte[32];
            secureRandom.nextBytes(sessionKey);
        }
    }

    private CipherOutputStream createEncryptingStream(DataOutputStream output, byte[] nonce) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(AES_GCM_TAG_LENGTH, nonce);
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
            return new CipherOutputStream(new java.io.OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    output.write(b);
                }
                @Override
                public void write(byte[] b, int off, int len) throws IOException {
                    output.write(b, off, len);
                }
                @Override
                public void flush() throws IOException {
                    output.flush();
                }
            }, cipher);
        } catch (java.security.GeneralSecurityException e) {
            throw new IOException("Encryption setup failed", e);
        }
    }

    private Cipher createDecryptingCipher(byte[] nonce) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(AES_GCM_TAG_LENGTH, nonce);
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
            return cipher;
        } catch (java.security.GeneralSecurityException e) {
            throw new IOException("Decryption setup failed", e);
        }
    }

    private FileMetadata buildMetadata(List<File> files) throws IOException {
        List<FileMetadata.FileEntry> entries = new ArrayList<>();
        for (File file : files) {
            byte[] hash = computeSha256(file);
            entries.add(new FileMetadata.FileEntry(
                    file.getName(),
                    file.length(),
                    bytesToHex(hash)
            ));
        }
        return new FileMetadata(entries);
    }

    private List<File> validateFiles(List<String> sourcePaths) throws IOException {
        if (sourcePaths == null || sourcePaths.isEmpty()) {
            throw new IOException("At least one source file is required");
        }
        List<File> files = new ArrayList<>();
        for (String path : sourcePaths) {
            files.add(validateReadableFile(path));
        }
        return files;
    }

    private String resolveSenderAddress() {
        TransferRequest request = lastTransferRequest;
        if (request != null && request.destinationAddress != null && !request.destinationAddress.isEmpty()) {
            return request.destinationAddress;
        }
        return "192.168.49.1";
    }

    private List<TransferRecord> saveTransferRecords(List<File> files, TransferDirection direction) {
        List<TransferRecord> records = new ArrayList<>();
        for (File file : files) {
            records.add(new TransferRecord(
                    0L, file.getName(), file.length(), "application/octet-stream",
                    direction, TransferStatus.COMPLETED, System.currentTimeMillis()
            ));
        }
        return records;
    }

    private void runWithRetry(TransferExecutionStatus activeStatus, String startMessage,
                              TransferOperation operation, String successMessage) {
        postStatus(new TransferStatusUpdate(activeStatus, startMessage));
        IOException lastException = null;

        for (int attempt = 1; attempt <= MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                operation.execute();
                if (!cancelled) {
                    postStatus(new TransferStatusUpdate(TransferExecutionStatus.COMPLETED, successMessage));
                }
                return;
            } catch (IOException exception) {
                lastException = exception;
                if (cancelled) {
                    postStatus(new TransferStatusUpdate(TransferExecutionStatus.FAILED, "Transfer cancelled by user."));
                    return;
                }
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    postStatus(new TransferStatusUpdate(
                            TransferExecutionStatus.RETRYING,
                            "Attempt " + attempt + " failed: " + friendlyError(exception)
                                    + " Retrying " + (attempt + 1) + "/" + MAX_RETRY_ATTEMPTS
                    ));
                    sleepQuietly(RETRY_DELAY_MS * attempt);
                }
            }
        }

        String failureMessage = "Transfer failed after " + MAX_RETRY_ATTEMPTS + " attempts. "
                + friendlyError(lastException);
        postStatus(new TransferStatusUpdate(TransferExecutionStatus.FAILED, failureMessage));
    }

    private void throwIfCancelled() throws IOException {
        if (cancelled) throw new IOException("Transfer cancelled by user");
    }

    private TransferProgress progressFromBytes(long transferredBytes, long totalBytes, long startMillis) {
        long elapsedMillis = Math.max(System.currentTimeMillis() - startMillis, 1L);
        double speedBytesPerSecond = transferredBytes * 1000.0 / elapsedMillis;
        float percent = totalBytes <= 0L ? 0f : (float) ((transferredBytes * 100.0) / totalBytes);
        return new TransferProgress(transferredBytes, totalBytes, speedBytesPerSecond, Math.min(percent, 100f));
    }

    private void postStatus(TransferStatusUpdate statusUpdate) {
        for (TransferStatusListener listener : statusListeners) {
            listener.onStatusUpdated(statusUpdate);
        }
    }

    private void postProgress(TransferProgress progress) {
        for (TransferProgressListener listener : progressListeners) {
            listener.onProgressUpdated(progress);
        }
    }

    private String friendlyError(IOException exception) {
        if (exception == null || exception.getMessage() == null) return "Unknown network or file error.";
        String message = exception.getMessage().toLowerCase();
        if (message.contains("timed out")) return "Connection timed out.";
        if (message.contains("refused")) return "Target device refused connection.";
        if (message.contains("not exist") || message.contains("not readable")) return "Selected file is unavailable.";
        if (message.contains("dropped")) return "Connection dropped during transfer.";
        if (message.contains("reject")) return "Receiver rejected the transfer.";
        if (message.contains("sha-256") || message.contains("integrity")) return "Integrity verification failed. Please retry transfer.";
        if (message.contains("encrypt") || message.contains("decrypt")) return "Encryption error. Session may be invalid.";
        return exception.getMessage();
    }

    private void sleepQuietly(long delayMillis) {
        try { Thread.sleep(delayMillis); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private void closeActiveSockets() {
        closeSocketQuietly(activeSocket);
        closeServerSocketQuietly(activeServerSocket);
        activeSocket = null;
        activeServerSocket = null;
    }

    private void closeSocketQuietly(Socket socket) {
        if (socket == null) return;
        try { socket.close(); } catch (IOException ignored) { }
    }

    private void closeServerSocketQuietly(ServerSocket serverSocket) {
        if (serverSocket == null) return;
        try { serverSocket.close(); } catch (IOException ignored) { }
    }

    private File validateReadableFile(String sourcePath) throws IOException {
        if (sourcePath == null || sourcePath.trim().isEmpty()) throw new IOException("Source path must not be blank");
        File file = new File(sourcePath);
        if (!file.exists() || !file.isFile()) throw new IOException("File does not exist: " + sourcePath);
        if (!file.canRead()) throw new IOException("File is not readable: " + sourcePath);
        return file;
    }

    private File validateDestinationDirectory(String destinationPath) throws IOException {
        if (destinationPath == null || destinationPath.trim().isEmpty()) throw new IOException("Destination directory must not be blank");
        File directory = new File(destinationPath);
        if (!directory.exists() && !directory.mkdirs()) throw new IOException("Unable to create destination directory: " + destinationPath);
        if (!directory.isDirectory()) throw new IOException("Destination path is not a directory: " + destinationPath);
        return directory;
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) return "received_" + System.currentTimeMillis();
        return fileName.replace("..", "").replace('/', '_').replace('\\', '_');
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) builder.append(String.format("%02x", value));
        return builder.toString();
    }

    private interface TransferOperation {
        void execute() throws IOException;
    }

    private enum TransferType { SEND, RECEIVE }

    private static class TransferRequest {
        private final TransferType type;
        private final List<String> sourcePaths;
        private final String destinationAddress;
        private final String destinationDirectoryPath;
        private final String sessionToken;

        private TransferRequest(TransferType type, List<String> sourcePaths, String destinationAddress,
                                String destinationDirectoryPath, String sessionToken) {
            this.type = type;
            this.sourcePaths = sourcePaths;
            this.destinationAddress = destinationAddress;
            this.destinationDirectoryPath = destinationDirectoryPath;
            this.sessionToken = sessionToken;
        }

        static TransferRequest forSend(List<String> sourcePaths, String destinationAddress, String sessionToken) {
            return new TransferRequest(TransferType.SEND,
                    sourcePaths == null ? Collections.emptyList() : new ArrayList<>(sourcePaths),
                    destinationAddress, null, sessionToken);
        }

        static TransferRequest forReceive(String destinationDirectoryPath, String sessionToken) {
            return new TransferRequest(TransferType.RECEIVE, Collections.emptyList(), null,
                    destinationDirectoryPath, sessionToken);
        }
    }
}
