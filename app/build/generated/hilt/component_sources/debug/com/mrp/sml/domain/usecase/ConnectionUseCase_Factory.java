package com.mrp.sml.domain.usecase;

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
public final class ConnectionUseCase_Factory implements Factory<ConnectionUseCase> {
  private final Provider<DeviceConnectionRepository> repositoryProvider;

  public ConnectionUseCase_Factory(Provider<DeviceConnectionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ConnectionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ConnectionUseCase_Factory create(
      Provider<DeviceConnectionRepository> repositoryProvider) {
    return new ConnectionUseCase_Factory(repositoryProvider);
  }

  public static ConnectionUseCase newInstance(DeviceConnectionRepository repository) {
    return new ConnectionUseCase(repository);
  }
}
