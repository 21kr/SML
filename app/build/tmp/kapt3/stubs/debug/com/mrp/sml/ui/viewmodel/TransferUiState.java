package com.mrp.sml.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.TransferProgress;
import com.mrp.sml.core.models.TransferStatus;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import com.mrp.sml.domain.model.TransferModel;
import com.mrp.sml.domain.repository.TransferRepository;
import com.mrp.sml.services.TransferForegroundService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b/\b\u0087\b\u0018\u00002\u00020\u0001B\u00a7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\b\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0002\u0010\u0019J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0010H\u00c6\u0003J\t\u00103\u001a\u00020\u000bH\u00c6\u0003J\t\u00104\u001a\u00020\u0013H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\bH\u00c6\u0003J\t\u00107\u001a\u00020\u0017H\u00c6\u0003J\t\u00108\u001a\u00020\u0017H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\bH\u00c6\u0003J\t\u0010=\u001a\u00020\bH\u00c6\u0003J\t\u0010>\u001a\u00020\u000bH\u00c6\u0003J\t\u0010?\u001a\u00020\u000bH\u00c6\u0003J\t\u0010@\u001a\u00020\u000eH\u00c6\u0003J\u00ab\u0001\u0010A\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\b2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0017H\u00c6\u0001J\u0013\u0010B\u001a\u00020\u00172\b\u0010C\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010D\u001a\u00020\bH\u00d6\u0001J\t\u0010E\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010 R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u0011\u0010\u0011\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010 R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\u0015\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001eR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010$\u00a8\u0006F"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/TransferUiState;", "", "sessionId", "", "direction", "peerDevice", "currentFileName", "currentFileIndex", "", "totalFiles", "transferredBytes", "", "totalBytes", "progressPercent", "", "speed", "", "eta", "status", "Lcom/mrp/sml/core/models/TransferStatus;", "errorMessage", "retryAttempt", "canPause", "", "canResume", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIJJFDJLcom/mrp/sml/core/models/TransferStatus;Ljava/lang/String;IZZ)V", "getCanPause", "()Z", "getCanResume", "getCurrentFileIndex", "()I", "getCurrentFileName", "()Ljava/lang/String;", "getDirection", "getErrorMessage", "getEta", "()J", "getPeerDevice", "getProgressPercent", "()F", "getRetryAttempt", "getSessionId", "getSpeed", "()D", "getStatus", "()Lcom/mrp/sml/core/models/TransferStatus;", "getTotalBytes", "getTotalFiles", "getTransferredBytes", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class TransferUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String direction = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String peerDevice = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String currentFileName = null;
    private final int currentFileIndex = 0;
    private final int totalFiles = 0;
    private final long transferredBytes = 0L;
    private final long totalBytes = 0L;
    private final float progressPercent = 0.0F;
    private final double speed = 0.0;
    private final long eta = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.core.models.TransferStatus status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    private final int retryAttempt = 0;
    private final boolean canPause = false;
    private final boolean canResume = false;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    public final long component11() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.TransferStatus component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final boolean component15() {
        return false;
    }
    
    public final boolean component16() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.viewmodel.TransferUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String direction, @org.jetbrains.annotations.NotNull()
    java.lang.String peerDevice, @org.jetbrains.annotations.NotNull()
    java.lang.String currentFileName, int currentFileIndex, int totalFiles, long transferredBytes, long totalBytes, float progressPercent, double speed, long eta, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.TransferStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, int retryAttempt, boolean canPause, boolean canResume) {
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
    
    public TransferUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String direction, @org.jetbrains.annotations.NotNull()
    java.lang.String peerDevice, @org.jetbrains.annotations.NotNull()
    java.lang.String currentFileName, int currentFileIndex, int totalFiles, long transferredBytes, long totalBytes, float progressPercent, double speed, long eta, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.TransferStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, int retryAttempt, boolean canPause, boolean canResume) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDirection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPeerDevice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentFileName() {
        return null;
    }
    
    public final int getCurrentFileIndex() {
        return 0;
    }
    
    public final int getTotalFiles() {
        return 0;
    }
    
    public final long getTransferredBytes() {
        return 0L;
    }
    
    public final long getTotalBytes() {
        return 0L;
    }
    
    public final float getProgressPercent() {
        return 0.0F;
    }
    
    public final double getSpeed() {
        return 0.0;
    }
    
    public final long getEta() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.TransferStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public final int getRetryAttempt() {
        return 0;
    }
    
    public final boolean getCanPause() {
        return false;
    }
    
    public final boolean getCanResume() {
        return false;
    }
    
    public TransferUiState() {
        super();
    }
}