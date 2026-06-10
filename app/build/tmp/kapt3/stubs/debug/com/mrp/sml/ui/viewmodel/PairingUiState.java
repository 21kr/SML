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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bk\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u0012J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010\'\u001a\u00020\rH\u00c6\u0003J\t\u0010(\u001a\u00020\u000fH\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003Jo\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010,\u001a\u00020\u000f2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020/H\u00d6\u0001J\t\u00100\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001a\u00a8\u00061"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/PairingUiState;", "", "mode", "Lcom/mrp/sml/ui/viewmodel/PairingRole;", "connectionMethod", "Lcom/mrp/sml/ui/viewmodel/PairingMode;", "qrPayload", "", "discoveredDevices", "", "Lcom/mrp/sml/core/models/Device;", "selectedDevice", "connectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "isDiscovering", "", "errorMessage", "selectedFileSummary", "(Lcom/mrp/sml/ui/viewmodel/PairingRole;Lcom/mrp/sml/ui/viewmodel/PairingMode;Ljava/lang/String;Ljava/util/List;Lcom/mrp/sml/core/models/Device;Lcom/mrp/sml/core/models/ConnectionState;ZLjava/lang/String;Ljava/lang/String;)V", "getConnectionMethod", "()Lcom/mrp/sml/ui/viewmodel/PairingMode;", "getConnectionState", "()Lcom/mrp/sml/core/models/ConnectionState;", "getDiscoveredDevices", "()Ljava/util/List;", "getErrorMessage", "()Ljava/lang/String;", "()Z", "getMode", "()Lcom/mrp/sml/ui/viewmodel/PairingRole;", "getQrPayload", "getSelectedDevice", "()Lcom/mrp/sml/core/models/Device;", "getSelectedFileSummary", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class PairingUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.ui.viewmodel.PairingRole mode = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.ui.viewmodel.PairingMode connectionMethod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String qrPayload = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.mrp.sml.core.models.Device> discoveredDevices = null;
    @org.jetbrains.annotations.Nullable()
    private final com.mrp.sml.core.models.Device selectedDevice = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.core.models.ConnectionState connectionState = null;
    private final boolean isDiscovering = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String selectedFileSummary = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.PairingRole component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.PairingMode component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.mrp.sml.core.models.Device> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.mrp.sml.core.models.Device component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.ConnectionState component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.PairingUiState copy(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingRole mode, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingMode connectionMethod, @org.jetbrains.annotations.Nullable()
    java.lang.String qrPayload, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, @org.jetbrains.annotations.Nullable()
    com.mrp.sml.core.models.Device selectedDevice, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.ConnectionState connectionState, boolean isDiscovering, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedFileSummary) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    public PairingUiState(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingRole mode, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingMode connectionMethod, @org.jetbrains.annotations.Nullable()
    java.lang.String qrPayload, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, @org.jetbrains.annotations.Nullable()
    com.mrp.sml.core.models.Device selectedDevice, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.ConnectionState connectionState, boolean isDiscovering, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedFileSummary) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.PairingRole getMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.PairingMode getConnectionMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getQrPayload() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.mrp.sml.core.models.Device> getDiscoveredDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.mrp.sml.core.models.Device getSelectedDevice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.ConnectionState getConnectionState() {
        return null;
    }
    
    public final boolean isDiscovering() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedFileSummary() {
        return null;
    }
    
    public PairingUiState() {
        super();
    }
}