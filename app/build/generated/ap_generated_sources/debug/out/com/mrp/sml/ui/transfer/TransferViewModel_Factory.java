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
public final class TransferViewModel_Factory implements Factory<TransferViewModel> {
  private final Provider<FileTransferRepository> fileTransferRepositoryProvider;

  public TransferViewModel_Factory(
      Provider<FileTransferRepository> fileTransferRepositoryProvider) {
    this.fileTransferRepositoryProvider = fileTransferRepositoryProvider;
  }

  @Override
  public TransferViewModel get() {
    return newInstance(fileTransferRepositoryProvider.get());
  }

  public static TransferViewModel_Factory create(
      Provider<FileTransferRepository> fileTransferRepositoryProvider) {
    return new TransferViewModel_Factory(fileTransferRepositoryProvider);
  }

  public static TransferViewModel newInstance(FileTransferRepository fileTransferRepository) {
    return new TransferViewModel(fileTransferRepository);
  }
}
