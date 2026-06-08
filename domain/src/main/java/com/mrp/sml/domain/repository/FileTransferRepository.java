package com.mrp.sml.domain.repository;

import com.mrp.sml.domain.model.FileMetadata;
import java.util.List;

public interface FileTransferRepository {
    void observeTransferProgress(TransferProgressListener listener);
    void removeTransferProgressObserver(TransferProgressListener listener);

    void observeTransferStatus(TransferStatusListener listener);
    void removeTransferStatusObserver(TransferStatusListener listener);

    void sendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken);
    void receiveFiles(String destinationDirectoryPath, String sessionToken);
    void cancelTransfer();
    void resumeLastTransfer();

    void sendMetadata(List<String> sourcePaths, String destinationAddress, String sessionToken);
    FileMetadata receiveMetadata(String sessionToken);
    void acceptTransfer(String sessionToken);
    void rejectTransfer(String sessionToken);

    void sendChunk(String filePath, String destinationAddress, int chunkIndex, int chunkSize, String sessionToken);
    boolean receiveChunkAck(String sessionToken, int chunkIndex);

    interface TransferProgressListener {
        void onProgressUpdated(TransferProgress progress);
    }

    interface TransferStatusListener {
        void onStatusUpdated(TransferStatusUpdate statusUpdate);
    }
}
