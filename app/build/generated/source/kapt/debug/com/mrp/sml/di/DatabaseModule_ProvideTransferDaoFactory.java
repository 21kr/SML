package com.mrp.sml.di;

import com.mrp.sml.data.local.db.AppDatabase;
import com.mrp.sml.data.local.db.dao.TransferDao;
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
public final class DatabaseModule_ProvideTransferDaoFactory implements Factory<TransferDao> {
  private final Provider<AppDatabase> databaseProvider;

  private DatabaseModule_ProvideTransferDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TransferDao get() {
    return provideTransferDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTransferDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTransferDaoFactory(databaseProvider);
  }

  public static TransferDao provideTransferDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTransferDao(database));
  }
}
