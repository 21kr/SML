package com.mrp.sml.data.di;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.data.local.TransferDao;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DataModule_ProvideTransferHistoryRepositoryFactory implements Factory<TransferHistoryRepository> {
  private final Provider<TransferDao> transferDaoProvider;

  private final Provider<DispatchersProvider> dispatchersProvider;

  public DataModule_ProvideTransferHistoryRepositoryFactory(
      Provider<TransferDao> transferDaoProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    this.transferDaoProvider = transferDaoProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public TransferHistoryRepository get() {
    return provideTransferHistoryRepository(transferDaoProvider.get(), dispatchersProvider.get());
  }

  public static DataModule_ProvideTransferHistoryRepositoryFactory create(
      Provider<TransferDao> transferDaoProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    return new DataModule_ProvideTransferHistoryRepositoryFactory(transferDaoProvider, dispatchersProvider);
  }

  public static TransferHistoryRepository provideTransferHistoryRepository(TransferDao transferDao,
      DispatchersProvider dispatchersProvider) {
    return Preconditions.checkNotNullFromProvides(DataModule.provideTransferHistoryRepository(transferDao, dispatchersProvider));
  }
}
