package com.mrp.sml.domain.usecase;

import com.mrp.sml.domain.repository.TransferHistoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "KotlinInternalInJava"
})
public final class ObserveTransferHistoryUseCase_Factory implements Factory<ObserveTransferHistoryUseCase> {
  private final Provider<TransferHistoryRepository> transferHistoryRepositoryProvider;

  public ObserveTransferHistoryUseCase_Factory(
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    this.transferHistoryRepositoryProvider = transferHistoryRepositoryProvider;
  }

  @Override
  public ObserveTransferHistoryUseCase get() {
    return newInstance(transferHistoryRepositoryProvider.get());
  }

  public static ObserveTransferHistoryUseCase_Factory create(
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    return new ObserveTransferHistoryUseCase_Factory(transferHistoryRepositoryProvider);
  }

  public static ObserveTransferHistoryUseCase newInstance(
      TransferHistoryRepository transferHistoryRepository) {
    return new ObserveTransferHistoryUseCase(transferHistoryRepository);
  }
}
