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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<ObserveTransferHistoryUseCase> observeTransferHistoryUseCaseProvider;

  public HistoryViewModel_Factory(
      Provider<ObserveTransferHistoryUseCase> observeTransferHistoryUseCaseProvider) {
    this.observeTransferHistoryUseCaseProvider = observeTransferHistoryUseCaseProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(observeTransferHistoryUseCaseProvider.get());
  }

  public static HistoryViewModel_Factory create(
      Provider<ObserveTransferHistoryUseCase> observeTransferHistoryUseCaseProvider) {
    return new HistoryViewModel_Factory(observeTransferHistoryUseCaseProvider);
  }

  public static HistoryViewModel newInstance(
      ObserveTransferHistoryUseCase observeTransferHistoryUseCase) {
    return new HistoryViewModel(observeTransferHistoryUseCase);
  }
}
