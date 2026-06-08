package com.mrp.sml.data.remote.wifi;

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
public final class WifiDirectManager_Factory implements Factory<WifiDirectManager> {
  private final Provider<Context> contextProvider;

  private WifiDirectManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WifiDirectManager get() {
    return newInstance(contextProvider.get());
  }

  public static WifiDirectManager_Factory create(Provider<Context> contextProvider) {
    return new WifiDirectManager_Factory(contextProvider);
  }

  public static WifiDirectManager newInstance(Context context) {
    return new WifiDirectManager(context);
  }
}
