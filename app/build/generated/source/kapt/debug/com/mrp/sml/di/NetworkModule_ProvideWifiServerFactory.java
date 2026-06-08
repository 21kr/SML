package com.mrp.sml.di;

import com.mrp.sml.data.remote.wifi.WifiServer;
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
public final class NetworkModule_ProvideWifiServerFactory implements Factory<WifiServer> {
  @Override
  public WifiServer get() {
    return provideWifiServer();
  }

  public static NetworkModule_ProvideWifiServerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WifiServer provideWifiServer() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWifiServer());
  }

  private static final class InstanceHolder {
    static final NetworkModule_ProvideWifiServerFactory INSTANCE = new NetworkModule_ProvideWifiServerFactory();
  }
}
