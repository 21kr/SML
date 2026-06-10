package com.mrp.sml.ui.viewmodel;

import com.mrp.sml.data.remote.hotspot.HotspotManager;
import com.mrp.sml.domain.repository.ConnectionRepository;
import com.mrp.sml.domain.repository.TransferRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ReceiveViewModel_Factory implements Factory<ReceiveViewModel> {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  private final Provider<TransferRepository> transferRepositoryProvider;

  private final Provider<HotspotManager> hotspotManagerProvider;

  private ReceiveViewModel_Factory(Provider<ConnectionRepository> connectionRepositoryProvider,
      Provider<TransferRepository> transferRepositoryProvider,
      Provider<HotspotManager> hotspotManagerProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
    this.transferRepositoryProvider = transferRepositoryProvider;
    this.hotspotManagerProvider = hotspotManagerProvider;
  }

  @Override
  public ReceiveViewModel get() {
    return newInstance(connectionRepositoryProvider.get(), transferRepositoryProvider.get(), hotspotManagerProvider.get());
  }

  public static ReceiveViewModel_Factory create(
      Provider<ConnectionRepository> connectionRepositoryProvider,
      Provider<TransferRepository> transferRepositoryProvider,
      Provider<HotspotManager> hotspotManagerProvider) {
    return new ReceiveViewModel_Factory(connectionRepositoryProvider, transferRepositoryProvider, hotspotManagerProvider);
  }

  public static ReceiveViewModel newInstance(ConnectionRepository connectionRepository,
      TransferRepository transferRepository, HotspotManager hotspotManager) {
    return new ReceiveViewModel(connectionRepository, transferRepository, hotspotManager);
  }
}
