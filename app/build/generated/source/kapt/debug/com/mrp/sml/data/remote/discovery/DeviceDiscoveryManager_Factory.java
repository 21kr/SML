package com.mrp.sml.data.remote.discovery;

import android.content.Context;
import com.mrp.sml.data.remote.nearby.NearbyManager;
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
public final class DeviceDiscoveryManager_Factory implements Factory<DeviceDiscoveryManager> {
  private final Provider<Context> contextProvider;

  private final Provider<WifiDirectManager> wifiDirectManagerProvider;

  private final Provider<NearbyManager> nearbyManagerProvider;

  private DeviceDiscoveryManager_Factory(Provider<Context> contextProvider,
      Provider<WifiDirectManager> wifiDirectManagerProvider,
      Provider<NearbyManager> nearbyManagerProvider) {
    this.contextProvider = contextProvider;
    this.wifiDirectManagerProvider = wifiDirectManagerProvider;
    this.nearbyManagerProvider = nearbyManagerProvider;
  }

  @Override
  public DeviceDiscoveryManager get() {
    return newInstance(contextProvider.get(), wifiDirectManagerProvider.get(), nearbyManagerProvider.get());
  }

  public static DeviceDiscoveryManager_Factory create(Provider<Context> contextProvider,
      Provider<WifiDirectManager> wifiDirectManagerProvider,
      Provider<NearbyManager> nearbyManagerProvider) {
    return new DeviceDiscoveryManager_Factory(contextProvider, wifiDirectManagerProvider, nearbyManagerProvider);
  }

  public static DeviceDiscoveryManager newInstance(Context context,
      WifiDirectManager wifiDirectManager, NearbyManager nearbyManager) {
    return new DeviceDiscoveryManager(context, wifiDirectManager, nearbyManager);
  }
}
