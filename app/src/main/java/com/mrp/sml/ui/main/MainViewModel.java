package com.mrp.sml.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferExecutionStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.Collections;
import javax.inject.Inject;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final DeviceConnectionRepository deviceConnectionRepository;
    private final FileTransferRepository fileTransferRepository;
    private final TransferHistoryRepository transferHistoryRepository;

    private final MutableLiveData<String> connectionStateText = new MutableLiveData<>("Connection: IDLE");
    private final MutableLiveData<String> transferStatusText = new MutableLiveData<>("Transfer: IDLE");
    private final MutableLiveData<String> transferProgressText = new MutableLiveData<>("Progress: 0.00% (0 B/s)");
    private final MutableLiveData<String> historySummaryText = new MutableLiveData<>("History: 0 records");

    private final DeviceConnectionRepository.ConnectionStateListener connectionStateListener =
            state -> connectionStateText.postValue("Connection: " + state.name());

    private final FileTransferRepository.TransferStatusListener transferStatusListener =
            this::handleTransferStatus;

    private final FileTransferRepository.TransferProgressListener transferProgressListener =
            this::handleTransferProgress;

    private final TransferHistoryRepository.TransferHistoryListener transferHistoryListener =
            history -> historySummaryText.postValue("History: " + history.size() + " records");

    @Inject
    public MainViewModel(
            DeviceConnectionRepository deviceConnectionRepository,
            FileTransferRepository fileTransferRepository,
            TransferHistoryRepository transferHistoryRepository
    ) {
        this.deviceConnectionRepository = deviceConnectionRepository;
        this.fileTransferRepository = fileTransferRepository;
        this.transferHistoryRepository = transferHistoryRepository;

        this.deviceConnectionRepository.observeConnectionState(connectionStateListener);
        this.fileTransferRepository.observeTransferStatus(transferStatusListener);
        this.fileTransferRepository.observeTransferProgress(transferProgressListener);
        this.transferHistoryRepository.observeTransferHistory(transferHistoryListener);
    }

    public LiveData<String> getConnectionStateText() {
        return connectionStateText;
    }

    public LiveData<String> getTransferStatusText() {
        return transferStatusText;
    }

    public LiveData<String> getTransferProgressText() {
        return transferProgressText;
    }

    public LiveData<String> getHistorySummaryText() {
        return historySummaryText;
    }

    public void discoverDevices() {
        deviceConnectionRepository.discoverDevices();
    }

    public void sendSample() {
        transferStatusText.postValue("Transfer: SENDING");
        fileTransferRepository.sendFiles(Collections.emptyList(), "127.0.0.1");
    }

    public void receiveSample() {
        transferStatusText.postValue("Transfer: RECEIVING");
        fileTransferRepository.receiveFiles("/tmp/sml-receiver");
    }

    private void handleTransferStatus(TransferStatusUpdate statusUpdate) {
        TransferExecutionStatus status = statusUpdate.getStatus();
        String message = statusUpdate.getUserMessage();
        if (message == null || message.trim().isEmpty()) {
            transferStatusText.postValue("Transfer: " + status.name());
            return;
        }
        transferStatusText.postValue("Transfer: " + status.name() + " - " + message);
    }

    private void handleTransferProgress(TransferProgress transferProgress) {
        String text = String.format(
                "Progress: %.2f%% (%.2f MB/s)",
                transferProgress.getProgressPercent(),
                transferProgress.getSpeedBytesPerSecond() / (1024.0 * 1024.0)
        );
        transferProgressText.postValue(text);
    }

    @Override
    protected void onCleared() {
        deviceConnectionRepository.removeConnectionStateObserver(connectionStateListener);
        fileTransferRepository.removeTransferStatusObserver(transferStatusListener);
        fileTransferRepository.removeTransferProgressObserver(transferProgressListener);
        transferHistoryRepository.removeTransferHistoryObserver(transferHistoryListener);
        super.onCleared();
    }
}
