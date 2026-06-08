package com.mrp.sml.di;

import android.content.Context;
import com.mrp.sml.SMLApplication;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideContextFactory implements Factory<Context> {
  private final Provider<SMLApplication> applicationProvider;

  public AppModule_ProvideContextFactory(Provider<SMLApplication> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public Context get() {
    return provideContext(applicationProvider.get());
  }

  public static AppModule_ProvideContextFactory create(
      Provider<SMLApplication> applicationProvider) {
    return new AppModule_ProvideContextFactory(applicationProvider);
  }

  public static Context provideContext(SMLApplication application) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideContext(application));
  }
}
