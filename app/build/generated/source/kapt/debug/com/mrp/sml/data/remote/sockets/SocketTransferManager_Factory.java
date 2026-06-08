package com.mrp.sml.data.remote.sockets;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SocketTransferManager_Factory implements Factory<SocketTransferManager> {
  @Override
  public SocketTransferManager get() {
    return newInstance();
  }

  public static SocketTransferManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SocketTransferManager newInstance() {
    return new SocketTransferManager();
  }

  private static final class InstanceHolder {
    static final SocketTransferManager_Factory INSTANCE = new SocketTransferManager_Factory();
  }
}
