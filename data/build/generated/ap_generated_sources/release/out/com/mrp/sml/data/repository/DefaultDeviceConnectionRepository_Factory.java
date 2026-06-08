package com.mrp.sml.data.repository;

import android.content.Context;
import com.mrp.sml.core.common.DispatchersProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DefaultDeviceConnectionRepository_Factory implements Factory<DefaultDeviceConnectionRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<DispatchersProvider> dispatchersProvider;

  public DefaultDeviceConnectionRepository_Factory(Provider<Context> contextProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    this.contextProvider = contextProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public DefaultDeviceConnectionRepository get() {
    return newInstance(contextProvider.get(), dispatchersProvider.get());
  }

  public static DefaultDeviceConnectionRepository_Factory create(Provider<Context> contextProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    return new DefaultDeviceConnectionRepository_Factory(contextProvider, dispatchersProvider);
  }

  public static DefaultDeviceConnectionRepository newInstance(Context context,
      DispatchersProvider dispatchersProvider) {
    return new DefaultDeviceConnectionRepository(context, dispatchersProvider);
  }
}
