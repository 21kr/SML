package com.mrp.sml.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.models.TransferFile;
import com.mrp.sml.domain.repository.ConnectionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000fJ\b\u0010\u0012\u001a\u00020\rH\u0014J\u0006\u0010\u0013\u001a\u00020\rJ\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\rJ\u0006\u0010\u0018\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/ReceiveViewModel;", "Landroidx/lifecycle/ViewModel;", "connectionRepository", "Lcom/mrp/sml/domain/repository/ConnectionRepository;", "(Lcom/mrp/sml/domain/repository/ConnectionRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/ui/viewmodel/ReceiveUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "acceptTransfer", "", "sessionId", "", "connectToDevice", "deviceId", "onCleared", "rejectTransfer", "setIncomingRequest", "request", "Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "startListening", "stopListening", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ReceiveViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.ConnectionRepository connectionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.viewmodel.ReceiveUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.ReceiveUiState> uiState = null;
    
    @javax.inject.Inject()
    public ReceiveViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.ConnectionRepository connectionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.ReceiveUiState> getUiState() {
        return null;
    }
    
    public final void startListening() {
    }
    
    public final void stopListening() {
    }
    
    public final void connectToDevice(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    public final void acceptTransfer(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId) {
    }
    
    public final void rejectTransfer() {
    }
    
    public final void setIncomingRequest(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.IncomingTransferRequest request) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}