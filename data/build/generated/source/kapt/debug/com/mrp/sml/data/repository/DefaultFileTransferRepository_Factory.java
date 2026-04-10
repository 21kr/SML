package com.mrp.sml.data.repository;

import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
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
public final class DefaultFileTransferRepository_Factory implements Factory<DefaultFileTransferRepository> {
  private final Provider<DispatchersProvider> dispatchersProvider;

  private final Provider<TransferHistoryRepository> transferHistoryRepositoryProvider;

  public DefaultFileTransferRepository_Factory(Provider<DispatchersProvider> dispatchersProvider,
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    this.dispatchersProvider = dispatchersProvider;
    this.transferHistoryRepositoryProvider = transferHistoryRepositoryProvider;
  }

  @Override
  public DefaultFileTransferRepository get() {
    return newInstance(dispatchersProvider.get(), transferHistoryRepositoryProvider.get());
  }

  public static DefaultFileTransferRepository_Factory create(
      Provider<DispatchersProvider> dispatchersProvider,
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    return new DefaultFileTransferRepository_Factory(dispatchersProvider, transferHistoryRepositoryProvider);
  }

  public static DefaultFileTransferRepository newInstance(DispatchersProvider dispatchersProvider,
      TransferHistoryRepository transferHistoryRepository) {
    return new DefaultFileTransferRepository(dispatchersProvider, transferHistoryRepository);
  }
}
