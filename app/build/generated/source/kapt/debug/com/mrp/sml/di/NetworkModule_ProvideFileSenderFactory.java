package com.mrp.sml.di;

import com.mrp.sml.data.remote.sockets.FileSender;
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
public final class NetworkModule_ProvideFileSenderFactory implements Factory<FileSender> {
  @Override
  public FileSender get() {
    return provideFileSender();
  }

  public static NetworkModule_ProvideFileSenderFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FileSender provideFileSender() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideFileSender());
  }

  private static final class InstanceHolder {
    static final NetworkModule_ProvideFileSenderFactory INSTANCE = new NetworkModule_ProvideFileSenderFactory();
  }
}
