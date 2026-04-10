package com.mrp.sml.ui.transfer;

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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<TransferHistoryRepository> transferHistoryRepositoryProvider;

  public HistoryViewModel_Factory(
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    this.transferHistoryRepositoryProvider = transferHistoryRepositoryProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(transferHistoryRepositoryProvider.get());
  }

  public static HistoryViewModel_Factory create(
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    return new HistoryViewModel_Factory(transferHistoryRepositoryProvider);
  }

  public static HistoryViewModel newInstance(TransferHistoryRepository transferHistoryRepository) {
    return new HistoryViewModel(transferHistoryRepository);
  }
}
