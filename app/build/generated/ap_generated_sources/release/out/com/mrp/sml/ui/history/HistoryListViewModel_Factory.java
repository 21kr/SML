package com.mrp.sml.ui.history;

import com.mrp.sml.domain.usecase.ObserveTransferHistoryUseCase;
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
  private final Provider<ObserveTransferHistoryUseCase> observeTransferHistoryUseCaseProvider;

  public HistoryListViewModel_Factory(
      Provider<ObserveTransferHistoryUseCase> observeTransferHistoryUseCaseProvider) {
    this.observeTransferHistoryUseCaseProvider = observeTransferHistoryUseCaseProvider;
  }

  @Override
  public HistoryListViewModel get() {
    return newInstance(observeTransferHistoryUseCaseProvider.get());
  }

  public static HistoryListViewModel_Factory create(
      Provider<ObserveTransferHistoryUseCase> observeTransferHistoryUseCaseProvider) {
    return new HistoryListViewModel_Factory(observeTransferHistoryUseCaseProvider);
  }

  public static HistoryListViewModel newInstance(
      ObserveTransferHistoryUseCase observeTransferHistoryUseCase) {
    return new HistoryListViewModel(observeTransferHistoryUseCase);
  }
}
