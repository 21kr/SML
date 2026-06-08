package com.mrp.sml.di;

import android.content.Context;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import com.mrp.sml.data.remote.nearby.NearbyManager;
import com.mrp.sml.data.remote.wifi.WifiDirectManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class NetworkModule_ProvideDeviceDiscoveryManagerFactory implements Factory<DeviceDiscoveryManager> {
  private final Provider<Context> contextProvider;

  private final Provider<WifiDirectManager> wifiDirectManagerProvider;

  private final Provider<NearbyManager> nearbyManagerProvider;

  private NetworkModule_ProvideDeviceDiscoveryManagerFactory(Provider<Context> contextProvider,
      Provider<WifiDirectManager> wifiDirectManagerProvider,
      Provider<NearbyManager> nearbyManagerProvider) {
    this.contextProvider = contextProvider;
    this.wifiDirectManagerProvider = wifiDirectManagerProvider;
    this.nearbyManagerProvider = nearbyManagerProvider;
  }

  @Override
  public DeviceDiscoveryManager get() {
    return provideDeviceDiscoveryManager(contextProvider.get(), wifiDirectManagerProvider.get(), nearbyManagerProvider.get());
  }

  public static NetworkModule_ProvideDeviceDiscoveryManagerFactory create(
      Provider<Context> contextProvider, Provider<WifiDirectManager> wifiDirectManagerProvider,
      Provider<NearbyManager> nearbyManagerProvider) {
    return new NetworkModule_ProvideDeviceDiscoveryManagerFactory(contextProvider, wifiDirectManagerProvider, nearbyManagerProvider);
  }

  public static DeviceDiscoveryManager provideDeviceDiscoveryManager(Context context,
      WifiDirectManager wifiDirectManager, NearbyManager nearbyManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideDeviceDiscoveryManager(context, wifiDirectManager, nearbyManager));
  }
}
