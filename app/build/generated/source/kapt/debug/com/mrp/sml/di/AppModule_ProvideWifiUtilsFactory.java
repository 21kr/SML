package com.mrp.sml.di;

import com.mrp.sml.core.utils.WifiUtils;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideWifiUtilsFactory implements Factory<WifiUtils> {
  @Override
  public WifiUtils get() {
    return provideWifiUtils();
  }

  public static AppModule_ProvideWifiUtilsFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WifiUtils provideWifiUtils() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWifiUtils());
  }

  private static final class InstanceHolder {
    static final AppModule_ProvideWifiUtilsFactory INSTANCE = new AppModule_ProvideWifiUtilsFactory();
  }
}
