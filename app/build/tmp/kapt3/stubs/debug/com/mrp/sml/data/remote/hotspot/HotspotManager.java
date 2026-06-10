package com.mrp.sml.data.remote.hotspot;

import android.content.Context;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.mrp.sml.core.utils.WifiUtils;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0006\u0010\u001f\u001a\u00020\u0013R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/mrp/sml/data/remote/hotspot/HotspotManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "connectedNetwork", "Landroid/net/Network;", "connectivityManager", "Landroid/net/ConnectivityManager;", "getConnectivityManager", "()Landroid/net/ConnectivityManager;", "hotspotReservation", "networkCallback", "Landroid/net/ConnectivityManager$NetworkCallback;", "wifiManager", "Landroid/net/wifi/WifiManager;", "getWifiManager", "()Landroid/net/wifi/WifiManager;", "disconnect", "", "getConnectedNetwork", "observeHotspotConnection", "Lkotlinx/coroutines/flow/Flow;", "ssid", "", "password", "startHotspot", "Lkotlin/Result;", "Lcom/mrp/sml/data/remote/hotspot/HotspotInfo;", "startHotspot-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopHotspot", "app_debug"})
public final class HotspotManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Object hotspotReservation;
    @org.jetbrains.annotations.Nullable()
    private android.net.ConnectivityManager.NetworkCallback networkCallback;
    @org.jetbrains.annotations.Nullable()
    private android.net.Network connectedNetwork;
    
    @javax.inject.Inject()
    public HotspotManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.net.wifi.WifiManager getWifiManager() {
        return null;
    }
    
    private final android.net.ConnectivityManager getConnectivityManager() {
        return null;
    }
    
    public final void stopHotspot() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<kotlin.Unit> observeHotspotConnection(@org.jetbrains.annotations.NotNull()
    java.lang.String ssid, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    public final void disconnect() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.net.Network getConnectedNetwork() {
        return null;
    }
}