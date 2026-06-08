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
public final class FileSender_Factory implements Factory<FileSender> {
  @Override
  public FileSender get() {
    return newInstance();
  }

  public static FileSender_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FileSender newInstance() {
    return new FileSender();
  }

  private static final class InstanceHolder {
    static final FileSender_Factory INSTANCE = new FileSender_Factory();
  }
}
