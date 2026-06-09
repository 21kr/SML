package com.mrp.sml;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mrp.sml.data.local.db.AppDatabase;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.local.db.dao.TransferProgressDao;
import com.mrp.sml.data.local.preferences.SettingsManager;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import com.mrp.sml.data.remote.nearby.NearbyManager;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import com.mrp.sml.data.remote.wifi.WifiDirectManager;
import com.mrp.sml.data.repository.ConnectionRepositoryImpl;
import com.mrp.sml.data.repository.TransferRepositoryImpl;
import com.mrp.sml.di.AppModule_ProvideContextFactory;
import com.mrp.sml.di.DatabaseModule_ProvideDatabaseFactory;
import com.mrp.sml.di.DatabaseModule_ProvideTransferDaoFactory;
import com.mrp.sml.di.DatabaseModule_ProvideTransferProgressDaoFactory;
import com.mrp.sml.di.NetworkModule_ProvideDeviceDiscoveryManagerFactory;
import com.mrp.sml.di.NetworkModule_ProvideFileReceiverFactory;
import com.mrp.sml.di.NetworkModule_ProvideFileSenderFactory;
import com.mrp.sml.di.NetworkModule_ProvideNearbyManagerFactory;
import com.mrp.sml.di.NetworkModule_ProvideSocketTransferManagerFactory;
import com.mrp.sml.di.NetworkModule_ProvideWifiDirectManagerFactory;
import com.mrp.sml.services.DiscoveryService;
import com.mrp.sml.services.DiscoveryService_MembersInjector;
import com.mrp.sml.services.TransferForegroundService;
import com.mrp.sml.ui.viewmodel.DiscoveryViewModel;
import com.mrp.sml.ui.viewmodel.DiscoveryViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.DiscoveryViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.DiscoveryViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.HistoryViewModel;
import com.mrp.sml.ui.viewmodel.HistoryViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.HistoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.HistoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.HomeViewModel;
import com.mrp.sml.ui.viewmodel.HomeViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.ReceiveViewModel;
import com.mrp.sml.ui.viewmodel.ReceiveViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.ReceiveViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.ReceiveViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.SendViewModel;
import com.mrp.sml.ui.viewmodel.SendViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.SendViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.SendViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.SettingsViewModel;
import com.mrp.sml.ui.viewmodel.SettingsViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.TransferDetailViewModel;
import com.mrp.sml.ui.viewmodel.TransferDetailViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.TransferDetailViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.TransferDetailViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.ui.viewmodel.TransferViewModel;
import com.mrp.sml.ui.viewmodel.TransferViewModel_HiltModules;
import com.mrp.sml.ui.viewmodel.TransferViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.mrp.sml.ui.viewmodel.TransferViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.mrp.sml.workers.CleanupWorker;
import com.mrp.sml.workers.CleanupWorker_AssistedFactory;
import com.mrp.sml.workers.RetryTransferWorker;
import com.mrp.sml.workers.RetryTransferWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerSMLApplication_HiltComponents_SingletonC {
  private DaggerSMLApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public SMLApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements SMLApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements SMLApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements SMLApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements SMLApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements SMLApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements SMLApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements SMLApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public SMLApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends SMLApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends SMLApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    FragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends SMLApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends SMLApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    ActivityCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(8).put(DiscoveryViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, DiscoveryViewModel_HiltModules.KeyModule.provide()).put(HistoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HistoryViewModel_HiltModules.KeyModule.provide()).put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide()).put(ReceiveViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ReceiveViewModel_HiltModules.KeyModule.provide()).put(SendViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SendViewModel_HiltModules.KeyModule.provide()).put(SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SettingsViewModel_HiltModules.KeyModule.provide()).put(TransferDetailViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TransferDetailViewModel_HiltModules.KeyModule.provide()).put(TransferViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TransferViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends SMLApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    Provider<DiscoveryViewModel> discoveryViewModelProvider;

    Provider<HistoryViewModel> historyViewModelProvider;

    Provider<HomeViewModel> homeViewModelProvider;

    Provider<ReceiveViewModel> receiveViewModelProvider;

    Provider<SendViewModel> sendViewModelProvider;

    Provider<SettingsViewModel> settingsViewModelProvider;

    Provider<TransferDetailViewModel> transferDetailViewModelProvider;

    Provider<TransferViewModel> transferViewModelProvider;

    ViewModelCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        SavedStateHandle savedStateHandleParam, ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.discoveryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.historyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.receiveViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.sendViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.transferDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.transferViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(8).put(DiscoveryViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (discoveryViewModelProvider))).put(HistoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (historyViewModelProvider))).put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (homeViewModelProvider))).put(ReceiveViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (receiveViewModelProvider))).put(SendViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (sendViewModelProvider))).put(SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (settingsViewModelProvider))).put(TransferDetailViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (transferDetailViewModelProvider))).put(TransferViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (transferViewModelProvider))).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.mrp.sml.ui.viewmodel.DiscoveryViewModel
          return (T) new DiscoveryViewModel(singletonCImpl.connectionRepositoryImplProvider.get());

          case 1: // com.mrp.sml.ui.viewmodel.HistoryViewModel
          return (T) new HistoryViewModel(singletonCImpl.transferRepositoryImplProvider.get());

          case 2: // com.mrp.sml.ui.viewmodel.HomeViewModel
          return (T) new HomeViewModel(singletonCImpl.connectionRepositoryImplProvider.get(), singletonCImpl.transferRepositoryImplProvider.get());

          case 3: // com.mrp.sml.ui.viewmodel.ReceiveViewModel
          return (T) new ReceiveViewModel(singletonCImpl.connectionRepositoryImplProvider.get());

          case 4: // com.mrp.sml.ui.viewmodel.SendViewModel
          return (T) new SendViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.mrp.sml.ui.viewmodel.SettingsViewModel
          return (T) new SettingsViewModel(singletonCImpl.settingsManagerProvider.get());

          case 6: // com.mrp.sml.ui.viewmodel.TransferDetailViewModel
          return (T) new TransferDetailViewModel(singletonCImpl.transferRepositoryImplProvider.get());

          case 7: // com.mrp.sml.ui.viewmodel.TransferViewModel
          return (T) new TransferViewModel(singletonCImpl.transferRepositoryImplProvider.get(), singletonCImpl.provideSocketTransferManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends SMLApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends SMLApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectDiscoveryService(DiscoveryService discoveryService) {
      injectDiscoveryService2(discoveryService);
    }

    @Override
    public void injectTransferForegroundService(
        TransferForegroundService transferForegroundService) {
    }

    @CanIgnoreReturnValue
    private DiscoveryService injectDiscoveryService2(DiscoveryService instance) {
      DiscoveryService_MembersInjector.injectDiscoveryManager(instance, singletonCImpl.provideDeviceDiscoveryManagerProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends SMLApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    Provider<Context> provideContextProvider;

    Provider<AppDatabase> provideDatabaseProvider;

    Provider<CleanupWorker_AssistedFactory> cleanupWorker_AssistedFactoryProvider;

    Provider<SocketTransferManager> provideSocketTransferManagerProvider;

    Provider<FileSender> provideFileSenderProvider;

    Provider<FileReceiver> provideFileReceiverProvider;

    Provider<RetryTransferWorker_AssistedFactory> retryTransferWorker_AssistedFactoryProvider;

    Provider<WifiDirectManager> provideWifiDirectManagerProvider;

    Provider<NearbyManager> provideNearbyManagerProvider;

    Provider<DeviceDiscoveryManager> provideDeviceDiscoveryManagerProvider;

    Provider<ConnectionRepositoryImpl> connectionRepositoryImplProvider;

    Provider<TransferRepositoryImpl> transferRepositoryImplProvider;

    Provider<SettingsManager> settingsManagerProvider;

    SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    TransferDao transferDao() {
      return DatabaseModule_ProvideTransferDaoFactory.provideTransferDao(provideDatabaseProvider.get());
    }

    Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(2).put("com.mrp.sml.workers.CleanupWorker", ((Provider) (cleanupWorker_AssistedFactoryProvider))).put("com.mrp.sml.workers.RetryTransferWorker", ((Provider) (retryTransferWorker_AssistedFactoryProvider))).build();
    }

    HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    TransferProgressDao transferProgressDao() {
      return DatabaseModule_ProvideTransferProgressDaoFactory.provideTransferProgressDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideContextProvider = DoubleCheck.provider(new SwitchingProvider<Context>(singletonCImpl, 2));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 1));
      this.cleanupWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<CleanupWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideSocketTransferManagerProvider = DoubleCheck.provider(new SwitchingProvider<SocketTransferManager>(singletonCImpl, 5));
      this.provideFileSenderProvider = DoubleCheck.provider(new SwitchingProvider<FileSender>(singletonCImpl, 4));
      this.provideFileReceiverProvider = DoubleCheck.provider(new SwitchingProvider<FileReceiver>(singletonCImpl, 6));
      this.retryTransferWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<RetryTransferWorker_AssistedFactory>(singletonCImpl, 3));
      this.provideWifiDirectManagerProvider = DoubleCheck.provider(new SwitchingProvider<WifiDirectManager>(singletonCImpl, 9));
      this.provideNearbyManagerProvider = DoubleCheck.provider(new SwitchingProvider<NearbyManager>(singletonCImpl, 10));
      this.provideDeviceDiscoveryManagerProvider = DoubleCheck.provider(new SwitchingProvider<DeviceDiscoveryManager>(singletonCImpl, 8));
      this.connectionRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ConnectionRepositoryImpl>(singletonCImpl, 7));
      this.transferRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TransferRepositoryImpl>(singletonCImpl, 11));
      this.settingsManagerProvider = DoubleCheck.provider(new SwitchingProvider<SettingsManager>(singletonCImpl, 12));
    }

    @Override
    public void injectSMLApplication(SMLApplication sMLApplication) {
      injectSMLApplication2(sMLApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private SMLApplication injectSMLApplication2(SMLApplication instance) {
      SMLApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.mrp.sml.workers.CleanupWorker_AssistedFactory
          return (T) new CleanupWorker_AssistedFactory() {
            @Override
            public CleanupWorker create(Context appContext, WorkerParameters workerParams) {
              return new CleanupWorker(appContext, workerParams, singletonCImpl.transferDao());
            }
          };

          case 1: // com.mrp.sml.data.local.db.AppDatabase
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(singletonCImpl.provideContextProvider.get());

          case 2: // android.content.Context
          return (T) AppModule_ProvideContextFactory.provideContext(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 3: // com.mrp.sml.workers.RetryTransferWorker_AssistedFactory
          return (T) new RetryTransferWorker_AssistedFactory() {
            @Override
            public RetryTransferWorker create(Context appContext2, WorkerParameters workerParams2) {
              return new RetryTransferWorker(appContext2, workerParams2, singletonCImpl.transferDao(), singletonCImpl.provideFileSenderProvider.get(), singletonCImpl.provideFileReceiverProvider.get(), singletonCImpl.provideSocketTransferManagerProvider.get());
            }
          };

          case 4: // com.mrp.sml.data.remote.sockets.FileSender
          return (T) NetworkModule_ProvideFileSenderFactory.provideFileSender(singletonCImpl.provideSocketTransferManagerProvider.get());

          case 5: // com.mrp.sml.data.remote.sockets.SocketTransferManager
          return (T) NetworkModule_ProvideSocketTransferManagerFactory.provideSocketTransferManager();

          case 6: // com.mrp.sml.data.remote.sockets.FileReceiver
          return (T) NetworkModule_ProvideFileReceiverFactory.provideFileReceiver(singletonCImpl.provideSocketTransferManagerProvider.get());

          case 7: // com.mrp.sml.data.repository.ConnectionRepositoryImpl
          return (T) new ConnectionRepositoryImpl(singletonCImpl.provideDeviceDiscoveryManagerProvider.get(), singletonCImpl.provideWifiDirectManagerProvider.get());

          case 8: // com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
          return (T) NetworkModule_ProvideDeviceDiscoveryManagerFactory.provideDeviceDiscoveryManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideWifiDirectManagerProvider.get(), singletonCImpl.provideNearbyManagerProvider.get());

          case 9: // com.mrp.sml.data.remote.wifi.WifiDirectManager
          return (T) NetworkModule_ProvideWifiDirectManagerFactory.provideWifiDirectManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 10: // com.mrp.sml.data.remote.nearby.NearbyManager
          return (T) NetworkModule_ProvideNearbyManagerFactory.provideNearbyManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 11: // com.mrp.sml.data.repository.TransferRepositoryImpl
          return (T) new TransferRepositoryImpl(singletonCImpl.transferDao(), singletonCImpl.transferProgressDao(), singletonCImpl.provideFileSenderProvider.get(), singletonCImpl.provideFileReceiverProvider.get(), singletonCImpl.provideSocketTransferManagerProvider.get());

          case 12: // com.mrp.sml.data.local.preferences.SettingsManager
          return (T) new SettingsManager(singletonCImpl.provideContextProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
