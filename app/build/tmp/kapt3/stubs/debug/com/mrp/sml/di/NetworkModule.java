package com.mrp.sml.di;

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
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/mrp/sml/di/NetworkModule;", "", "()V", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.di.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
}