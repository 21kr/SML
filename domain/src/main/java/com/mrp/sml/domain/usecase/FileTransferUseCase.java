package com.mrp.sml.domain.usecase;

import com.mrp.sml.domain.model.FileMetadata;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import java.util.List;
import javax.inject.Inject;

public class FileTransferUseCase {

    private final FileTransferRepository repository;

    @Inject
    public FileTransferUseCase(FileTransferRepository repository) {
        this.repository = repository;
    }

    public void sendFiles(List<String> sourcePaths, String destinationAddress, String sessionToken) {
        if (sourcePaths == null || sourcePaths.isEmpty()) {
            throw new IllegalArgumentException("At least one source file is required");
        }
        if (destinationAddress == null || destinationAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination address is required");
        }
        repository.sendFiles(sourcePaths, destinationAddress, sessionToken == null ? "" : sessionToken.trim());
    }

    public void receiveFiles(String destinationDirectoryPath, String sessionToken) {
        if (destinationDirectoryPath == null || destinationDirectoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Output directory is required");
        }
        repository.receiveFiles(destinationDirectoryPath.trim(), sessionToken == null ? "" : sessionToken.trim());
    }

    public void cancelTransfer() {
        repository.cancelTransfer();
    }

    public void resumeLastTransfer() {
        repository.resumeLastTransfer();
    }

    public void sendMetadata(List<String> sourcePaths, String destinationAddress, String sessionToken) {
        repository.sendMetadata(sourcePaths, destinationAddress, sessionToken);
    }

    public FileMetadata receiveMetadata(String sessionToken) {
        return repository.receiveMetadata(sessionToken);
    }

    public void acceptTransfer(String sessionToken) {
        repository.acceptTransfer(sessionToken);
    }

    public void rejectTransfer(String sessionToken) {
        repository.rejectTransfer(sessionToken);
    }

    public void observeTransferStatus(FileTransferRepository.TransferStatusListener listener) {
        repository.observeTransferStatus(listener);
    }

    public void removeTransferStatusObserver(FileTransferRepository.TransferStatusListener listener) {
        repository.removeTransferStatusObserver(listener);
    }

    public void observeTransferProgress(FileTransferRepository.TransferProgressListener listener) {
        repository.observeTransferProgress(listener);
    }

    public void removeTransferProgressObserver(FileTransferRepository.TransferProgressListener listener) {
        repository.removeTransferProgressObserver(listener);
    }
}
