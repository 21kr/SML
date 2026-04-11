package com.mrp.sml.domain.repository;

import androidx.lifecycle.LiveData;
import com.mrp.sml.domain.model.TransferRecord;
import java.util.List;

public interface TransferHistoryRepository {
    LiveData<List<TransferRecord>> observeTransferHistory();
    void saveTransferRecord(TransferRecord record);
}
