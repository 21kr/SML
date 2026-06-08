package com.mrp.sml.ui.pairing;

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
public final class PairingViewModel_Factory implements Factory<PairingViewModel> {
  private final Provider<ConnectionUseCase> connectionUseCaseProvider;

  public PairingViewModel_Factory(Provider<ConnectionUseCase> connectionUseCaseProvider) {
    this.connectionUseCaseProvider = connectionUseCaseProvider;
  }

  @Override
  public PairingViewModel get() {
    return newInstance(connectionUseCaseProvider.get());
  }

  public static PairingViewModel_Factory create(
      Provider<ConnectionUseCase> connectionUseCaseProvider) {
    return new PairingViewModel_Factory(connectionUseCaseProvider);
  }

  public static PairingViewModel newInstance(ConnectionUseCase connectionUseCase) {
    return new PairingViewModel(connectionUseCase);
  }
}
