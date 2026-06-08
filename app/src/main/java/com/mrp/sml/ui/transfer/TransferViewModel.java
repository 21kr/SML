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
    private final MutableLiveData<String> currentFileName = new MutableLiveData<>("-");
    private final MutableLiveData<Float> progressValue = new MutableLiveData<>(0f);
    private final MutableLiveData<String> speedText = new MutableLiveData<>("- MB/s");
    private final MutableLiveData<String> etaText = new MutableLiveData<>("ETA: -");
    private final MutableLiveData<Boolean> isComplete = new MutableLiveData<>(false);
    private final MutableLiveData<String> successSummary = new MutableLiveData<>("-");

    private final FileTransferRepository.TransferStatusListener statusListener = this::onStatusUpdated;
    private final FileTransferRepository.TransferProgressListener progressListener = this::onProgressUpdated;

    private int completedFiles = 0;
    private int totalFiles = 0;

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

    public LiveData<String> getCurrentFileName() {
        return currentFileName;
    }

    public LiveData<Float> getProgressValue() {
        return progressValue;
    }

    public LiveData<String> getSpeedText() {
        return speedText;
    }

    public LiveData<String> getEtaText() {
        return etaText;
    }

    public LiveData<Boolean> getIsComplete() {
        return isComplete;
    }

    public LiveData<String> getSuccessSummary() {
        return successSummary;
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
            isComplete.postValue(false);
            completedFiles = 0;
            totalFiles = 1;
            currentFileName.postValue(path.substring(path.lastIndexOf('/') + 1));
            fileTransferUseCase.sendFiles(
                    Collections.singletonList(path.trim()),
                    destinationAddress.trim(),
                    sessionToken == null ? "" : sessionToken.trim()
            );
        } catch (IllegalArgumentException e) {
            transferStatusText.postValue("Transfer: FAILED - " + e.getMessage());
        }
    }

    public void receiveFiles(String outputDirectoryPath, String sessionToken) {
        try {
            isComplete.postValue(false);
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

        if (statusUpdate.getStatus().name().equals("COMPLETED")) {
            completedFiles++;
            isComplete.postValue(true);
            successSummary.postValue(completedFiles + " file(s) transferred");
        }
    }

    private void onProgressUpdated(TransferProgress progress) {
        float percent = progress.getProgressPercent();
        double speedMBps = progress.getSpeedBytesPerSecond() / (1024.0 * 1024.0);

        String progressText = String.format("Progress: %.2f%% (%.2f MB/s)", percent, speedMBps);
        transferProgressText.postValue(progressText);

        progressValue.postValue(percent / 100f);

        speedText.postValue(String.format("%.2f MB/s", speedMBps));

        if (speedMBps > 0.01 && percent > 0) {
            long remainingBytes = progress.getTotalBytes() - progress.getTransferredBytes();
            long etaSeconds = (long) (remainingBytes / progress.getSpeedBytesPerSecond());
            if (etaSeconds < 60) {
                etaText.postValue("ETA: " + etaSeconds + "s");
            } else {
                etaText.postValue("ETA: " + (etaSeconds / 60) + "m " + (etaSeconds % 60) + "s");
            }
        }
    }

    @Override
    protected void onCleared() {
        fileTransferUseCase.removeTransferStatusObserver(statusListener);
        fileTransferUseCase.removeTransferProgressObserver(progressListener);
        super.onCleared();
    }
}
