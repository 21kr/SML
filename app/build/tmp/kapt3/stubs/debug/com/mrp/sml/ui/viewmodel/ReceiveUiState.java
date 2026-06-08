package com.mrp.sml.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.models.TransferFile;
import com.mrp.sml.domain.repository.ConnectionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\nH\u00c6\u0003J9\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0012\u00a8\u0006\u001e"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/ReceiveUiState;", "", "connectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "discoveredDevices", "", "Lcom/mrp/sml/core/models/Device;", "isScanning", "", "incomingRequest", "Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "(Lcom/mrp/sml/core/models/ConnectionState;Ljava/util/List;ZLcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;)V", "getConnectionState", "()Lcom/mrp/sml/core/models/ConnectionState;", "getDiscoveredDevices", "()Ljava/util/List;", "getIncomingRequest", "()Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ReceiveUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.core.models.ConnectionState connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.mrp.sml.core.models.Device> discoveredDevices = null;
    private final boolean isScanning = false;
    @org.jetbrains.annotations.Nullable()
    private final com.mrp.sml.ui.viewmodel.IncomingTransferRequest incomingRequest = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.ConnectionState component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.mrp.sml.core.models.Device> component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.mrp.sml.ui.viewmodel.IncomingTransferRequest component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.ReceiveUiState copy(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.ConnectionState connectionState, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, boolean isScanning, @org.jetbrains.annotations.Nullable()
    com.mrp.sml.ui.viewmodel.IncomingTransferRequest incomingRequest) {
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
    
    public ReceiveUiState(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.ConnectionState connectionState, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, boolean isScanning, @org.jetbrains.annotations.Nullable()
    com.mrp.sml.ui.viewmodel.IncomingTransferRequest incomingRequest) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.ConnectionState getConnectionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.mrp.sml.core.models.Device> getDiscoveredDevices() {
        return null;
    }
    
    public final boolean isScanning() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.mrp.sml.ui.viewmodel.IncomingTransferRequest getIncomingRequest() {
        return null;
    }
    
    public ReceiveUiState() {
        super();
    }
}