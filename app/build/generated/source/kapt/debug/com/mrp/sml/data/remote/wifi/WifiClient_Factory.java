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
public final class WifiClient_Factory implements Factory<WifiClient> {
  @Override
  public WifiClient get() {
    return newInstance();
  }

  public static WifiClient_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WifiClient newInstance() {
    return new WifiClient();
  }

  private static final class InstanceHolder {
    static final WifiClient_Factory INSTANCE = new WifiClient_Factory();
  }
}
