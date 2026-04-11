package com.mrp.sml.data.repository;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferExecutionStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import com.mrp.sml.domain.repository.TransferProgress;
import com.mrp.sml.domain.repository.TransferStatusUpdate;
import kotlinx.coroutines.flow.Flow;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\n\b\u0007\u0018\u0000 ;2\u00020\u0001:\u0002;<B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\tH\u0002J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J0\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u001aH\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aH\u0016J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u001fJ$\u0010 \u001a\u00020\u001d2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\"2\u0006\u0010#\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010$J \u0010%\u001a\u00020\u001d2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\u0016\u0010&\u001a\u00020\u001d2\u0006\u0010\'\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010(\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010)\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u0012H\u0002J\u0018\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020\u000eH\u0002J4\u0010.\u001a\u00020\u001d2\u0006\u0010/\u001a\u00020\u000e2\u001c\u00100\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d02\u0012\u0006\u0012\u0004\u0018\u00010301H\u0082@\u00a2\u0006\u0002\u00104J\u001e\u00105\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u00107J$\u00108\u001a\u00020\u001d2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\"2\u0006\u0010#\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010$J\u0010\u00109\u001a\u00020+2\u0006\u0010\'\u001a\u00020\u000eH\u0002J\u0010\u0010:\u001a\u00020+2\u0006\u00106\u001a\u00020\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006="}, d2 = {"Lcom/mrp/sml/data/repository/DefaultFileTransferRepository;", "Lcom/mrp/sml/domain/repository/FileTransferRepository;", "dispatchersProvider", "Lcom/mrp/sml/core/common/DispatchersProvider;", "transferHistoryRepository", "Lcom/mrp/sml/domain/repository/TransferHistoryRepository;", "(Lcom/mrp/sml/core/common/DispatchersProvider;Lcom/mrp/sml/domain/repository/TransferHistoryRepository;)V", "transferProgress", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/domain/repository/TransferProgress;", "transferStatus", "Lcom/mrp/sml/domain/repository/TransferStatusUpdate;", "emptyTransferProgress", "friendlyError", "", "error", "Ljava/io/IOException;", "maybePublishProgress", "", "transferredBytes", "totalBytes", "startTimeMs", "lastPublishTimeMs", "force", "", "observeTransferProgress", "Lkotlinx/coroutines/flow/Flow;", "observeTransferStatus", "performReceiveFiles", "", "destinationDirectoryPath", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "performSendFiles", "sourcePaths", "", "destinationAddress", "(Ljava/util/List;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "publishProgress", "receiveFile", "destinationPath", "receiveFiles", "resetProgress", "resolveSafeOutputFile", "Ljava/io/File;", "destinationDirectory", "fileName", "runWithRetry", "operationName", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendFile", "sourcePath", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendFiles", "validateDestinationDirectory", "validateReadableFile", "Companion", "IncomingFile", "data_release"})
public final class DefaultFileTransferRepository implements com.mrp.sml.domain.repository.FileTransferRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.core.common.DispatchersProvider dispatchersProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.TransferHistoryRepository transferHistoryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.domain.repository.TransferProgress> transferProgress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.domain.repository.TransferStatusUpdate> transferStatus = null;
    private static final int DEFAULT_TRANSFER_PORT = 8988;
    private static final int BUFFER_SIZE_BYTES = 262144;
    private static final int SOCKET_TIMEOUT_MS = 20000;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long BASE_RETRY_DELAY_MS = 400L;
    private static final long PROGRESS_EMIT_INTERVAL_MS = 250L;
    @org.jetbrains.annotations.NotNull()
    private static final com.mrp.sml.data.repository.DefaultFileTransferRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public DefaultFileTransferRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.common.DispatchersProvider dispatchersProvider, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.TransferHistoryRepository transferHistoryRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.mrp.sml.domain.repository.TransferProgress> observeTransferProgress() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.mrp.sml.domain.repository.TransferStatusUpdate> observeTransferStatus() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object sendFile(@org.jetbrains.annotations.NotNull()
    java.lang.String sourcePath, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationAddress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object sendFiles(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> sourcePaths, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationAddress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object receiveFile(@org.jetbrains.annotations.NotNull()
    java.lang.String destinationPath, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object receiveFiles(@org.jetbrains.annotations.NotNull()
    java.lang.String destinationDirectoryPath, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object performSendFiles(java.util.List<java.lang.String> sourcePaths, java.lang.String destinationAddress, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object performReceiveFiles(java.lang.String destinationDirectoryPath, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object runWithRetry(java.lang.String operationName, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> block, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final long maybePublishProgress(long transferredBytes, long totalBytes, long startTimeMs, long lastPublishTimeMs, boolean force) {
        return 0L;
    }
    
    private final java.lang.String friendlyError(java.io.IOException error) {
        return null;
    }
    
    private final java.io.File validateReadableFile(java.lang.String sourcePath) {
        return null;
    }
    
    private final java.io.File validateDestinationDirectory(java.lang.String destinationPath) {
        return null;
    }
    
    private final java.io.File resolveSafeOutputFile(java.io.File destinationDirectory, java.lang.String fileName) {
        return null;
    }
    
    private final void publishProgress(long transferredBytes, long totalBytes, long startTimeMs) {
    }
    
    private final void resetProgress(long totalBytes) {
    }
    
    private final com.mrp.sml.domain.repository.TransferProgress emptyTransferProgress() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/mrp/sml/data/repository/DefaultFileTransferRepository$Companion;", "", "()V", "BASE_RETRY_DELAY_MS", "", "BUFFER_SIZE_BYTES", "", "DEFAULT_TRANSFER_PORT", "MAX_RETRY_ATTEMPTS", "PROGRESS_EMIT_INTERVAL_MS", "SOCKET_TIMEOUT_MS", "data_release"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/mrp/sml/data/repository/DefaultFileTransferRepository$IncomingFile;", "", "name", "", "sizeBytes", "", "(Ljava/lang/String;J)V", "getName", "()Ljava/lang/String;", "getSizeBytes", "()J", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "data_release"})
    static final class IncomingFile {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        private final long sizeBytes = 0L;
        
        public IncomingFile(@org.jetbrains.annotations.NotNull()
        java.lang.String name, long sizeBytes) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        public final long getSizeBytes() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mrp.sml.data.repository.DefaultFileTransferRepository.IncomingFile copy(@org.jetbrains.annotations.NotNull()
        java.lang.String name, long sizeBytes) {
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
}