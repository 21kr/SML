package com.mrp.sml.domain.usecase;

import com.mrp.sml.domain.repository.FileTransferRepository;
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
public final class FileTransferUseCase_Factory implements Factory<FileTransferUseCase> {
  private final Provider<FileTransferRepository> repositoryProvider;

  public FileTransferUseCase_Factory(Provider<FileTransferRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public FileTransferUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static FileTransferUseCase_Factory create(
      Provider<FileTransferRepository> repositoryProvider) {
    return new FileTransferUseCase_Factory(repositoryProvider);
  }

  public static FileTransferUseCase newInstance(FileTransferRepository repository) {
    return new FileTransferUseCase(repository);
  }
}
