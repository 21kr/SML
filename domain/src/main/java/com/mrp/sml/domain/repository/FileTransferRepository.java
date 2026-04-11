package com.mrp.sml.domain.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

public interface FileTransferRepository {
    LiveData<TransferProgress> observeTransferProgress();
    LiveData<TransferStatusUpdate> observeTransferStatus();
    void sendFile(String sourcePath, String destinationAddress);
    void sendFiles(List<String> sourcePaths, String destinationAddress);
    void receiveFile(String destinationPath);
    void receiveFiles(String destinationDirectoryPath);
}
