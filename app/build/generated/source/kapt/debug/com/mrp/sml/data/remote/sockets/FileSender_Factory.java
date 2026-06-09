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
public final class FileSender_Factory implements Factory<FileSender> {
  private final Provider<SocketTransferManager> transferManagerProvider;

  private FileSender_Factory(Provider<SocketTransferManager> transferManagerProvider) {
    this.transferManagerProvider = transferManagerProvider;
  }

  @Override
  public FileSender get() {
    return newInstance(transferManagerProvider.get());
  }

  public static FileSender_Factory create(Provider<SocketTransferManager> transferManagerProvider) {
    return new FileSender_Factory(transferManagerProvider);
  }

  public static FileSender newInstance(SocketTransferManager transferManager) {
    return new FileSender(transferManager);
  }
}
