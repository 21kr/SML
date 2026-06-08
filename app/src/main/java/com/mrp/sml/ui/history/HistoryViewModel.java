package com.mrp.sml.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.usecase.ObserveTransferHistoryUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final ObserveTransferHistoryUseCase observeTransferHistoryUseCase;

    private final MutableLiveData<String> historySummaryText = new MutableLiveData<>("History: 0 records");

    private final TransferHistoryRepository.TransferHistoryListener historyListener = this::onHistoryChanged;

    @Inject
    public HistoryViewModel(ObserveTransferHistoryUseCase observeTransferHistoryUseCase) {
        this.observeTransferHistoryUseCase = observeTransferHistoryUseCase;
        this.observeTransferHistoryUseCase.observe(historyListener);
    }

    public LiveData<String> getHistorySummaryText() {
        return historySummaryText;
    }

    private void onHistoryChanged(List<TransferRecord> history) {
        if (history == null || history.isEmpty()) {
            historySummaryText.postValue("History: 0 records");
            return;
        }

        TransferRecord latest = history.get(0);
        String summary = "History: " + history.size() + " records (latest: " + latest.getFileName() + ")";
        historySummaryText.postValue(summary);
    }

    @Override
    protected void onCleared() {
        observeTransferHistoryUseCase.removeObserver(historyListener);
        super.onCleared();
    }
}
