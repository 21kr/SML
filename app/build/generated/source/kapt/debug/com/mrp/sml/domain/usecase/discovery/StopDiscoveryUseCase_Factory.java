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
public final class StopDiscoveryUseCase_Factory implements Factory<StopDiscoveryUseCase> {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  private StopDiscoveryUseCase_Factory(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @Override
  public StopDiscoveryUseCase get() {
    return newInstance(connectionRepositoryProvider.get());
  }

  public static StopDiscoveryUseCase_Factory create(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    return new StopDiscoveryUseCase_Factory(connectionRepositoryProvider);
  }

  public static StopDiscoveryUseCase newInstance(ConnectionRepository connectionRepository) {
    return new StopDiscoveryUseCase(connectionRepository);
  }
}
