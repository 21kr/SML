package com.mrp.sml.data.remote.sockets;

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
public final class FileReceiver_Factory implements Factory<FileReceiver> {
  private final Provider<SocketTransferManager> transferManagerProvider;

  private FileReceiver_Factory(Provider<SocketTransferManager> transferManagerProvider) {
    this.transferManagerProvider = transferManagerProvider;
  }

  @Override
  public FileReceiver get() {
    return newInstance(transferManagerProvider.get());
  }

  public static FileReceiver_Factory create(
      Provider<SocketTransferManager> transferManagerProvider) {
    return new FileReceiver_Factory(transferManagerProvider);
  }

  public static FileReceiver newInstance(SocketTransferManager transferManager) {
    return new FileReceiver(transferManager);
  }
}
