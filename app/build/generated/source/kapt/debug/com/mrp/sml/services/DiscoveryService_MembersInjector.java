package com.mrp.sml.services;

import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class DiscoveryService_MembersInjector implements MembersInjector<DiscoveryService> {
  private final Provider<DeviceDiscoveryManager> discoveryManagerProvider;

  private DiscoveryService_MembersInjector(
      Provider<DeviceDiscoveryManager> discoveryManagerProvider) {
    this.discoveryManagerProvider = discoveryManagerProvider;
  }

  public static MembersInjector<DiscoveryService> create(
      Provider<DeviceDiscoveryManager> discoveryManagerProvider) {
    return new DiscoveryService_MembersInjector(discoveryManagerProvider);
  }

  @Override
  public void injectMembers(DiscoveryService instance) {
    injectDiscoveryManager(instance, discoveryManagerProvider.get());
  }

  @InjectedFieldSignature("com.mrp.sml.services.DiscoveryService.discoveryManager")
  public static void injectDiscoveryManager(DiscoveryService instance,
      DeviceDiscoveryManager discoveryManager) {
    instance.discoveryManager = discoveryManager;
  }
}
