package com.mrp.sml.ui.connection;

import com.mrp.sml.domain.usecase.ConnectionUseCase;
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
  private final Provider<ConnectionUseCase> connectionUseCaseProvider;

  public ConnectionViewModel_Factory(Provider<ConnectionUseCase> connectionUseCaseProvider) {
    this.connectionUseCaseProvider = connectionUseCaseProvider;
  }

  @Override
  public ConnectionViewModel get() {
    return newInstance(connectionUseCaseProvider.get());
  }

  public static ConnectionViewModel_Factory create(
      Provider<ConnectionUseCase> connectionUseCaseProvider) {
    return new ConnectionViewModel_Factory(connectionUseCaseProvider);
  }

  public static ConnectionViewModel newInstance(ConnectionUseCase connectionUseCase) {
    return new ConnectionViewModel(connectionUseCase);
  }
}
