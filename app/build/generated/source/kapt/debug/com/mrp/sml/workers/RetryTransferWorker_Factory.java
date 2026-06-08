package com.mrp.sml.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.remote.sockets.FileSender;
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

  private RetryTransferWorker_Factory(Provider<TransferDao> transferDaoProvider,
      Provider<FileSender> fileSenderProvider) {
    this.transferDaoProvider = transferDaoProvider;
    this.fileSenderProvider = fileSenderProvider;
  }

  public RetryTransferWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, transferDaoProvider.get(), fileSenderProvider.get());
  }

  public static RetryTransferWorker_Factory create(Provider<TransferDao> transferDaoProvider,
      Provider<FileSender> fileSenderProvider) {
    return new RetryTransferWorker_Factory(transferDaoProvider, fileSenderProvider);
  }

  public static RetryTransferWorker newInstance(Context appContext, WorkerParameters workerParams,
      TransferDao transferDao, FileSender fileSender) {
    return new RetryTransferWorker(appContext, workerParams, transferDao, fileSender);
  }
}
