package com.mrp.sml.ui.viewmodel;

import com.mrp.sml.data.remote.sockets.SocketTransferManager;
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
public final class TransferViewModel_Factory implements Factory<TransferViewModel> {
  private final Provider<TransferRepository> transferRepositoryProvider;

  private final Provider<SocketTransferManager> socketTransferManagerProvider;

  private TransferViewModel_Factory(Provider<TransferRepository> transferRepositoryProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
    this.socketTransferManagerProvider = socketTransferManagerProvider;
  }

  @Override
  public TransferViewModel get() {
    return newInstance(transferRepositoryProvider.get(), socketTransferManagerProvider.get());
  }

  public static TransferViewModel_Factory create(
      Provider<TransferRepository> transferRepositoryProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider) {
    return new TransferViewModel_Factory(transferRepositoryProvider, socketTransferManagerProvider);
  }

  public static TransferViewModel newInstance(TransferRepository transferRepository,
      SocketTransferManager socketTransferManager) {
    return new TransferViewModel(transferRepository, socketTransferManager);
  }
}
