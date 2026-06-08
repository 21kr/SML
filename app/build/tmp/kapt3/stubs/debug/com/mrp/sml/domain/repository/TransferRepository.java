package com.mrp.sml.domain.repository;

import com.mrp.sml.domain.model.TransferModel;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u000e\u0010\u0004\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000e0\rH&J\b\u0010\u000f\u001a\u00020\u0003H&J\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\bH&J\b\u0010\u0013\u001a\u00020\u0003H&J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\bH&J\u0016\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u000bH\u00a6@\u00a2\u0006\u0002\u0010\u0018J&\u0010\u0019\u001a\u00020\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\bH&J&\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u00a6@\u00a2\u0006\u0002\u0010!J*\u0010\"\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010#\u001a\u00020$2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\bH\u00a6@\u00a2\u0006\u0002\u0010&\u00a8\u0006\'"}, d2 = {"Lcom/mrp/sml/domain/repository/TransferRepository;", "", "cancelTransfer", "", "clearHistory", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTransfer", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTransferById", "Lcom/mrp/sml/domain/model/TransferModel;", "observeTransfers", "Lkotlinx/coroutines/flow/Flow;", "", "pauseTransfer", "receiveFiles", "outputDirectoryPath", "sessionToken", "resumeTransfer", "retryTransfer", "sessionId", "saveTransfer", "transfer", "(Lcom/mrp/sml/domain/model/TransferModel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendFiles", "filePaths", "destinationAddress", "updateTransferProgress", "progress", "", "speed", "", "(Ljava/lang/String;FDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTransferStatus", "status", "Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;", "error", "(Ljava/lang/String;Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface TransferRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.mrp.sml.domain.model.TransferModel>> observeTransfers();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTransferById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.mrp.sml.domain.model.TransferModel> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveTransfer(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel transfer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTransferStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel.TransferStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTransferProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String id, float progress, double speed, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTransfer(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    public abstract void sendFiles(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> filePaths, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken);
    
    public abstract void receiveFiles(@org.jetbrains.annotations.NotNull()
    java.lang.String outputDirectoryPath, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken);
    
    public abstract void cancelTransfer();
    
    public abstract void pauseTransfer();
    
    public abstract void resumeTransfer();
    
    public abstract void retryTransfer(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}