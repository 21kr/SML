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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\f\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0013J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\t\u0010*\u001a\u00020\bH\u00c6\u0003J\t\u0010+\u001a\u00020\fH\u00c6\u0003J\t\u0010,\u001a\u00020\fH\u00c6\u0003J{\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\f2\b\b\u0002\u0010\u0011\u001a\u00020\f2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010.\u001a\u00020\b2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u000201H\u00d6\u0001J\t\u00102\u001a\u00020\fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u0010\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001eR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001e\u00a8\u00063"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/ReceiveUiState;", "", "connectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "discoveredDevices", "", "Lcom/mrp/sml/core/models/Device;", "isScanning", "", "incomingRequest", "Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "qrPayload", "", "qrBitmap", "Landroid/graphics/Bitmap;", "usingHotspot", "hotspotSsid", "hotspotPassword", "errorMessage", "(Lcom/mrp/sml/core/models/ConnectionState;Ljava/util/List;ZLcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;Ljava/lang/String;Landroid/graphics/Bitmap;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getConnectionState", "()Lcom/mrp/sml/core/models/ConnectionState;", "getDiscoveredDevices", "()Ljava/util/List;", "getErrorMessage", "()Ljava/lang/String;", "getHotspotPassword", "getHotspotSsid", "getIncomingRequest", "()Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "()Z", "getQrBitmap", "()Landroid/graphics/Bitmap;", "getQrPayload", "getUsingHotspot", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ReceiveUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.core.models.ConnectionState connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.mrp.sml.core.models.Device> discoveredDevices = null;
    private final boolean isScanning = false;
    @org.jetbrains.annotations.Nullable()
    private final com.mrp.sml.ui.viewmodel.IncomingTransferRequest incomingRequest = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String qrPayload = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.Bitmap qrBitmap = null;
    private final boolean usingHotspot = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String hotspotSsid = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String hotspotPassword = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.ConnectionState component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.ReceiveUiState copy(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.ConnectionState connectionState, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, boolean isScanning, @org.jetbrains.annotations.Nullable()
    com.mrp.sml.ui.viewmodel.IncomingTransferRequest incomingRequest, @org.jetbrains.annotations.Nullable()
    java.lang.String qrPayload, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap qrBitmap, boolean usingHotspot, @org.jetbrains.annotations.NotNull()
    java.lang.String hotspotSsid, @org.jetbrains.annotations.NotNull()
    java.lang.String hotspotPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
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
    com.mrp.sml.ui.viewmodel.IncomingTransferRequest incomingRequest, @org.jetbrains.annotations.Nullable()
    java.lang.String qrPayload, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap qrBitmap, boolean usingHotspot, @org.jetbrains.annotations.NotNull()
    java.lang.String hotspotSsid, @org.jetbrains.annotations.NotNull()
    java.lang.String hotspotPassword, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getQrPayload() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getQrBitmap() {
        return null;
    }
    
    public final boolean getUsingHotspot() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHotspotSsid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHotspotPassword() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public ReceiveUiState() {
        super();
    }
}