package com.mrp.sml.data.di;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.repository.FileTransferRepository;
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
public final class DataModule_ProvideFileTransferRepositoryFactory implements Factory<FileTransferRepository> {
  private final Provider<DispatchersProvider> dispatchersProvider;

  private final Provider<TransferHistoryRepository> transferHistoryRepositoryProvider;

  public DataModule_ProvideFileTransferRepositoryFactory(
      Provider<DispatchersProvider> dispatchersProvider,
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    this.dispatchersProvider = dispatchersProvider;
    this.transferHistoryRepositoryProvider = transferHistoryRepositoryProvider;
  }

  @Override
  public FileTransferRepository get() {
    return provideFileTransferRepository(dispatchersProvider.get(), transferHistoryRepositoryProvider.get());
  }

  public static DataModule_ProvideFileTransferRepositoryFactory create(
      Provider<DispatchersProvider> dispatchersProvider,
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    return new DataModule_ProvideFileTransferRepositoryFactory(dispatchersProvider, transferHistoryRepositoryProvider);
  }

  public static FileTransferRepository provideFileTransferRepository(
      DispatchersProvider dispatchersProvider,
      TransferHistoryRepository transferHistoryRepository) {
    return Preconditions.checkNotNullFromProvides(DataModule.provideFileTransferRepository(dispatchersProvider, transferHistoryRepository));
  }
}
