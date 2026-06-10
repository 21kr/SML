package com.mrp.sml.data.remote.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.MacAddress;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.models.DeviceType;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u00018B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010&\u001a\b\u0012\u0004\u0012\u00020(0\'2\u0006\u0010)\u001a\u00020\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b*\u0010+J\u001c\u0010,\u001a\b\u0012\u0004\u0012\u00020(0\'H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b-\u0010.J\u001c\u0010/\u001a\b\u0012\u0004\u0012\u00020(0\'H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b0\u0010.J\u0006\u00101\u001a\u00020(J\"\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n030\'H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b4\u0010.J\u0006\u00105\u001a\u00020(J\f\u00106\u001a\u00020\n*\u000207H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\u0012\u001a\u0004\b!\u0010\"R\u0012\u0010$\u001a\u00060%R\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00069"}, d2 = {"Lcom/mrp/sml/data/remote/wifi/WifiDirectManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/core/models/ConnectionState;", "_discoveredDevices", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/mrp/sml/core/models/Device;", "_groupOwnerIp", "", "channel", "Landroid/net/wifi/p2p/WifiP2pManager$Channel;", "getChannel", "()Landroid/net/wifi/p2p/WifiP2pManager$Channel;", "channel$delegate", "Lkotlin/Lazy;", "connectionState", "Lkotlinx/coroutines/flow/StateFlow;", "getConnectionState", "()Lkotlinx/coroutines/flow/StateFlow;", "discoveredDevices", "Lkotlinx/coroutines/flow/SharedFlow;", "getDiscoveredDevices", "()Lkotlinx/coroutines/flow/SharedFlow;", "groupOwnerIp", "getGroupOwnerIp", "isRegistered", "", "manager", "Landroid/net/wifi/p2p/WifiP2pManager;", "getManager", "()Landroid/net/wifi/p2p/WifiP2pManager;", "manager$delegate", "receiver", "Lcom/mrp/sml/data/remote/wifi/WifiDirectManager$WifiP2PReceiver;", "connectToDevice", "Lkotlin/Result;", "", "deviceAddress", "connectToDevice-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "disconnect-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "discoverPeers", "discoverPeers-IoAF18A", "registerReceiver", "requestPeers", "", "requestPeers-IoAF18A", "unregisterReceiver", "toDevice", "Landroid/net/wifi/p2p/WifiP2pDevice;", "WifiP2PReceiver", "app_debug"})
public final class WifiDirectManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy manager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy channel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.core.models.ConnectionState> _connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.ConnectionState> connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.mrp.sml.core.models.Device> _discoveredDevices = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.mrp.sml.core.models.Device> discoveredDevices = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _groupOwnerIp = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> groupOwnerIp = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.wifi.WifiDirectManager.WifiP2PReceiver receiver = null;
    private boolean isRegistered = false;
    
    @javax.inject.Inject()
    public WifiDirectManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.net.wifi.p2p.WifiP2pManager getManager() {
        return null;
    }
    
    private final android.net.wifi.p2p.WifiP2pManager.Channel getChannel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.ConnectionState> getConnectionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.mrp.sml.core.models.Device> getDiscoveredDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getGroupOwnerIp() {
        return null;
    }
    
    public final void registerReceiver() {
    }
    
    public final void unregisterReceiver() {
    }
    
    private final com.mrp.sml.core.models.Device toDevice(android.net.wifi.p2p.WifiP2pDevice $this$toDevice) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2 = {"Lcom/mrp/sml/data/remote/wifi/WifiDirectManager$WifiP2PReceiver;", "Landroid/content/BroadcastReceiver;", "(Lcom/mrp/sml/data/remote/wifi/WifiDirectManager;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
    final class WifiP2PReceiver extends android.content.BroadcastReceiver {
        
        public WifiP2PReceiver() {
            super();
        }
        
        @java.lang.Override()
        public void onReceive(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        android.content.Intent intent) {
        }
    }
}