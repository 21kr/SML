package com.mrp.sml.di;

import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class NetworkModule_ProvideSocketTransferManagerFactory implements Factory<SocketTransferManager> {
  @Override
  public SocketTransferManager get() {
    return provideSocketTransferManager();
  }

  public static NetworkModule_ProvideSocketTransferManagerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SocketTransferManager provideSocketTransferManager() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSocketTransferManager());
  }

  private static final class InstanceHolder {
    static final NetworkModule_ProvideSocketTransferManagerFactory INSTANCE = new NetworkModule_ProvideSocketTransferManagerFactory();
  }
}
