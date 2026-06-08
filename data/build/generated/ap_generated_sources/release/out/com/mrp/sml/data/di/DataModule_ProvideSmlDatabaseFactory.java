package com.mrp.sml.data.di;

import android.content.Context;
import com.mrp.sml.data.local.SmlDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DataModule_ProvideSmlDatabaseFactory implements Factory<SmlDatabase> {
  private final Provider<Context> contextProvider;

  public DataModule_ProvideSmlDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SmlDatabase get() {
    return provideSmlDatabase(contextProvider.get());
  }

  public static DataModule_ProvideSmlDatabaseFactory create(Provider<Context> contextProvider) {
    return new DataModule_ProvideSmlDatabaseFactory(contextProvider);
  }

  public static SmlDatabase provideSmlDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DataModule.provideSmlDatabase(context));
  }
}
