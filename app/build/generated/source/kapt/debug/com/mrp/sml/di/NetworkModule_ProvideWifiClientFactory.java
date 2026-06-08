package com.mrp.sml.di;

import com.mrp.sml.data.remote.wifi.WifiClient;
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
public final class NetworkModule_ProvideWifiClientFactory implements Factory<WifiClient> {
  @Override
  public WifiClient get() {
    return provideWifiClient();
  }

  public static NetworkModule_ProvideWifiClientFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WifiClient provideWifiClient() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWifiClient());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideWifiClientFactory INSTANCE = new NetworkModule_ProvideWifiClientFactory();
  }
}
