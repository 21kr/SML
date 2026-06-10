package com.mrp.sml.ui.viewmodel;

import android.graphics.Bitmap;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.constants.TransferConstants;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.models.TransferFile;
import com.mrp.sml.core.utils.QrCodeUtils;
import com.mrp.sml.core.utils.WifiUtils;
import com.mrp.sml.data.remote.hotspot.HotspotManager;
import com.mrp.sml.domain.repository.ConnectionRepository;
import com.mrp.sml.domain.repository.TransferRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0013J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0014J\u0006\u0010\u001a\u001a\u00020\u0011J\u000e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u0011J\u0006\u0010\u001f\u001a\u00020\u0011J\u0006\u0010 \u001a\u00020\u0011R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006!"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/ReceiveViewModel;", "Landroidx/lifecycle/ViewModel;", "connectionRepository", "Lcom/mrp/sml/domain/repository/ConnectionRepository;", "transferRepository", "Lcom/mrp/sml/domain/repository/TransferRepository;", "hotspotManager", "Lcom/mrp/sml/data/remote/hotspot/HotspotManager;", "(Lcom/mrp/sml/domain/repository/ConnectionRepository;Lcom/mrp/sml/domain/repository/TransferRepository;Lcom/mrp/sml/data/remote/hotspot/HotspotManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/ui/viewmodel/ReceiveUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "acceptTransfer", "", "sessionId", "", "connectToDevice", "deviceId", "generateReceiverQrCode", "useHotspot", "", "onCleared", "rejectTransfer", "setIncomingRequest", "request", "Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "startHotspotAndListen", "startListening", "stopListening", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ReceiveViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.ConnectionRepository connectionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.TransferRepository transferRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.hotspot.HotspotManager hotspotManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.viewmodel.ReceiveUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.ReceiveUiState> uiState = null;
    
    @javax.inject.Inject()
    public ReceiveViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.ConnectionRepository connectionRepository, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.TransferRepository transferRepository, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.hotspot.HotspotManager hotspotManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.ReceiveUiState> getUiState() {
        return null;
    }
    
    public final void startListening() {
    }
    
    public final void startHotspotAndListen() {
    }
    
    public final void stopListening() {
    }
    
    public final void connectToDevice(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    private final void generateReceiverQrCode(boolean useHotspot) {
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