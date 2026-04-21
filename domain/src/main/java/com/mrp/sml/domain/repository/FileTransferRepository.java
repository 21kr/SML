package com.mrp.sml.domain.repository;

import java.util.List;

public interface FileTransferRepository {
    void observeTransferProgress(TransferProgressListener listener);
    void removeTransferProgressObserver(TransferProgressListener listener);

    void observeTransferStatus(TransferStatusListener listener);
    void removeTransferStatusObserver(TransferStatusListener listener);

    default void sendFile(String sourcePath, String destinationAddress) {
        sendFile(sourcePath, destinationAddress, "");
    }

    default void sendFiles(List<String> sourcePaths, String destinationAddress) {
        sendFiles(sourcePaths, destinationAddress, "");
    }

    default void receiveFile(String destinationPath) {
        receiveFile(destinationPath, "");
    }

    default void receiveFiles(String destinationDirectoryPath) {
        receiveFiles(destinationDirectoryPath, "");
    }

    void sendFile(String sourcePath, String destinationAddress, String sessionToken);
    void sendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken);
    void receiveFile(String destinationPath, String sessionToken);
    void receiveFiles(String destinationDirectoryPath, String sessionToken);
    void cancelTransfer();
    void resumeLastTransfer();

    interface TransferProgressListener {
        void onProgressUpdated(TransferProgress progress);
    }

    interface TransferStatusListener {
        void onStatusUpdated(TransferStatusUpdate statusUpdate);
    }
}
