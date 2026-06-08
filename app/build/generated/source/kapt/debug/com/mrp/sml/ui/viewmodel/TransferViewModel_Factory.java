package com.mrp.sml.ui.viewmodel;

import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.domain.repository.TransferRepository;
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
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class TransferViewModel_Factory implements Factory<TransferViewModel> {
  private final Provider<TransferRepository> transferRepositoryProvider;

  private final Provider<FileSender> fileSenderProvider;

  private final Provider<FileReceiver> fileReceiverProvider;

  public TransferViewModel_Factory(Provider<TransferRepository> transferRepositoryProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
    this.fileSenderProvider = fileSenderProvider;
    this.fileReceiverProvider = fileReceiverProvider;
  }

  @Override
  public TransferViewModel get() {
    return newInstance(transferRepositoryProvider.get(), fileSenderProvider.get(), fileReceiverProvider.get());
  }

  public static TransferViewModel_Factory create(
      Provider<TransferRepository> transferRepositoryProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider) {
    return new TransferViewModel_Factory(transferRepositoryProvider, fileSenderProvider, fileReceiverProvider);
  }

  public static TransferViewModel newInstance(TransferRepository transferRepository,
      FileSender fileSender, FileReceiver fileReceiver) {
    return new TransferViewModel(transferRepository, fileSender, fileReceiver);
  }
}
