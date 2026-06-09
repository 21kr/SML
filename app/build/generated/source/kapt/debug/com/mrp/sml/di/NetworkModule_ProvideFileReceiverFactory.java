package com.mrp.sml.di;

import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class NetworkModule_ProvideFileReceiverFactory implements Factory<FileReceiver> {
  private final Provider<SocketTransferManager> transferManagerProvider;

  private NetworkModule_ProvideFileReceiverFactory(
      Provider<SocketTransferManager> transferManagerProvider) {
    this.transferManagerProvider = transferManagerProvider;
  }

  @Override
  public FileReceiver get() {
    return provideFileReceiver(transferManagerProvider.get());
  }

  public static NetworkModule_ProvideFileReceiverFactory create(
      Provider<SocketTransferManager> transferManagerProvider) {
    return new NetworkModule_ProvideFileReceiverFactory(transferManagerProvider);
  }

  public static FileReceiver provideFileReceiver(SocketTransferManager transferManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideFileReceiver(transferManager));
  }
}
