package com.mrp.sml.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferExecutionStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultFileTransferRepository implements FileTransferRepository {

    private final DispatchersProvider dispatchersProvider;
    private final TransferHistoryRepository transferHistoryRepository;
    private final MutableLiveData<TransferProgress> progressLiveData =
            new MutableLiveData<>(new TransferProgress(0L, 0L, 0.0, 0f));
    private final MutableLiveData<TransferStatusUpdate> statusLiveData =
            new MutableLiveData<>(new TransferStatusUpdate(TransferExecutionStatus.IDLE, null));

    @Inject
    public DefaultFileTransferRepository(
            DispatchersProvider dispatchersProvider,
            TransferHistoryRepository transferHistoryRepository
    ) {
        this.dispatchersProvider = dispatchersProvider;
        this.transferHistoryRepository = transferHistoryRepository;
    }

    @Override
    public LiveData<TransferProgress> observeTransferProgress() {
        return progressLiveData;
    }

    @Override
    public LiveData<TransferStatusUpdate> observeTransferStatus() {
        return statusLiveData;
    }

    @Override
    public void sendFile(String sourcePath, String destinationAddress) {
        sendFiles(Collections.singletonList(sourcePath), destinationAddress);
    }

    @Override
    public void sendFiles(List<String> sourcePaths, String destinationAddress) {
        statusLiveData.postValue(new TransferStatusUpdate(TransferExecutionStatus.SENDING, "Preparing files"));
        dispatchersProvider.ioExecutor().execute(() -> {
            long totalBytes = 0L;
            for (String sourcePath : sourcePaths) {
                File file = new File(sourcePath);
                if (file.exists() && file.isFile()) {
                    totalBytes += file.length();
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
            }
            progressLiveData.postValue(new TransferProgress(totalBytes, totalBytes, 0.0, 100f));
            statusLiveData.postValue(new TransferStatusUpdate(TransferExecutionStatus.COMPLETED, "Files marked as sent"));
        });
    }

    @Override
    public void receiveFile(String destinationPath) {
        receiveFiles(destinationPath);
    }

    @Override
    public void receiveFiles(String destinationDirectoryPath) {
        statusLiveData.postValue(new TransferStatusUpdate(TransferExecutionStatus.RECEIVING, "Waiting for files"));
        dispatchersProvider.ioExecutor().execute(() -> {
            progressLiveData.postValue(new TransferProgress(0L, 0L, 0.0, 0f));
            statusLiveData.postValue(new TransferStatusUpdate(TransferExecutionStatus.IDLE, "Receiver ready"));
        });
    }
}
