package com.mrp.sml.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.utils.QrCodeUtils;
import com.mrp.sml.core.utils.WifiUtils;
import com.mrp.sml.domain.repository.ConnectionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\tJ\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\tJ\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\tJ\u0006\u0010\u0016\u001a\u00020\tJ\b\u0010\u0017\u001a\u00020\u000fH\u0014J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\tJ\u0006\u0010 \u001a\u00020\u000fJ\u0006\u0010!\u001a\u00020\u000fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\""}, d2 = {"Lcom/mrp/sml/ui/viewmodel/DiscoveryViewModel;", "Landroidx/lifecycle/ViewModel;", "connectionRepository", "Lcom/mrp/sml/domain/repository/ConnectionRepository;", "(Lcom/mrp/sml/domain/repository/ConnectionRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/ui/viewmodel/PairingUiState;", "sessionToken", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearQrCode", "", "connectToDevice", "deviceId", "generateQrCode", "payload", "generateQrCodeForSender", "senderIp", "getSessionToken", "onCleared", "setConnectionMethod", "method", "Lcom/mrp/sml/ui/viewmodel/PairingMode;", "setMode", "mode", "Lcom/mrp/sml/ui/viewmodel/PairingRole;", "setSelectedFileSummary", "summary", "startDiscovery", "stopDiscovery", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DiscoveryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.ConnectionRepository connectionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.viewmodel.PairingUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.PairingUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sessionToken;
    
    @javax.inject.Inject()
    public DiscoveryViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.ConnectionRepository connectionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.PairingUiState> getUiState() {
        return null;
    }
    
    public final void setMode(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingRole mode) {
    }
    
    public final void setSelectedFileSummary(@org.jetbrains.annotations.NotNull()
    java.lang.String summary) {
    }
    
    public final void startDiscovery() {
    }
    
    public final void stopDiscovery() {
    }
    
    public final void connectToDevice(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    public final void setConnectionMethod(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingMode method) {
    }
    
    public final void generateQrCodeForSender(@org.jetbrains.annotations.NotNull()
    java.lang.String senderIp) {
    }
    
    public final void generateQrCode(@org.jetbrains.annotations.NotNull()
    java.lang.String payload) {
    }
    
    public final void clearQrCode() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionToken() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}