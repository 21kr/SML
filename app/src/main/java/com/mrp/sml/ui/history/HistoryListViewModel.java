package com.mrp.sml.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.usecase.ObserveTransferHistoryUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class HistoryListViewModel extends ViewModel {

    private final ObserveTransferHistoryUseCase observeTransferHistoryUseCase;
    private final MutableLiveData<List<HistoryRecordAdapter.HistoryRow>> rows =
            new MutableLiveData<>(new ArrayList<>());

    private final TransferHistoryRepository.TransferHistoryListener historyListener = this::onHistoryChanged;

    @Inject
    public HistoryListViewModel(ObserveTransferHistoryUseCase observeTransferHistoryUseCase) {
        this.observeTransferHistoryUseCase = observeTransferHistoryUseCase;
        this.observeTransferHistoryUseCase.observe(historyListener);
    }

    public LiveData<List<HistoryRecordAdapter.HistoryRow>> getRows() {
        return rows;
    }

    private void onHistoryChanged(List<TransferRecord> history) {
        List<HistoryRecordAdapter.HistoryRow> mapped = new ArrayList<>();
        if (history != null) {
            for (TransferRecord record : history) {
                String title = record.getFileName();
                String subtitle = record.getDirection().name() + " • " + record.getStatus().name()
                        + " • " + record.getFileSizeBytes() + " bytes";
                mapped.add(new HistoryRecordAdapter.HistoryRow(title, subtitle));
            }
        }
        rows.postValue(mapped);
    }

    @Override
    protected void onCleared() {
        observeTransferHistoryUseCase.removeObserver(historyListener);
        super.onCleared();
    }
}
