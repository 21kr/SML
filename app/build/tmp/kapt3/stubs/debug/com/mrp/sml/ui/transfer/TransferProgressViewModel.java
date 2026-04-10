package com.mrp.sml.ui.transfer;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.FileTransferRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0002J\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0012"}, d2 = {"Lcom/mrp/sml/ui/transfer/TransferProgressViewModel;", "Landroidx/lifecycle/ViewModel;", "fileTransferRepository", "Lcom/mrp/sml/domain/repository/FileTransferRepository;", "(Lcom/mrp/sml/domain/repository/FileTransferRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/ui/transfer/TransferProgressUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "observeTransferProgress", "", "observeTransferStatus", "setDirection", "direction", "Lcom/mrp/sml/ui/transfer/TransferDirection;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TransferProgressViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.FileTransferRepository fileTransferRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.transfer.TransferProgressUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.transfer.TransferProgressUiState> uiState = null;
    
    @javax.inject.Inject()
    public TransferProgressViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.FileTransferRepository fileTransferRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.transfer.TransferProgressUiState> getUiState() {
        return null;
    }
    
    public final void setDirection(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.transfer.TransferDirection direction) {
    }
    
    private final void observeTransferProgress() {
    }
    
    private final void observeTransferStatus() {
    }
}