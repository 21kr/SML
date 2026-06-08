package com.mrp.sml.di;

import android.content.Context;
import com.mrp.sml.data.remote.wifi.WifiDirectManager;
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
public final class NetworkModule_ProvideWifiDirectManagerFactory implements Factory<WifiDirectManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideWifiDirectManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WifiDirectManager get() {
    return provideWifiDirectManager(contextProvider.get());
  }

  public static NetworkModule_ProvideWifiDirectManagerFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideWifiDirectManagerFactory(contextProvider);
  }

  public static WifiDirectManager provideWifiDirectManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWifiDirectManager(context));
  }
}
