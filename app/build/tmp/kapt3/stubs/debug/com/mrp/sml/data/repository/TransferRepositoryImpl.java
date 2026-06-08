package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.local.db.entities.TransferEntity;
import com.mrp.sml.data.mapper.TransferMapper;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.domain.model.TransferModel;
import com.mrp.sml.domain.repository.TransferRepository;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0016\u001a\u00020\u0017H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u001d0\u001cH\u0016J\u0018\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0017H\u0016J\b\u0010!\u001a\u00020\u0012H\u0016J\u0016\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010$J&\u0010%\u001a\u00020\u00122\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00170\u001d2\u0006\u0010\'\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0017H\u0016J&\u0010(\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0096@\u00a2\u0006\u0002\u0010-J(\u0010.\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u00010\u0017H\u0096@\u00a2\u0006\u0002\u00102R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/mrp/sml/data/repository/TransferRepositoryImpl;", "Lcom/mrp/sml/domain/repository/TransferRepository;", "transferDao", "Lcom/mrp/sml/data/local/db/dao/TransferDao;", "fileSender", "Lcom/mrp/sml/data/remote/sockets/FileSender;", "fileReceiver", "Lcom/mrp/sml/data/remote/sockets/FileReceiver;", "(Lcom/mrp/sml/data/local/db/dao/TransferDao;Lcom/mrp/sml/data/remote/sockets/FileSender;Lcom/mrp/sml/data/remote/sockets/FileReceiver;)V", "appContext", "Landroid/content/Context;", "getAppContext", "()Landroid/content/Context;", "appContext$delegate", "Lkotlin/Lazy;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "cancelTransfer", "", "clearHistory", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTransfer", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTransferById", "Lcom/mrp/sml/domain/model/TransferModel;", "observeTransfers", "Lkotlinx/coroutines/flow/Flow;", "", "receiveFiles", "outputDirectoryPath", "sessionToken", "resumeTransfer", "saveTransfer", "transfer", "(Lcom/mrp/sml/domain/model/TransferModel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendFiles", "filePaths", "destinationAddress", "updateTransferProgress", "progress", "", "speed", "", "(Ljava/lang/String;FDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTransferStatus", "status", "Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;", "error", "(Ljava/lang/String;Lcom/mrp/sml/domain/model/TransferModel$TransferStatus;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TransferRepositoryImpl implements com.mrp.sml.domain.repository.TransferRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.local.db.dao.TransferDao transferDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.FileSender fileSender = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.FileReceiver fileReceiver = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy appContext$delegate = null;
    
    @javax.inject.Inject()
    public TransferRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.dao.TransferDao transferDao, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.FileSender fileSender, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.FileReceiver fileReceiver) {
        super();
    }
    
    private final android.content.Context getAppContext() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.mrp.sml.domain.model.TransferModel>> observeTransfers() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getTransferById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.mrp.sml.domain.model.TransferModel> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveTransfer(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel transfer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateTransferStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel.TransferStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateTransferProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String id, float progress, double speed, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteTransfer(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object clearHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void sendFiles(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> filePaths, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken) {
    }
    
    @java.lang.Override()
    public void receiveFiles(@org.jetbrains.annotations.NotNull()
    java.lang.String outputDirectoryPath, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken) {
    }
    
    @java.lang.Override()
    public void cancelTransfer() {
    }
    
    @java.lang.Override()
    public void resumeTransfer() {
    }
}