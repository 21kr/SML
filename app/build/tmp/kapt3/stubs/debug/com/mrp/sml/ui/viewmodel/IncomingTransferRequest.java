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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J-\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u0018"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/IncomingTransferRequest;", "", "deviceName", "", "files", "", "Lcom/mrp/sml/core/models/TransferFile;", "sessionId", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getDeviceName", "()Ljava/lang/String;", "getFiles", "()Ljava/util/List;", "getSessionId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class IncomingTransferRequest {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.mrp.sml.core.models.TransferFile> files = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionId = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.mrp.sml.core.models.TransferFile> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.IncomingTransferRequest copy(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.TransferFile> files, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionId) {
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
    
    public IncomingTransferRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    java.util.List<com.mrp.sml.core.models.TransferFile> files, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.mrp.sml.core.models.TransferFile> getFiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionId() {
        return null;
    }
}