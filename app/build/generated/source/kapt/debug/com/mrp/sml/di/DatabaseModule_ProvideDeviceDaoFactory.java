package com.mrp.sml.di;

import com.mrp.sml.data.local.db.AppDatabase;
import com.mrp.sml.data.local.db.dao.DeviceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class DatabaseModule_ProvideDeviceDaoFactory implements Factory<DeviceDao> {
  private final Provider<AppDatabase> databaseProvider;

  private DatabaseModule_ProvideDeviceDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DeviceDao get() {
    return provideDeviceDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideDeviceDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideDeviceDaoFactory(databaseProvider);
  }

  public static DeviceDao provideDeviceDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDeviceDao(database));
  }
}
