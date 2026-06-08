package com.mrp.sml.di;

import android.content.Context;
import com.mrp.sml.data.remote.nearby.NearbyManager;
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
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NetworkModule_ProvideNearbyManagerFactory implements Factory<NearbyManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideNearbyManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NearbyManager get() {
    return provideNearbyManager(contextProvider.get());
  }

  public static NetworkModule_ProvideNearbyManagerFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideNearbyManagerFactory(contextProvider);
  }

  public static NearbyManager provideNearbyManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNearbyManager(context));
  }
}
