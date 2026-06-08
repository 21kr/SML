package com.mrp.sml.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class RetryTransferWorker_AssistedFactory_Impl implements RetryTransferWorker_AssistedFactory {
  private final RetryTransferWorker_Factory delegateFactory;

  RetryTransferWorker_AssistedFactory_Impl(RetryTransferWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public RetryTransferWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<RetryTransferWorker_AssistedFactory> create(
      RetryTransferWorker_Factory delegateFactory) {
    return InstanceFactory.create(new RetryTransferWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<RetryTransferWorker_AssistedFactory> createFactoryProvider(
      RetryTransferWorker_Factory delegateFactory) {
    return InstanceFactory.create(new RetryTransferWorker_AssistedFactory_Impl(delegateFactory));
  }
}
