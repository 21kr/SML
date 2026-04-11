package com.mrp.sml.ui.transfer;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.domain.repository.FileTransferRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003JG\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011\u00a8\u0006\""}, d2 = {"Lcom/mrp/sml/ui/transfer/TransferProgressUiState;", "", "progressPercent", "", "progressPercentText", "", "speedText", "direction", "Lcom/mrp/sml/ui/transfer/TransferDirection;", "status", "userMessage", "(FLjava/lang/String;Ljava/lang/String;Lcom/mrp/sml/ui/transfer/TransferDirection;Ljava/lang/String;Ljava/lang/String;)V", "getDirection", "()Lcom/mrp/sml/ui/transfer/TransferDirection;", "getProgressPercent", "()F", "getProgressPercentText", "()Ljava/lang/String;", "getSpeedText", "getStatus", "getUserMessage", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
public final class TransferProgressUiState {
    private final float progressPercent = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String progressPercentText = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String speedText = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.ui.transfer.TransferDirection direction = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String userMessage = null;
    
    public TransferProgressUiState(float progressPercent, @org.jetbrains.annotations.NotNull()
    java.lang.String progressPercentText, @org.jetbrains.annotations.NotNull()
    java.lang.String speedText, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.transfer.TransferDirection direction, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String userMessage) {
        super();
    }
    
    public final float getProgressPercent() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProgressPercentText() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSpeedText() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.transfer.TransferDirection getDirection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUserMessage() {
        return null;
    }
    
    public TransferProgressUiState() {
        super();
    }
    
    public final float component1() {
        return 0.0F;
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
    public final com.mrp.sml.ui.transfer.TransferDirection component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.ui.transfer.TransferProgressUiState copy(float progressPercent, @org.jetbrains.annotations.NotNull()
    java.lang.String progressPercentText, @org.jetbrains.annotations.NotNull()
    java.lang.String speedText, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.transfer.TransferDirection direction, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String userMessage) {
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
}