package com.mrp.sml.ui.viewmodel;

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
public final class DiscoveryViewModel_Factory implements Factory<DiscoveryViewModel> {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  private DiscoveryViewModel_Factory(Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @Override
  public DiscoveryViewModel get() {
    return newInstance(connectionRepositoryProvider.get());
  }

  public static DiscoveryViewModel_Factory create(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    return new DiscoveryViewModel_Factory(connectionRepositoryProvider);
  }

  public static DiscoveryViewModel newInstance(ConnectionRepository connectionRepository) {
    return new DiscoveryViewModel(connectionRepository);
  }
}
