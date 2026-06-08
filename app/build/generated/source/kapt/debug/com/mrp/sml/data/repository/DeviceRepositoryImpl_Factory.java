package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.db.dao.DeviceDao;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
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
public final class DeviceRepositoryImpl_Factory implements Factory<DeviceRepositoryImpl> {
  private final Provider<DeviceDiscoveryManager> deviceDiscoveryManagerProvider;

  private final Provider<DeviceDao> deviceDaoProvider;

  private DeviceRepositoryImpl_Factory(
      Provider<DeviceDiscoveryManager> deviceDiscoveryManagerProvider,
      Provider<DeviceDao> deviceDaoProvider) {
    this.deviceDiscoveryManagerProvider = deviceDiscoveryManagerProvider;
    this.deviceDaoProvider = deviceDaoProvider;
  }

  @Override
  public DeviceRepositoryImpl get() {
    return newInstance(deviceDiscoveryManagerProvider.get(), deviceDaoProvider.get());
  }

  public static DeviceRepositoryImpl_Factory create(
      Provider<DeviceDiscoveryManager> deviceDiscoveryManagerProvider,
      Provider<DeviceDao> deviceDaoProvider) {
    return new DeviceRepositoryImpl_Factory(deviceDiscoveryManagerProvider, deviceDaoProvider);
  }

  public static DeviceRepositoryImpl newInstance(DeviceDiscoveryManager deviceDiscoveryManager,
      DeviceDao deviceDao) {
    return new DeviceRepositoryImpl(deviceDiscoveryManager, deviceDao);
  }
}
