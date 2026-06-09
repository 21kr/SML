package com.mrp.sml.workers;

import android.content.Context;
import androidx.hilt.work.HiltWorker;
import androidx.work.CoroutineWorker;
import androidx.work.Data;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import timber.log.Timber;
import java.io.File;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B;\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/mrp/sml/workers/RetryTransferWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "transferDao", "Lcom/mrp/sml/data/local/db/dao/TransferDao;", "fileSender", "Lcom/mrp/sml/data/remote/sockets/FileSender;", "fileReceiver", "Lcom/mrp/sml/data/remote/sockets/FileReceiver;", "socketTransferManager", "Lcom/mrp/sml/data/remote/sockets/SocketTransferManager;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lcom/mrp/sml/data/local/db/dao/TransferDao;Lcom/mrp/sml/data/remote/sockets/FileSender;Lcom/mrp/sml/data/remote/sockets/FileReceiver;Lcom/mrp/sml/data/remote/sockets/SocketTransferManager;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
@androidx.hilt.work.HiltWorker()
public final class RetryTransferWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.local.db.dao.TransferDao transferDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.FileSender fileSender = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.FileReceiver fileReceiver = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.SocketTransferManager socketTransferManager = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_TRANSFER_ID = "transfer_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.workers.RetryTransferWorker.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public RetryTransferWorker(@dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    android.content.Context appContext, @dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters workerParams, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.dao.TransferDao transferDao, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.FileSender fileSender, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.FileReceiver fileReceiver, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.SocketTransferManager socketTransferManager) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/mrp/sml/workers/RetryTransferWorker$Companion;", "", "()V", "KEY_TRANSFER_ID", "", "scheduleRetry", "", "context", "Landroid/content/Context;", "transferId", "delayMs", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void scheduleRetry(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String transferId, long delayMs) {
        }
    }
}