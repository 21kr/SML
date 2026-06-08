package com.mrp.sml.ui.transfer;

import com.mrp.sml.domain.usecase.FileTransferUseCase;
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
  private final Provider<FileTransferUseCase> fileTransferUseCaseProvider;

  public TransferViewModel_Factory(Provider<FileTransferUseCase> fileTransferUseCaseProvider) {
    this.fileTransferUseCaseProvider = fileTransferUseCaseProvider;
  }

  @Override
  public TransferViewModel get() {
    return newInstance(fileTransferUseCaseProvider.get());
  }

  public static TransferViewModel_Factory create(
      Provider<FileTransferUseCase> fileTransferUseCaseProvider) {
    return new TransferViewModel_Factory(fileTransferUseCaseProvider);
  }

  public static TransferViewModel newInstance(FileTransferUseCase fileTransferUseCase) {
    return new TransferViewModel(fileTransferUseCase);
  }
}
