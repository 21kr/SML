package com.mrp.sml.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.mrp.sml.data.local.TransferDao;
import com.mrp.sml.data.local.TransferEntity;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultTransferHistoryRepository implements TransferHistoryRepository {

    private final TransferDao transferDao;

    @Inject
    public DefaultTransferHistoryRepository(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @Override
    public LiveData<List<TransferRecord>> observeTransferHistory() {
        return Transformations.map(transferDao.observeTransferHistory(), entities -> {
            List<TransferRecord> records = new ArrayList<>();
            if (entities == null) {
                return records;
            }
            for (TransferEntity entity : entities) {
                records.add(new TransferRecord(
                        entity.id,
                        entity.fileName,
                        entity.fileSizeBytes,
                        entity.mimeType,
                        TransferDirection.valueOf(entity.direction),
                        TransferStatus.valueOf(entity.status),
                        entity.timestampEpochMillis
                ));
            }
            return records;
        });
    }

    @Override
    public void saveTransferRecord(TransferRecord record) {
        TransferEntity entity = new TransferEntity(
                record.getFileName(),
                record.getFileSizeBytes(),
                record.getMimeType(),
                record.getDirection().name(),
                record.getStatus().name(),
                record.getTimestampEpochMillis()
        );
        transferDao.insert(entity);
    }
}
