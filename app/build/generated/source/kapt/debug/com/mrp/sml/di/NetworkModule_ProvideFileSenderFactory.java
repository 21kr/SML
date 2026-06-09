package com.mrp.sml.di;

import com.mrp.sml.data.remote.sockets.FileSender;
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
public final class NetworkModule_ProvideFileSenderFactory implements Factory<FileSender> {
  private final Provider<SocketTransferManager> transferManagerProvider;

  private NetworkModule_ProvideFileSenderFactory(
      Provider<SocketTransferManager> transferManagerProvider) {
    this.transferManagerProvider = transferManagerProvider;
  }

  @Override
  public FileSender get() {
    return provideFileSender(transferManagerProvider.get());
  }

  public static NetworkModule_ProvideFileSenderFactory create(
      Provider<SocketTransferManager> transferManagerProvider) {
    return new NetworkModule_ProvideFileSenderFactory(transferManagerProvider);
  }

  public static FileSender provideFileSender(SocketTransferManager transferManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideFileSender(transferManager));
  }
}
