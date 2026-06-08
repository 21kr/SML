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
public final class ReceiveViewModel_Factory implements Factory<ReceiveViewModel> {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  private ReceiveViewModel_Factory(Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @Override
  public ReceiveViewModel get() {
    return newInstance(connectionRepositoryProvider.get());
  }

  public static ReceiveViewModel_Factory create(
      Provider<ConnectionRepository> connectionRepositoryProvider) {
    return new ReceiveViewModel_Factory(connectionRepositoryProvider);
  }

  public static ReceiveViewModel newInstance(ConnectionRepository connectionRepository) {
    return new ReceiveViewModel(connectionRepository);
  }
}
