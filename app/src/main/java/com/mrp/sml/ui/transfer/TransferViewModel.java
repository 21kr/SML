package com.mrp.sml.ui.transfer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import com.mrp.sml.domain.usecase.FileTransferUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.Collections;
import javax.inject.Inject;

@HiltViewModel
public class TransferViewModel extends ViewModel {

    private final FileTransferUseCase fileTransferUseCase;

    private final MutableLiveData<String> transferStatusText = new MutableLiveData<>("Transfer: IDLE");
    private final MutableLiveData<String> transferProgressText = new MutableLiveData<>("Progress: 0.00% (0.00 MB/s)");

    private final FileTransferRepository.TransferStatusListener statusListener = this::onStatusUpdated;
    private final FileTransferRepository.TransferProgressListener progressListener = this::onProgressUpdated;

    @Inject
    public TransferViewModel(FileTransferUseCase fileTransferUseCase) {
        this.fileTransferUseCase = fileTransferUseCase;
        this.fileTransferUseCase.observeTransferStatus(statusListener);
        this.fileTransferUseCase.observeTransferProgress(progressListener);
    }

    public LiveData<String> getTransferStatusText() {
        return transferStatusText;
    }

    public LiveData<String> getTransferProgressText() {
        return transferProgressText;
    }

    public void sendFile(String path, String destinationAddress) {
        sendFile(path, destinationAddress, "");
    }

    public void sendFile(String path, String destinationAddress, String sessionToken) {
        if (path == null || path.trim().isEmpty()) {
            transferStatusText.postValue("Transfer: FAILED - file path is required");
            return;
        }
        if (destinationAddress == null || destinationAddress.trim().isEmpty()) {
            transferStatusText.postValue("Transfer: FAILED - destination address is required");
            return;
        }
        try {
            fileTransferUseCase.sendFiles(
                    Collections.singletonList(path.trim()),
                    destinationAddress.trim(),
                    sessionToken == null ? "" : sessionToken.trim()
            );
        } catch (IllegalArgumentException e) {
            transferStatusText.postValue("Transfer: FAILED - " + e.getMessage());
        }
    }

    public void receiveFiles(String outputDirectoryPath) {
        receiveFiles(outputDirectoryPath, "");
    }

    public void receiveFiles(String outputDirectoryPath, String sessionToken) {
        try {
            fileTransferUseCase.receiveFiles(outputDirectoryPath, sessionToken);
        } catch (IllegalArgumentException e) {
            transferStatusText.postValue("Transfer: FAILED - " + e.getMessage());
        }
    }

    public void cancelTransfer() {
        fileTransferUseCase.cancelTransfer();
    }

    public void resumeTransfer() {
        fileTransferUseCase.resumeLastTransfer();
    }

    private void onStatusUpdated(TransferStatusUpdate statusUpdate) {
        String text = "Transfer: " + statusUpdate.getStatus().name();
        if (statusUpdate.getUserMessage() != null && !statusUpdate.getUserMessage().trim().isEmpty()) {
            text = text + " - " + statusUpdate.getUserMessage();
        }
        transferStatusText.postValue(text);
    }

    private void onProgressUpdated(TransferProgress progress) {
        String text = String.format(
                "Progress: %.2f%% (%.2f MB/s)",
                progress.getProgressPercent(),
                progress.getSpeedBytesPerSecond() / (1024.0 * 1024.0)
        );
        transferProgressText.postValue(text);
    }

    @Override
    protected void onCleared() {
        fileTransferUseCase.removeTransferStatusObserver(statusListener);
        fileTransferUseCase.removeTransferProgressObserver(progressListener);
        super.onCleared();
    }
}
