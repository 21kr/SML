package com.mrp.sml.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.model.TransferModel;
import com.mrp.sml.domain.repository.TransferRepository;
import com.mrp.sml.ui.screens.history.HistoryFilter;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0018"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/HistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "transferRepository", "Lcom/mrp/sml/domain/repository/TransferRepository;", "(Lcom/mrp/sml/domain/repository/TransferRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/ui/viewmodel/HistoryUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "applyFilter", "", "Lcom/mrp/sml/domain/model/TransferModel;", "transfers", "filter", "Lcom/mrp/sml/ui/screens/history/HistoryFilter;", "clearHistory", "", "retryTransfer", "sessionId", "", "setFilter", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.TransferRepository transferRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.viewmodel.HistoryUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.HistoryUiState> uiState = null;
    
    @javax.inject.Inject()
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.TransferRepository transferRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.HistoryUiState> getUiState() {
        return null;
    }
    
    public final void setFilter(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.screens.history.HistoryFilter filter) {
    }
    
    public final void clearHistory() {
    }
    
    public final void retryTransfer(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId) {
    }
    
    private final java.util.List<com.mrp.sml.domain.model.TransferModel> applyFilter(java.util.List<com.mrp.sml.domain.model.TransferModel> transfers, com.mrp.sml.ui.screens.history.HistoryFilter filter) {
        return null;
    }
}