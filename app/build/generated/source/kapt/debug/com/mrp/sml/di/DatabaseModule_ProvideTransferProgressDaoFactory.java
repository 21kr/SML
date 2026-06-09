package com.mrp.sml.di;

import com.mrp.sml.data.local.db.AppDatabase;
import com.mrp.sml.data.local.db.dao.TransferProgressDao;
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
public final class DatabaseModule_ProvideTransferProgressDaoFactory implements Factory<TransferProgressDao> {
  private final Provider<AppDatabase> databaseProvider;

  private DatabaseModule_ProvideTransferProgressDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TransferProgressDao get() {
    return provideTransferProgressDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTransferProgressDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTransferProgressDaoFactory(databaseProvider);
  }

  public static TransferProgressDao provideTransferProgressDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTransferProgressDao(database));
  }
}
