package com.mrp.sml.data.repository;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.FileTransferRepository;
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
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultFileTransferRepository implements FileTransferRepository {
    private static final String PROTOCOL_MAGIC = "SMLP2P";
    private static final int PROTOCOL_VERSION = 1;
    private static final int DEFAULT_TRANSFER_PORT = 8988;
    private static final int BUFFER_SIZE_BYTES = 256 * 1024;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 350L;
    private static final int SOCKET_CONNECT_TIMEOUT_MS = 10_000;
    private static final int SOCKET_READ_TIMEOUT_MS = 15_000;
    private static final int AUTH_NONCE_SIZE = 16;
    private static final String AUTH_HMAC_ALGORITHM = "HmacSHA256";

    private final DispatchersProvider dispatchersProvider;
    private final TransferHistoryRepository transferHistoryRepository;

    private final CopyOnWriteArrayList<TransferProgressListener> progressListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<TransferStatusListener> statusListeners = new CopyOnWriteArrayList<>();

    private volatile TransferRequest lastTransferRequest;
    private volatile boolean cancelled;
    private volatile Socket activeSocket;
    private volatile ServerSocket activeServerSocket;

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
    public void sendFile(String sourcePath, String destinationAddress) {
        sendFile(sourcePath, destinationAddress, "");
    }

    @Override
    public void sendFile(String sourcePath, String destinationAddress, String sessionToken) {
        sendFiles(Collections.singletonList(sourcePath), destinationAddress, sessionToken);
    }

    @Override
    public void sendFiles(List<String> sourcePaths, String destinationAddress) {
        sendFiles(sourcePaths, destinationAddress, "");
    }

    @Override
    public void sendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken) {
        cancelled = false;
        lastTransferRequest = TransferRequest.forSend(sourcePaths, destinationAddress, sessionToken);
        dispatchersProvider.ioExecutor().execute(() -> runWithRetry(
                TransferExecutionStatus.SENDING,
                "Sending files",
                () -> performSendFiles(sourcePaths, destinationAddress, sessionToken),
                "Files sent"
        ));
    }

    @Override
    public void receiveFile(String destinationPath) {
        receiveFile(destinationPath, "");
    }

    @Override
    public void receiveFile(String destinationPath, String sessionToken) {
        receiveFiles(destinationPath, sessionToken);
    }

    @Override
    public void receiveFiles(String destinationDirectoryPath) {
        receiveFiles(destinationDirectoryPath, "");
    }

    @Override
    public void receiveFiles(String destinationDirectoryPath, String sessionToken) {
        cancelled = false;
        lastTransferRequest = TransferRequest.forReceive(destinationDirectoryPath, sessionToken);
        dispatchersProvider.ioExecutor().execute(() -> runWithRetry(
                TransferExecutionStatus.RECEIVING,
                "Waiting for incoming files",
                () -> performReceiveFiles(destinationDirectoryPath, sessionToken),
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

    private void runWithRetry(
            TransferExecutionStatus activeStatus,
            String startMessage,
            TransferOperation operation,
            String successMessage
    ) {
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

    private void performSendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken)
            throws IOException {
        if (sourcePaths == null || sourcePaths.isEmpty()) {
            throw new IOException("At least one source path is required");
        }
        if (destinationAddress == null || destinationAddress.trim().isEmpty()) {
            throw new IOException("Destination address is required");
        }

        List<File> files = new ArrayList<>();
        long totalBytes = 0L;
        for (String sourcePath : sourcePaths) {
            File file = validateReadableFile(sourcePath);
            files.add(file);
            totalBytes += file.length();
        }

        long transferred = 0L;
        postProgress(new TransferProgress(0L, totalBytes, 0.0, 0f));
        long start = System.currentTimeMillis();

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(destinationAddress, DEFAULT_TRANSFER_PORT), SOCKET_CONNECT_TIMEOUT_MS);
        socket.setSoTimeout(SOCKET_READ_TIMEOUT_MS);
        activeSocket = socket;
        try (Socket ignored = socket;
             DataOutputStream output = new DataOutputStream(
                     new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE_BYTES))) {
            output.writeUTF(PROTOCOL_MAGIC);
            output.writeInt(PROTOCOL_VERSION);
            String safeToken = sanitizeSessionToken(sessionToken);
            output.writeUTF(safeToken);
            byte[] authNonce = new byte[AUTH_NONCE_SIZE];
            new SecureRandom().nextBytes(authNonce);
            output.writeInt(authNonce.length);
            output.write(authNonce);
            output.writeUTF(computeAuthSignature(safeToken, authNonce));

            output.writeInt(files.size());
            byte[] buffer = new byte[BUFFER_SIZE_BYTES];

            for (File file : files) {
                throwIfCancelled();
                output.writeUTF(file.getName());
                output.writeLong(file.length());
                CRC32 crc32 = new CRC32();
                try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE_BYTES)) {
                    int read;
                    while ((read = input.read(buffer)) >= 0) {
                        throwIfCancelled();
                        output.write(buffer, 0, read);
                        crc32.update(buffer, 0, read);
                        transferred += read;
                        postProgress(progressFromBytes(transferred, totalBytes, start));
                    }
                }
                output.writeLong(crc32.getValue());

                transferHistoryRepository.saveTransferRecord(new TransferRecord(
                        0L,
                        file.getName(),
                        file.length(),
                        "application/octet-stream",
                        TransferDirection.SENT,
                        TransferStatus.COMPLETED,
                        System.currentTimeMillis()
                ));
            }
            output.flush();
        } finally {
            activeSocket = null;
        }
    }

    private void performReceiveFiles(String destinationDirectoryPath, String sessionToken) throws IOException {
        File destinationDirectory = validateDestinationDirectory(destinationDirectoryPath);
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_TRANSFER_PORT)) {
            activeServerSocket = serverSocket;
            serverSocket.setReuseAddress(true);
            Socket socket = serverSocket.accept();
            socket.setSoTimeout(SOCKET_READ_TIMEOUT_MS);
            activeSocket = socket;
            try (Socket ignored = socket;
                 DataInputStream input = new DataInputStream(
                         new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE_BYTES))) {

                String protocolMagic = input.readUTF();
                int protocolVersion = input.readInt();
                String providedSessionToken = input.readUTF();
                if (!PROTOCOL_MAGIC.equals(protocolMagic) || protocolVersion != PROTOCOL_VERSION) {
                    throw new IOException("Unsupported transfer protocol.");
                }
                String safeToken = sanitizeSessionToken(sessionToken);
                if (!safeToken.equals(providedSessionToken)) {
                    throw new IOException("Session token mismatch.");
                }
                int nonceLength = input.readInt();
                if (nonceLength <= 0 || nonceLength > 1024) {
                    throw new IOException("Invalid auth nonce.");
                }
                byte[] nonce = new byte[nonceLength];
                input.readFully(nonce);
                String providedSignature = input.readUTF();
                String expectedSignature = computeAuthSignature(safeToken, nonce);
                if (!MessageDigest.isEqual(
                        expectedSignature.getBytes(StandardCharsets.UTF_8),
                        providedSignature.getBytes(StandardCharsets.UTF_8)
                )) {
                    throw new IOException("Authentication failed.");
                }

                int fileCount = input.readInt();
                if (fileCount <= 0) {
                    throw new IOException("No files were provided by sender");
                }

                List<FileHeader> headers = new ArrayList<>();
                long totalBytes = 0L;
                for (int i = 0; i < fileCount; i++) {
                    String name = input.readUTF();
                    long sizeBytes = input.readLong();
                    headers.add(new FileHeader(name, sizeBytes));
                    totalBytes += sizeBytes;
                }

                long transferred = 0L;
                long start = System.currentTimeMillis();
                byte[] buffer = new byte[BUFFER_SIZE_BYTES];

                for (FileHeader header : headers) {
                    throwIfCancelled();
                    File outputFile = new File(destinationDirectory, sanitizeFileName(header.fileName));
                    long remaining = header.sizeBytes;
                    CRC32 crc32 = new CRC32();
                    try (BufferedOutputStream output = new BufferedOutputStream(
                            new FileOutputStream(outputFile), BUFFER_SIZE_BYTES)) {
                        while (remaining > 0) {
                            throwIfCancelled();
                            int bytesToRead = (int) Math.min((long) buffer.length, remaining);
                            int read = input.read(buffer, 0, bytesToRead);
                            if (read < 0) {
                                throw new IOException("Connection dropped during file transfer");
                            }
                            output.write(buffer, 0, read);
                            crc32.update(buffer, 0, read);
                            remaining -= read;
                            transferred += read;
                            postProgress(progressFromBytes(transferred, totalBytes, start));
                        }
                        output.flush();
                    }
                    long expectedCrc = input.readLong();
                    long actualCrc = crc32.getValue();
                    if (expectedCrc != actualCrc) {
                        if (!outputFile.delete()) {
                            // Keep mismatch failure primary; best-effort cleanup only.
                        }
                        throw new IOException("File integrity check failed.");
                    }

                    transferHistoryRepository.saveTransferRecord(new TransferRecord(
                            0L,
                            outputFile.getName(),
                            outputFile.length(),
                            "application/octet-stream",
                            TransferDirection.RECEIVED,
                            TransferStatus.COMPLETED,
                            System.currentTimeMillis()
                    ));
                }
            } finally {
                activeSocket = null;
                activeServerSocket = null;
            }
        }
    }

    private void throwIfCancelled() throws IOException {
        if (cancelled) {
            throw new IOException("Transfer cancelled by user");
        }
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
        if (exception == null || exception.getMessage() == null) {
            return "Unknown network or file error.";
        }

        String message = exception.getMessage().toLowerCase();
        if (message.contains("timed out")) {
            return "Connection timed out.";
        }
        if (message.contains("refused")) {
            return "Target device refused connection.";
        }
        if (message.contains("not exist") || message.contains("not readable")) {
            return "Selected file is unavailable.";
        }
        if (message.contains("dropped")) {
            return "Connection dropped during transfer.";
        }
        if (message.contains("token mismatch")) {
            return "Sender/receiver token mismatch.";
        }
        if (message.contains("authentication")) {
            return "Authentication failed. Check session token and retry.";
        }
        if (message.contains("integrity")) {
            return "Integrity verification failed. Please retry transfer.";
        }
        return exception.getMessage();
    }

    private void sleepQuietly(long delayMillis) {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private void closeActiveSockets() {
        closeSocketQuietly(activeSocket);
        closeServerSocketQuietly(activeServerSocket);
        activeSocket = null;
        activeServerSocket = null;
    }

    private void closeSocketQuietly(Socket socket) {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch (IOException ignored) {
            // Ignore close errors during cancellation.
        }
    }

    private void closeServerSocketQuietly(ServerSocket serverSocket) {
        if (serverSocket == null) {
            return;
        }
        try {
            serverSocket.close();
        } catch (IOException ignored) {
            // Ignore close errors during cancellation.
        }
    }

    private File validateReadableFile(String sourcePath) throws IOException {
        if (sourcePath == null || sourcePath.trim().isEmpty()) {
            throw new IOException("Source path must not be blank");
        }
        File file = new File(sourcePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File does not exist: " + sourcePath);
        }
        if (!file.canRead()) {
            throw new IOException("File is not readable: " + sourcePath);
        }
        return file;
    }

    private File validateDestinationDirectory(String destinationPath) throws IOException {
        if (destinationPath == null || destinationPath.trim().isEmpty()) {
            throw new IOException("Destination directory must not be blank");
        }

        File directory = new File(destinationPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Unable to create destination directory: " + destinationPath);
        }
        if (!directory.isDirectory()) {
            throw new IOException("Destination path is not a directory: " + destinationPath);
        }
        return directory;
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return "received_" + System.currentTimeMillis();
        }
        return fileName.replace("..", "").replace('/', '_').replace('\\', '_');
    }

    private String sanitizeSessionToken(String sessionToken) {
        return sessionToken == null ? "" : sessionToken.trim();
    }

    private String computeAuthSignature(String sessionToken, byte[] nonce) throws IOException {
        try {
            Mac mac = Mac.getInstance(AUTH_HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(
                    sanitizeSessionToken(sessionToken).getBytes(StandardCharsets.UTF_8),
                    AUTH_HMAC_ALGORITHM
            ));
            byte[] digest = mac.doFinal(nonce);
            return toHex(digest);
        } catch (GeneralSecurityException securityException) {
            throw new IOException("Authentication setup failed.", securityException);
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) {
            builder.append(String.format("%02x", value));
        }
        return builder.toString();
    }

    private interface TransferOperation {
        void execute() throws IOException;
    }

    private enum TransferType {
        SEND,
        RECEIVE
    }

    private static class TransferRequest {
        private final TransferType type;
        private final List<String> sourcePaths;
        private final String destinationAddress;
        private final String destinationDirectoryPath;
        private final String sessionToken;

        private TransferRequest(
                TransferType type,
                List<String> sourcePaths,
                String destinationAddress,
                String destinationDirectoryPath,
                String sessionToken
        ) {
            this.type = type;
            this.sourcePaths = sourcePaths;
            this.destinationAddress = destinationAddress;
            this.destinationDirectoryPath = destinationDirectoryPath;
            this.sessionToken = sessionToken;
        }

        static TransferRequest forSend(List<String> sourcePaths, String destinationAddress, String sessionToken) {
            return new TransferRequest(
                    TransferType.SEND,
                    sourcePaths == null ? Collections.emptyList() : new ArrayList<>(sourcePaths),
                    destinationAddress,
                    null,
                    sessionToken
            );
        }

        static TransferRequest forReceive(String destinationDirectoryPath, String sessionToken) {
            return new TransferRequest(
                    TransferType.RECEIVE,
                    Collections.emptyList(),
                    null,
                    destinationDirectoryPath,
                    sessionToken
            );
        }
    }

    private static class FileHeader {
        private final String fileName;
        private final long sizeBytes;

        private FileHeader(String fileName, long sizeBytes) {
            this.fileName = fileName;
            this.sizeBytes = sizeBytes;
        }
    }
}
