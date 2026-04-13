package com.mrp.sml.data.repository;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.data.local.TransferDao;
import com.mrp.sml.data.local.TransferEntity;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultTransferHistoryRepository implements TransferHistoryRepository {

    private final TransferDao transferDao;
    private final DispatchersProvider dispatchersProvider;
    private final CopyOnWriteArrayList<TransferHistoryListener> listeners = new CopyOnWriteArrayList<>();
    private volatile List<TransferRecord> cachedHistory = new ArrayList<>();

    @Inject
    public DefaultTransferHistoryRepository(TransferDao transferDao, DispatchersProvider dispatchersProvider) {
        this.transferDao = transferDao;
        this.dispatchersProvider = dispatchersProvider;
        refreshHistoryAsync();
    }

    @Override
    public List<TransferRecord> getTransferHistory() {
        return new ArrayList<>(cachedHistory);
    }

    @Override
    public void saveTransferRecord(TransferRecord record) {
        dispatchersProvider.ioExecutor().execute(() -> {
            TransferEntity entity = new TransferEntity(
                    record.getFileName(),
                    record.getFileSizeBytes(),
                    record.getMimeType(),
                    record.getDirection().name(),
                    record.getStatus().name(),
                    record.getTimestampEpochMillis()
            );
            transferDao.insert(entity);
            refreshHistoryAsync();
        });
    }

    @Override
    public void observeTransferHistory(TransferHistoryListener listener) {
        listeners.addIfAbsent(listener);
        listener.onHistoryChanged(Collections.unmodifiableList(new ArrayList<>(cachedHistory)));
        refreshHistoryAsync();
    }

    @Override
    public void removeTransferHistoryObserver(TransferHistoryListener listener) {
        listeners.remove(listener);
    }

    private void refreshHistoryAsync() {
        dispatchersProvider.ioExecutor().execute(() -> {
            List<TransferEntity> entities = transferDao.getTransferHistory();
            List<TransferRecord> mapped = new ArrayList<>();
            if (entities != null) {
                for (TransferEntity entity : entities) {
                    try {
                        mapped.add(new TransferRecord(
                                entity.id,
                                entity.fileName,
                                entity.fileSizeBytes,
                                entity.mimeType,
                                TransferDirection.valueOf(entity.direction),
                                TransferStatus.valueOf(entity.status),
                                entity.timestampEpochMillis
                        ));
                    } catch (IllegalArgumentException enumParseException) {
                        // Skip corrupted rows so a single invalid record cannot crash app startup/history load.
                    }
                }
            }

            cachedHistory = mapped;
            dispatchersProvider.mainHandler().post(() -> {
                List<TransferRecord> snapshot = Collections.unmodifiableList(new ArrayList<>(cachedHistory));
                for (TransferHistoryListener historyListener : listeners) {
                    historyListener.onHistoryChanged(snapshot);
                }
            });
        });
    }
}
