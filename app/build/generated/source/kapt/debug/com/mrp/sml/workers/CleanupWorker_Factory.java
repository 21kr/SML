package com.mrp.sml.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.mrp.sml.data.local.db.dao.TransferDao;
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
public final class CleanupWorker_Factory {
  private final Provider<TransferDao> transferDaoProvider;

  private CleanupWorker_Factory(Provider<TransferDao> transferDaoProvider) {
    this.transferDaoProvider = transferDaoProvider;
  }

  public CleanupWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, transferDaoProvider.get());
  }

  public static CleanupWorker_Factory create(Provider<TransferDao> transferDaoProvider) {
    return new CleanupWorker_Factory(transferDaoProvider);
  }

  public static CleanupWorker newInstance(Context appContext, WorkerParameters workerParams,
      TransferDao transferDao) {
    return new CleanupWorker(appContext, workerParams, transferDao);
  }
}
