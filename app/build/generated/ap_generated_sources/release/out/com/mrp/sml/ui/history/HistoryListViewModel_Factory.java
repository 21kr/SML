package com.mrp.sml.ui.history;

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
public final class HistoryListViewModel_Factory implements Factory<HistoryListViewModel> {
  private final Provider<TransferHistoryRepository> transferHistoryRepositoryProvider;

  public HistoryListViewModel_Factory(
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    this.transferHistoryRepositoryProvider = transferHistoryRepositoryProvider;
  }

  @Override
  public HistoryListViewModel get() {
    return newInstance(transferHistoryRepositoryProvider.get());
  }

  public static HistoryListViewModel_Factory create(
      Provider<TransferHistoryRepository> transferHistoryRepositoryProvider) {
    return new HistoryListViewModel_Factory(transferHistoryRepositoryProvider);
  }

  public static HistoryListViewModel newInstance(
      TransferHistoryRepository transferHistoryRepository) {
    return new HistoryListViewModel(transferHistoryRepository);
  }
}
