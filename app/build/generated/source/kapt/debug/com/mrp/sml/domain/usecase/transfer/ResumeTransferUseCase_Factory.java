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
public final class ResumeTransferUseCase_Factory implements Factory<ResumeTransferUseCase> {
  private final Provider<TransferRepository> transferRepositoryProvider;

  private ResumeTransferUseCase_Factory(Provider<TransferRepository> transferRepositoryProvider) {
    this.transferRepositoryProvider = transferRepositoryProvider;
  }

  @Override
  public ResumeTransferUseCase get() {
    return newInstance(transferRepositoryProvider.get());
  }

  public static ResumeTransferUseCase_Factory create(
      Provider<TransferRepository> transferRepositoryProvider) {
    return new ResumeTransferUseCase_Factory(transferRepositoryProvider);
  }

  public static ResumeTransferUseCase newInstance(TransferRepository transferRepository) {
    return new ResumeTransferUseCase(transferRepository);
  }
}
