package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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

  private final Provider<FileSender> fileSenderProvider;

  private final Provider<FileReceiver> fileReceiverProvider;

  public TransferRepositoryImpl_Factory(Provider<TransferDao> transferDaoProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider) {
    this.transferDaoProvider = transferDaoProvider;
    this.fileSenderProvider = fileSenderProvider;
    this.fileReceiverProvider = fileReceiverProvider;
  }

  @Override
  public TransferRepositoryImpl get() {
    return newInstance(transferDaoProvider.get(), fileSenderProvider.get(), fileReceiverProvider.get());
  }

  public static TransferRepositoryImpl_Factory create(Provider<TransferDao> transferDaoProvider,
      Provider<FileSender> fileSenderProvider, Provider<FileReceiver> fileReceiverProvider) {
    return new TransferRepositoryImpl_Factory(transferDaoProvider, fileSenderProvider, fileReceiverProvider);
  }

  public static TransferRepositoryImpl newInstance(TransferDao transferDao, FileSender fileSender,
      FileReceiver fileReceiver) {
    return new TransferRepositoryImpl(transferDao, fileSender, fileReceiver);
  }
}
