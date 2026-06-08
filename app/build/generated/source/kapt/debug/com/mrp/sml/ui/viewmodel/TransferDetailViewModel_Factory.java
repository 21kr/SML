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
public final class TransferDetailViewModel_Factory implements Factory<TransferDetailViewModel> {
  private final Provider<TransferRepository> transferRepositoryProvider;

  private TransferDetailViewModel_Factory(Provider<TransferRepository> transferRepositoryProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
  }

  @Override
  public TransferDetailViewModel get() {
    return newInstance(transferRepositoryProvider.get());
  }

  public static TransferDetailViewModel_Factory create(
      Provider<TransferRepository> transferRepositoryProvider) {
    return new TransferDetailViewModel_Factory(transferRepositoryProvider);
  }

  public static TransferDetailViewModel newInstance(TransferRepository transferRepository) {
    return new TransferDetailViewModel(transferRepository);
  }
}
