package com.mrp.sml.data.remote.nearby;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class NearbyManager_Factory implements Factory<NearbyManager> {
  private final Provider<Context> contextProvider;

  private NearbyManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NearbyManager get() {
    return newInstance(contextProvider.get());
  }

  public static NearbyManager_Factory create(Provider<Context> contextProvider) {
    return new NearbyManager_Factory(contextProvider);
  }

  public static NearbyManager newInstance(Context context) {
    return new NearbyManager(context);
  }
}
