package com.mrp.sml.data.di;

import com.mrp.sml.data.local.SmlDatabase;
import com.mrp.sml.data.local.TransferDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "KotlinInternalInJava"
})
public final class DataModule_ProvideTransferDaoFactory implements Factory<TransferDao> {
  private final Provider<SmlDatabase> databaseProvider;

  public DataModule_ProvideTransferDaoFactory(Provider<SmlDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TransferDao get() {
    return provideTransferDao(databaseProvider.get());
  }

  public static DataModule_ProvideTransferDaoFactory create(
      Provider<SmlDatabase> databaseProvider) {
    return new DataModule_ProvideTransferDaoFactory(databaseProvider);
  }

  public static TransferDao provideTransferDao(SmlDatabase database) {
    return Preconditions.checkNotNullFromProvides(DataModule.provideTransferDao(database));
  }
}
