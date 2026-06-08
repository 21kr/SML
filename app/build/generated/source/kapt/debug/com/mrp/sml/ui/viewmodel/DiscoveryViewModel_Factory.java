package com.mrp.sml.ui.viewmodel;

import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DiscoveryViewModel_Factory implements Factory<DiscoveryViewModel> {
  private final Provider<DeviceDiscoveryManager> discoveryManagerProvider;

  public DiscoveryViewModel_Factory(Provider<DeviceDiscoveryManager> discoveryManagerProvider) {
    this.discoveryManagerProvider = discoveryManagerProvider;
  }

  @Override
  public DiscoveryViewModel get() {
    return newInstance(discoveryManagerProvider.get());
  }

  public static DiscoveryViewModel_Factory create(
      Provider<DeviceDiscoveryManager> discoveryManagerProvider) {
    return new DiscoveryViewModel_Factory(discoveryManagerProvider);
  }

  public static DiscoveryViewModel newInstance(DeviceDiscoveryManager discoveryManager) {
    return new DiscoveryViewModel(discoveryManager);
  }
}
