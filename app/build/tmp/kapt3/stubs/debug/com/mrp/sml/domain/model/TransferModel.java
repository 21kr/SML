package com.mrp.sml.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b)\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0087\b\u0018\u00002\u00020\u0001:\u0002DEB\u008b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u000b\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0016H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0006H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\u000bH\u00c6\u0003J\t\u0010:\u001a\u00020\rH\u00c6\u0003J\t\u0010;\u001a\u00020\u000fH\u00c6\u0003J\t\u0010<\u001a\u00020\u0006H\u00c6\u0003J\u009e\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001\u00a2\u0006\u0002\u0010>J\u0013\u0010?\u001a\u00020@2\b\u0010A\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010B\u001a\u00020\u0016H\u00d6\u0001J\t\u0010C\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001eR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001eR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0010\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.\u00a8\u0006F"}, d2 = {"Lcom/mrp/sml/domain/model/TransferModel;", "", "id", "", "fileName", "fileSize", "", "mimeType", "direction", "Lcom/mrp/sml/domain/model/TransferModel$TransferDirection;", "status", "Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;", "progress", "", "speedBytesPerSecond", "", "startedAt", "completedAt", "errorMessage", "sessionToken", "peerDeviceName", "totalFiles", "", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Lcom/mrp/sml/domain/model/TransferModel$TransferDirection;Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;FDJLjava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getCompletedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getDirection", "()Lcom/mrp/sml/domain/model/TransferModel$TransferDirection;", "getErrorMessage", "()Ljava/lang/String;", "getFileName", "getFileSize", "()J", "getId", "getMimeType", "getPeerDeviceName", "getProgress", "()F", "getSessionToken", "getSpeedBytesPerSecond", "()D", "getStartedAt", "getStatus", "()Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;", "getTotalFiles", "()I", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Lcom/mrp/sml/domain/model/TransferModel$TransferDirection;Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;FDJLjava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lcom/mrp/sml/domain/model/TransferModel;", "equals", "", "other", "hashCode", "toString", "TransferDirection", "TransferStatus", "app_debug"})
public final class TransferModel {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fileName = null;
    private final long fileSize = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mimeType = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.model.TransferModel.TransferDirection direction = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.model.TransferModel.TransferStatus status = null;
    private final float progress = 0.0F;
    private final double speedBytesPerSecond = 0.0;
    private final long startedAt = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long completedAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sessionToken = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String peerDeviceName = null;
    private final int totalFiles = 0;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final int component14() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel.TransferDirection component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel.TransferStatus component6() {
        return null;
    }
    
    public final float component7() {
        return 0.0F;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, long fileSize, @org.jetbrains.annotations.NotNull()
    java.lang.String mimeType, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel.TransferDirection direction, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel.TransferStatus status, float progress, double speedBytesPerSecond, long startedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
    java.lang.String peerDeviceName, int totalFiles) {
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
    
    public TransferModel(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, long fileSize, @org.jetbrains.annotations.NotNull()
    java.lang.String mimeType, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel.TransferDirection direction, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel.TransferStatus status, float progress, double speedBytesPerSecond, long startedAt, @org.jetbrains.annotations.Nullable()
    java.lang.Long completedAt, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
    java.lang.String peerDeviceName, int totalFiles) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFileName() {
        return null;
    }
    
    public final long getFileSize() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMimeType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel.TransferDirection getDirection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel.TransferStatus getStatus() {
        return null;
    }
    
    public final float getProgress() {
        return 0.0F;
    }
    
    public final double getSpeedBytesPerSecond() {
        return 0.0;
    }
    
    public final long getStartedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCompletedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSessionToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPeerDeviceName() {
        return null;
    }
    
    public final int getTotalFiles() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/mrp/sml/domain/model/TransferModel$TransferDirection;", "", "(Ljava/lang/String;I)V", "SENT", "RECEIVED", "app_debug"})
    public static enum TransferDirection {
        /*public static final*/ SENT /* = new SENT() */,
        /*public static final*/ RECEIVED /* = new RECEIVED() */;
        
        TransferDirection() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.mrp.sml.domain.model.TransferModel.TransferDirection> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;", "", "(Ljava/lang/String;I)V", "PENDING", "TRANSFERRING", "PAUSED", "COMPLETED", "FAILED", "CANCELLED", "app_debug"})
    public static enum TransferStatus {
        /*public static final*/ PENDING /* = new PENDING() */,
        /*public static final*/ TRANSFERRING /* = new TRANSFERRING() */,
        /*public static final*/ PAUSED /* = new PAUSED() */,
        /*public static final*/ COMPLETED /* = new COMPLETED() */,
        /*public static final*/ FAILED /* = new FAILED() */,
        /*public static final*/ CANCELLED /* = new CANCELLED() */;
        
        TransferStatus() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.mrp.sml.domain.model.TransferModel.TransferStatus> getEntries() {
            return null;
        }
    }
}