package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.TransferDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "KotlinInternalInJava"
})
public final class DefaultTransferHistoryRepository_Factory implements Factory<DefaultTransferHistoryRepository> {
  private final Provider<TransferDao> transferDaoProvider;

  public DefaultTransferHistoryRepository_Factory(Provider<TransferDao> transferDaoProvider) {
    this.transferDaoProvider = transferDaoProvider;
  }

  @Override
  public DefaultTransferHistoryRepository get() {
    return newInstance(transferDaoProvider.get());
  }

  public static DefaultTransferHistoryRepository_Factory create(
      Provider<TransferDao> transferDaoProvider) {
    return new DefaultTransferHistoryRepository_Factory(transferDaoProvider);
  }

  public static DefaultTransferHistoryRepository newInstance(TransferDao transferDao) {
    return new DefaultTransferHistoryRepository(transferDao);
  }
}
