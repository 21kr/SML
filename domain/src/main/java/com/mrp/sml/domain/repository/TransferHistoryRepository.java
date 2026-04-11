package com.mrp.sml.domain.repository;

import com.mrp.sml.domain.model.TransferRecord;
import java.util.List;

public interface TransferHistoryRepository {
    List<TransferRecord> getTransferHistory();
    void saveTransferRecord(TransferRecord record);

    void observeTransferHistory(TransferHistoryListener listener);
    void removeTransferHistoryObserver(TransferHistoryListener listener);

    interface TransferHistoryListener {
        void onHistoryChanged(List<TransferRecord> history);
    }
}
