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
public final class FileReceiver_Factory implements Factory<FileReceiver> {
  @Override
  public FileReceiver get() {
    return newInstance();
  }

  public static FileReceiver_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FileReceiver newInstance() {
    return new FileReceiver();
  }

  private static final class InstanceHolder {
    private static final FileReceiver_Factory INSTANCE = new FileReceiver_Factory();
  }
}
