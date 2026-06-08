package com.mrp.sml.data.repository;

import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import com.mrp.sml.data.remote.wifi.WifiDirectManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class ConnectionRepositoryImpl_Factory implements Factory<ConnectionRepositoryImpl> {
  private final Provider<DeviceDiscoveryManager> deviceDiscoveryManagerProvider;

  private final Provider<WifiDirectManager> wifiDirectManagerProvider;

  private ConnectionRepositoryImpl_Factory(
      Provider<DeviceDiscoveryManager> deviceDiscoveryManagerProvider,
      Provider<WifiDirectManager> wifiDirectManagerProvider) {
    this.deviceDiscoveryManagerProvider = deviceDiscoveryManagerProvider;
    this.wifiDirectManagerProvider = wifiDirectManagerProvider;
  }

  @Override
  public ConnectionRepositoryImpl get() {
    return newInstance(deviceDiscoveryManagerProvider.get(), wifiDirectManagerProvider.get());
  }

  public static ConnectionRepositoryImpl_Factory create(
      Provider<DeviceDiscoveryManager> deviceDiscoveryManagerProvider,
      Provider<WifiDirectManager> wifiDirectManagerProvider) {
    return new ConnectionRepositoryImpl_Factory(deviceDiscoveryManagerProvider, wifiDirectManagerProvider);
  }

  public static ConnectionRepositoryImpl newInstance(DeviceDiscoveryManager deviceDiscoveryManager,
      WifiDirectManager wifiDirectManager) {
    return new ConnectionRepositoryImpl(deviceDiscoveryManager, wifiDirectManager);
  }
}
