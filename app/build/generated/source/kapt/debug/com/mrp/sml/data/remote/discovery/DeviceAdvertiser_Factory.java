package com.mrp.sml.data.remote.discovery;

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
public final class DeviceAdvertiser_Factory implements Factory<DeviceAdvertiser> {
  private final Provider<Context> contextProvider;

  private DeviceAdvertiser_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DeviceAdvertiser get() {
    return newInstance(contextProvider.get());
  }

  public static DeviceAdvertiser_Factory create(Provider<Context> contextProvider) {
    return new DeviceAdvertiser_Factory(contextProvider);
  }

  public static DeviceAdvertiser newInstance(Context context) {
    return new DeviceAdvertiser(context);
  }
}
