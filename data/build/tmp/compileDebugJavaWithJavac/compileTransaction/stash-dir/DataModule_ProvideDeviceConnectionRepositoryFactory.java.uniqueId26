package com.mrp.sml.data.di;

import android.content.Context;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
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
public final class DataModule_ProvideDeviceConnectionRepositoryFactory implements Factory<DeviceConnectionRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<DispatchersProvider> dispatchersProvider;

  public DataModule_ProvideDeviceConnectionRepositoryFactory(Provider<Context> contextProvider,
      Provider<DispatchersProvider> dispatchersProvider) {
    this.contextProvider = contextProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public DeviceConnectionRepository get() {
    return provideDeviceConnectionRepository(contextProvider.get(), dispatchersProvider.get());
  }

  public static DataModule_ProvideDeviceConnectionRepositoryFactory create(
      Provider<Context> contextProvider, Provider<DispatchersProvider> dispatchersProvider) {
    return new DataModule_ProvideDeviceConnectionRepositoryFactory(contextProvider, dispatchersProvider);
  }

  public static DeviceConnectionRepository provideDeviceConnectionRepository(Context context,
      DispatchersProvider dispatchersProvider) {
    return Preconditions.checkNotNullFromProvides(DataModule.provideDeviceConnectionRepository(context, dispatchersProvider));
  }
}
