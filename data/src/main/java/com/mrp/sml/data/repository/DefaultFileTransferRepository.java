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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultFileTransferRepository implements FileTransferRepository {
    private static final int DEFAULT_TRANSFER_PORT = 8988;
    private static final int BUFFER_SIZE_BYTES = 256 * 1024;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 350L;

    private final DispatchersProvider dispatchersProvider;
    private final TransferHistoryRepository transferHistoryRepository;

    private final CopyOnWriteArrayList<TransferProgressListener> progressListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<TransferStatusListener> statusListeners = new CopyOnWriteArrayList<>();

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
        sendFiles(Collections.singletonList(sourcePath), destinationAddress);
    }

    @Override
    public void sendFiles(List<String> sourcePaths, String destinationAddress) {
        dispatchersProvider.ioExecutor().execute(() -> runWithRetry(
                TransferExecutionStatus.SENDING,
                "Sending files",
                () -> performSendFiles(sourcePaths, destinationAddress),
                "Files sent"
        ));
    }

    @Override
    public void receiveFile(String destinationPath) {
        receiveFiles(destinationPath);
    }

    @Override
    public void receiveFiles(String destinationDirectoryPath) {
        dispatchersProvider.ioExecutor().execute(() -> runWithRetry(
                TransferExecutionStatus.RECEIVING,
                "Waiting for incoming files",
                () -> performReceiveFiles(destinationDirectoryPath),
                "Files received"
        ));
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
                postStatus(new TransferStatusUpdate(TransferExecutionStatus.COMPLETED, successMessage));
                return;
            } catch (IOException exception) {
                lastException = exception;
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

    private void performSendFiles(List<String> sourcePaths, String destinationAddress) throws IOException {
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

        try (Socket socket = new Socket(destinationAddress, DEFAULT_TRANSFER_PORT);
             DataOutputStream output = new DataOutputStream(
                     new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE_BYTES))) {

            output.writeInt(files.size());
            byte[] buffer = new byte[BUFFER_SIZE_BYTES];

            for (File file : files) {
                output.writeUTF(file.getName());
                output.writeLong(file.length());
                try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE_BYTES)) {
                    int read;
                    while ((read = input.read(buffer)) >= 0) {
                        output.write(buffer, 0, read);
                        transferred += read;
                        postProgress(progressFromBytes(transferred, totalBytes, start));
                    }
                }

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
        }
    }

    private void performReceiveFiles(String destinationDirectoryPath) throws IOException {
        File destinationDirectory = validateDestinationDirectory(destinationDirectoryPath);
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_TRANSFER_PORT);
             Socket socket = serverSocket.accept();
             DataInputStream input = new DataInputStream(
                     new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE_BYTES))) {

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
                File outputFile = new File(destinationDirectory, sanitizeFileName(header.fileName));
                long remaining = header.sizeBytes;
                try (BufferedOutputStream output = new BufferedOutputStream(
                        new FileOutputStream(outputFile), BUFFER_SIZE_BYTES)) {
                    while (remaining > 0) {
                        int bytesToRead = (int) Math.min((long) buffer.length, remaining);
                        int read = input.read(buffer, 0, bytesToRead);
                        if (read < 0) {
                            throw new IOException("Connection dropped during file transfer");
                        }
                        output.write(buffer, 0, read);
                        remaining -= read;
                        transferred += read;
                        postProgress(progressFromBytes(transferred, totalBytes, start));
                    }
                    output.flush();
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
        return exception.getMessage();
    }

    private void sleepQuietly(long delayMillis) {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
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

    private interface TransferOperation {
        void execute() throws IOException;
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
