package com.mrp.sml.di;

import com.mrp.sml.data.remote.sockets.FileReceiver;
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
public final class NetworkModule_ProvideFileReceiverFactory implements Factory<FileReceiver> {
  @Override
  public FileReceiver get() {
    return provideFileReceiver();
  }

  public static NetworkModule_ProvideFileReceiverFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FileReceiver provideFileReceiver() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideFileReceiver());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideFileReceiverFactory INSTANCE = new NetworkModule_ProvideFileReceiverFactory();
  }
}
