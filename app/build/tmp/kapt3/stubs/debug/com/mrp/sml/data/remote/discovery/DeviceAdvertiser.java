package com.mrp.sml.data.remote.discovery;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import com.mrp.sml.core.constants.NetworkConstants;
import com.mrp.sml.core.utils.DeviceUtils;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0006\u0010\u0015\u001a\u00020\u0012R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u0004\u0018\u00010\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0016"}, d2 = {"Lcom/mrp/sml/data/remote/discovery/DeviceAdvertiser;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "channel", "Landroid/net/wifi/p2p/WifiP2pManager$Channel;", "getChannel", "()Landroid/net/wifi/p2p/WifiP2pManager$Channel;", "channel$delegate", "Lkotlin/Lazy;", "wifiP2pManager", "Landroid/net/wifi/p2p/WifiP2pManager;", "getWifiP2pManager", "()Landroid/net/wifi/p2p/WifiP2pManager;", "wifiP2pManager$delegate", "createGroup", "Lkotlin/Result;", "", "createGroup-d1pmJ48", "()Ljava/lang/Object;", "removeGroup", "app_debug"})
public final class DeviceAdvertiser {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy wifiP2pManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy channel$delegate = null;
    
    @javax.inject.Inject()
    public DeviceAdvertiser(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.net.wifi.p2p.WifiP2pManager getWifiP2pManager() {
        return null;
    }
    
    private final android.net.wifi.p2p.WifiP2pManager.Channel getChannel() {
        return null;
    }
    
    public final void removeGroup() {
    }
}