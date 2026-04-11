package com.mrp.sml.domain.repository;

import java.util.List;

public interface FileTransferRepository {
    void observeTransferProgress(TransferProgressListener listener);
    void removeTransferProgressObserver(TransferProgressListener listener);

    void observeTransferStatus(TransferStatusListener listener);
    void removeTransferStatusObserver(TransferStatusListener listener);

    void sendFile(String sourcePath, String destinationAddress);
    void sendFiles(List<String> sourcePaths, String destinationAddress);
    void receiveFile(String destinationPath);
    void receiveFiles(String destinationDirectoryPath);

    interface TransferProgressListener {
        void onProgressUpdated(TransferProgress progress);
    }

    interface TransferStatusListener {
        void onStatusUpdated(TransferStatusUpdate statusUpdate);
    }
}
