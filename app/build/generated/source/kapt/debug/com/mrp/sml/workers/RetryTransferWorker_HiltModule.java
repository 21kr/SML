package com.mrp.sml.workers;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = RetryTransferWorker.class
)
public interface RetryTransferWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.mrp.sml.workers.RetryTransferWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      RetryTransferWorker_AssistedFactory factory);
}
