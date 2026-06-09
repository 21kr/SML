package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.local.db.dao.TransferProgressDao;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class TransferRepositoryImpl_Factory implements Factory<TransferRepositoryImpl> {
  private final Provider<TransferDao> transferDaoProvider;

  private final Provider<TransferProgressDao> transferProgressDaoProvider;

  private final Provider<FileSender> fileSenderProvider;

  private final Provider<FileReceiver> fileReceiverProvider;

  private final Provider<SocketTransferManager> socketTransferManagerProvider;

  private TransferRepositoryImpl_Factory(Provider<TransferDao> transferDaoProvider,
      Provider<TransferProgressDao> transferProgressDaoProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider) {
    this.transferDaoProvider = transferDaoProvider;
    this.transferProgressDaoProvider = transferProgressDaoProvider;
    this.fileSenderProvider = fileSenderProvider;
    this.fileReceiverProvider = fileReceiverProvider;
    this.socketTransferManagerProvider = socketTransferManagerProvider;
  }

  @Override
  public TransferRepositoryImpl get() {
    return newInstance(transferDaoProvider.get(), transferProgressDaoProvider.get(), fileSenderProvider.get(), fileReceiverProvider.get(), socketTransferManagerProvider.get());
  }

  public static TransferRepositoryImpl_Factory create(Provider<TransferDao> transferDaoProvider,
      Provider<TransferProgressDao> transferProgressDaoProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider,
      Provider<SocketTransferManager> socketTransferManagerProvider) {
    return new TransferRepositoryImpl_Factory(transferDaoProvider, transferProgressDaoProvider, fileSenderProvider, fileReceiverProvider, socketTransferManagerProvider);
  }

  public static TransferRepositoryImpl newInstance(TransferDao transferDao,
      TransferProgressDao transferProgressDao, FileSender fileSender, FileReceiver fileReceiver,
      SocketTransferManager socketTransferManager) {
    return new TransferRepositoryImpl(transferDao, transferProgressDao, fileSender, fileReceiver, socketTransferManager);
  }
}
