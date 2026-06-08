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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  private HomeViewModel_Factory(Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(connectionRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    return new HomeViewModel_Factory(connectionRepositoryProvider);
  }

  public static HomeViewModel newInstance(ConnectionRepository connectionRepository) {
    return new HomeViewModel(connectionRepository);
  }
}
