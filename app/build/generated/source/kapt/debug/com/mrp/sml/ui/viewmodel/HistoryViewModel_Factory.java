package com.mrp.sml.ui.viewmodel;

import com.mrp.sml.domain.repository.TransferRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<TransferRepository> transferRepositoryProvider;

  private HistoryViewModel_Factory(Provider<TransferRepository> transferRepositoryProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(transferRepositoryProvider.get());
  }

  public static HistoryViewModel_Factory create(
      Provider<TransferRepository> transferRepositoryProvider) {
    return new HistoryViewModel_Factory(transferRepositoryProvider);
  }

  public static HistoryViewModel newInstance(TransferRepository transferRepository) {
    return new HistoryViewModel(transferRepository);
  }
}
