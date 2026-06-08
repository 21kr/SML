package com.mrp.sml.domain.usecase.transfer;

import com.mrp.sml.domain.repository.TransferRepository;
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
public final class SendFileUseCase_Factory implements Factory<SendFileUseCase> {
  private final Provider<TransferRepository> transferRepositoryProvider;

  private SendFileUseCase_Factory(Provider<TransferRepository> transferRepositoryProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
  }

  @Override
  public SendFileUseCase get() {
    return newInstance(transferRepositoryProvider.get());
  }

  public static SendFileUseCase_Factory create(
      Provider<TransferRepository> transferRepositoryProvider) {
    return new SendFileUseCase_Factory(transferRepositoryProvider);
  }

  public static SendFileUseCase newInstance(TransferRepository transferRepository) {
    return new SendFileUseCase(transferRepository);
  }
}
