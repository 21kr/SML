package com.mrp.sml.data.repository;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.domain.repository.ConnectionState;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0096@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001eH\u0016J\u0014\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u001eH\u0016J\b\u0010 \u001a\u00020\u0016H\u0002J\b\u0010!\u001a\u00020\u0016H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/mrp/sml/data/repository/DefaultDeviceConnectionRepository;", "Lcom/mrp/sml/domain/repository/DeviceConnectionRepository;", "context", "Landroid/content/Context;", "dispatchersProvider", "Lcom/mrp/sml/core/common/DispatchersProvider;", "(Landroid/content/Context;Lcom/mrp/sml/core/common/DispatchersProvider;)V", "channel", "Landroid/net/wifi/p2p/WifiP2pManager$Channel;", "connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/domain/repository/ConnectionState;", "discoveredDevices", "", "Lcom/mrp/sml/domain/repository/DiscoveredDevice;", "receiver", "Lcom/mrp/sml/data/repository/WifiDirectBroadcastReceiver;", "wifiP2pManager", "Landroid/net/wifi/p2p/WifiP2pManager;", "buildIntentFilter", "Landroid/content/IntentFilter;", "connectToDevice", "", "deviceId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "discoverDevices", "observeConnectionState", "Lkotlinx/coroutines/flow/Flow;", "observeDiscoveredDevices", "requestConnectionInfo", "requestPeers", "data_debug"})
public final class DefaultDeviceConnectionRepository implements com.mrp.sml.domain.repository.DeviceConnectionRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.core.common.DispatchersProvider dispatchersProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.domain.repository.ConnectionState> connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.mrp.sml.domain.repository.DiscoveredDevice>> discoveredDevices = null;
    @org.jetbrains.annotations.Nullable()
    private final android.net.wifi.p2p.WifiP2pManager wifiP2pManager = null;
    @org.jetbrains.annotations.Nullable()
    private final android.net.wifi.p2p.WifiP2pManager.Channel channel = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.repository.WifiDirectBroadcastReceiver receiver = null;
    
    @javax.inject.Inject()
    public DefaultDeviceConnectionRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.common.DispatchersProvider dispatchersProvider) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.mrp.sml.domain.repository.ConnectionState> observeConnectionState() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.mrp.sml.domain.repository.DiscoveredDevice>> observeDiscoveredDevices() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object discoverDevices(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object connectToDevice(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object disconnect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void requestPeers() {
    }
    
    private final void requestConnectionInfo() {
    }
    
    private final android.content.IntentFilter buildIntentFilter() {
        return null;
    }
}