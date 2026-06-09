package com.mrp.sml.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class RetryTransferWorker_Factory {
  private final Provider<TransferDao> transferDaoProvider;

  private final Provider<FileSender> fileSenderProvider;

  private final Provider<FileReceiver> fileReceiverProvider;

  private final Provider<SocketTransferManager> socketTransferManagerProvider;

  private RetryTransferWorker_Factory(Provider<TransferDao> transferDaoProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider) {
    this.transferDaoProvider = transferDaoProvider;
    this.fileSenderProvider = fileSenderProvider;
    this.fileReceiverProvider = fileReceiverProvider;
    this.socketTransferManagerProvider = socketTransferManagerProvider;
  }

  public RetryTransferWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, transferDaoProvider.get(), fileSenderProvider.get(), fileReceiverProvider.get(), socketTransferManagerProvider.get());
  }

  public static RetryTransferWorker_Factory create(Provider<TransferDao> transferDaoProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider) {
    return new RetryTransferWorker_Factory(transferDaoProvider, fileSenderProvider, fileReceiverProvider, socketTransferManagerProvider);
  }

  public static RetryTransferWorker newInstance(Context appContext, WorkerParameters workerParams,
      TransferDao transferDao, FileSender fileSender, FileReceiver fileReceiver,
      SocketTransferManager socketTransferManager) {
    return new RetryTransferWorker(appContext, workerParams, transferDao, fileSender, fileReceiver, socketTransferManager);
  }
}
