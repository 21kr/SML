package com.mrp.sml;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SMLApplication_MembersInjector implements MembersInjector<SMLApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public SMLApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<SMLApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new SMLApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(SMLApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.mrp.sml.SMLApplication.workerFactory")
  public static void injectWorkerFactory(SMLApplication instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
