package com.mrp.sml.data.remote.wifi;

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
public final class WifiServer_Factory implements Factory<WifiServer> {
  @Override
  public WifiServer get() {
    return newInstance();
  }

  public static WifiServer_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WifiServer newInstance() {
    return new WifiServer();
  }

  private static final class InstanceHolder {
    static final WifiServer_Factory INSTANCE = new WifiServer_Factory();
  }
}
