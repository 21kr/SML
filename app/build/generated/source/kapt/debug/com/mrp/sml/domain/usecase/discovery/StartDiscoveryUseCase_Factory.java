package com.mrp.sml.domain.usecase.discovery;

import com.mrp.sml.domain.repository.ConnectionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class StartDiscoveryUseCase_Factory implements Factory<StartDiscoveryUseCase> {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  private StartDiscoveryUseCase_Factory(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @Override
  public StartDiscoveryUseCase get() {
    return newInstance(connectionRepositoryProvider.get());
  }

  public static StartDiscoveryUseCase_Factory create(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    return new StartDiscoveryUseCase_Factory(connectionRepositoryProvider);
  }

  public static StartDiscoveryUseCase newInstance(ConnectionRepository connectionRepository) {
    return new StartDiscoveryUseCase(connectionRepository);
  }
}
