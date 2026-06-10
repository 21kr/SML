package com.mrp.sml.ui.viewmodel;

import android.content.Context;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import com.mrp.sml.domain.repository.TransferRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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

  private final Provider<Context> contextProvider;

  private TransferViewModel_Factory(Provider<TransferRepository> transferRepositoryProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider,
      Provider<Context> contextProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
    this.socketTransferManagerProvider = socketTransferManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public TransferViewModel get() {
    return newInstance(transferRepositoryProvider.get(), socketTransferManagerProvider.get(), contextProvider.get());
  }

  public static TransferViewModel_Factory create(
      Provider<TransferRepository> transferRepositoryProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider,
      Provider<Context> contextProvider) {
    return new TransferViewModel_Factory(transferRepositoryProvider, socketTransferManagerProvider, contextProvider);
  }

  public static TransferViewModel newInstance(TransferRepository transferRepository,
      SocketTransferManager socketTransferManager, Context context) {
    return new TransferViewModel(transferRepository, socketTransferManager, context);
  }
}
