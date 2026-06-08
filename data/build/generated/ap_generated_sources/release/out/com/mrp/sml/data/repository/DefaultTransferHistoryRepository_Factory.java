package com.mrp.sml.data.repository;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.data.local.TransferDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
    "KotlinInternalInJava"
})
public final class DefaultTransferHistoryRepository_Factory implements Factory<DefaultTransferHistoryRepository> {
  private final Provider<TransferDao> transferDaoProvider;

  private final Provider<DispatchersProvider> dispatchersProvider;

  public DefaultTransferHistoryRepository_Factory(Provider<TransferDao> transferDaoProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    this.transferDaoProvider = transferDaoProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public DefaultTransferHistoryRepository get() {
    return newInstance(transferDaoProvider.get(), dispatchersProvider.get());
  }

  public static DefaultTransferHistoryRepository_Factory create(
      Provider<TransferDao> transferDaoProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    return new DefaultTransferHistoryRepository_Factory(transferDaoProvider, dispatchersProvider);
  }

  public static DefaultTransferHistoryRepository newInstance(TransferDao transferDao,
      DispatchersProvider dispatchersProvider) {
    return new DefaultTransferHistoryRepository(transferDao, dispatchersProvider);
  }
}
