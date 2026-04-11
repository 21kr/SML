package com.mrp.sml.ui.transfer;

import com.mrp.sml.domain.repository.FileTransferRepository;
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
public final class TransferProgressViewModel_Factory implements Factory<TransferProgressViewModel> {
  private final Provider<FileTransferRepository> fileTransferRepositoryProvider;

  public TransferProgressViewModel_Factory(
      Provider<FileTransferRepository> fileTransferRepositoryProvider) {
    this.fileTransferRepositoryProvider = fileTransferRepositoryProvider;
  }

  @Override
  public TransferProgressViewModel get() {
    return newInstance(fileTransferRepositoryProvider.get());
  }

  public static TransferProgressViewModel_Factory create(
      Provider<FileTransferRepository> fileTransferRepositoryProvider) {
    return new TransferProgressViewModel_Factory(fileTransferRepositoryProvider);
  }

  public static TransferProgressViewModel newInstance(
      FileTransferRepository fileTransferRepository) {
    return new TransferProgressViewModel(fileTransferRepository);
  }
}
