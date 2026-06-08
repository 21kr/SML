package com.mrp.sml.di;

import android.content.Context;
import com.mrp.sml.data.remote.discovery.DeviceAdvertiser;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import com.mrp.sml.data.remote.nearby.NearbyManager;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import com.mrp.sml.data.remote.wifi.WifiClient;
import com.mrp.sml.data.remote.wifi.WifiDirectManager;
import com.mrp.sml.data.remote.wifi.WifiServer;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\"\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\u000eH\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0007J\u0012\u0010\u0011\u001a\u00020\f2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0012\u001a\u00020\u0013H\u0007J\b\u0010\u0014\u001a\u00020\u0015H\u0007J\u0012\u0010\u0016\u001a\u00020\n2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0017\u001a\u00020\u0018H\u0007\u00a8\u0006\u0019"}, d2 = {"Lcom/mrp/sml/di/NetworkModule;", "", "()V", "provideDeviceAdvertiser", "Lcom/mrp/sml/data/remote/discovery/DeviceAdvertiser;", "context", "Landroid/content/Context;", "provideDeviceDiscoveryManager", "Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;", "wifiDirectManager", "Lcom/mrp/sml/data/remote/wifi/WifiDirectManager;", "nearbyManager", "Lcom/mrp/sml/data/remote/nearby/NearbyManager;", "provideFileReceiver", "Lcom/mrp/sml/data/remote/sockets/FileReceiver;", "provideFileSender", "Lcom/mrp/sml/data/remote/sockets/FileSender;", "provideNearbyManager", "provideSocketTransferManager", "Lcom/mrp/sml/data/remote/sockets/SocketTransferManager;", "provideWifiClient", "Lcom/mrp/sml/data/remote/wifi/WifiClient;", "provideWifiDirectManager", "provideWifiServer", "Lcom/mrp/sml/data/remote/wifi/WifiServer;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.di.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.wifi.WifiDirectManager provideWifiDirectManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.nearby.NearbyManager provideNearbyManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager provideDeviceDiscoveryManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.wifi.WifiDirectManager wifiDirectManager, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.nearby.NearbyManager nearbyManager) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.discovery.DeviceAdvertiser provideDeviceAdvertiser(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.wifi.WifiServer provideWifiServer() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.wifi.WifiClient provideWifiClient() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.sockets.SocketTransferManager provideSocketTransferManager() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.sockets.FileSender provideFileSender() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.sockets.FileReceiver provideFileReceiver() {
        return null;
    }
}