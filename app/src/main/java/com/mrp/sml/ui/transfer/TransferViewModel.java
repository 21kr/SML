package com.mrp.sml.ui.transfer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.Collections;
import javax.inject.Inject;

@HiltViewModel
public class TransferViewModel extends ViewModel {

    private final FileTransferRepository fileTransferRepository;

    private final MutableLiveData<String> transferStatusText = new MutableLiveData<>("Transfer: IDLE");
    private final MutableLiveData<String> transferProgressText = new MutableLiveData<>("Progress: 0.00% (0.00 MB/s)");

    private final FileTransferRepository.TransferStatusListener statusListener = this::onStatusUpdated;
    private final FileTransferRepository.TransferProgressListener progressListener = this::onProgressUpdated;

    @Inject
    public TransferViewModel(FileTransferRepository fileTransferRepository) {
        this.fileTransferRepository = fileTransferRepository;
        this.fileTransferRepository.observeTransferStatus(statusListener);
        this.fileTransferRepository.observeTransferProgress(progressListener);
    }

    public LiveData<String> getTransferStatusText() {
        return transferStatusText;
    }

    public LiveData<String> getTransferProgressText() {
        return transferProgressText;
    }

    public void sendFile(String path, String destinationAddress) {
        if (path == null || path.trim().isEmpty()) {
            transferStatusText.postValue("Transfer: FAILED - file path is required");
            return;
        }
        if (destinationAddress == null || destinationAddress.trim().isEmpty()) {
            transferStatusText.postValue("Transfer: FAILED - destination address is required");
            return;
        }
        fileTransferRepository.sendFiles(Collections.singletonList(path.trim()), destinationAddress.trim());
    }

    public void receiveFiles(String outputDirectoryPath) {
        if (outputDirectoryPath == null || outputDirectoryPath.trim().isEmpty()) {
            transferStatusText.postValue("Transfer: FAILED - output directory is required");
            return;
        }
        fileTransferRepository.receiveFiles(outputDirectoryPath.trim());
    }


    public void cancelTransfer() {
        fileTransferRepository.cancelTransfer();
    }

    public void resumeTransfer() {
        fileTransferRepository.resumeLastTransfer();
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
        fileTransferRepository.removeTransferStatusObserver(statusListener);
        fileTransferRepository.removeTransferProgressObserver(progressListener);
        super.onCleared();
    }
}
