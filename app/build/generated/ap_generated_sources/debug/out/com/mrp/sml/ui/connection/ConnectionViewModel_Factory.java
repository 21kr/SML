package com.mrp.sml.ui.connection;

import com.mrp.sml.domain.repository.DeviceConnectionRepository;
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
    "KotlinInternalInJava"
})
public final class ConnectionViewModel_Factory implements Factory<ConnectionViewModel> {
  private final Provider<DeviceConnectionRepository> deviceConnectionRepositoryProvider;

  public ConnectionViewModel_Factory(
      Provider<DeviceConnectionRepository> deviceConnectionRepositoryProvider) {
    this.deviceConnectionRepositoryProvider = deviceConnectionRepositoryProvider;
  }

  @Override
  public ConnectionViewModel get() {
    return newInstance(deviceConnectionRepositoryProvider.get());
  }

  public static ConnectionViewModel_Factory create(
      Provider<DeviceConnectionRepository> deviceConnectionRepositoryProvider) {
    return new ConnectionViewModel_Factory(deviceConnectionRepositoryProvider);
  }

  public static ConnectionViewModel newInstance(
      DeviceConnectionRepository deviceConnectionRepository) {
    return new ConnectionViewModel(deviceConnectionRepository);
  }
}
