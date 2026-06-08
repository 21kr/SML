package com.mrp.sml.di;

import android.content.Context;
import com.mrp.sml.data.remote.discovery.DeviceAdvertiser;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class NetworkModule_ProvideDeviceAdvertiserFactory implements Factory<DeviceAdvertiser> {
  private final Provider<Context> contextProvider;

  private NetworkModule_ProvideDeviceAdvertiserFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DeviceAdvertiser get() {
    return provideDeviceAdvertiser(contextProvider.get());
  }

  public static NetworkModule_ProvideDeviceAdvertiserFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideDeviceAdvertiserFactory(contextProvider);
  }

  public static DeviceAdvertiser provideDeviceAdvertiser(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideDeviceAdvertiser(context));
  }
}
