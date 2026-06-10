package com.mrp.sml.data.repository;

import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.utils.WifiUtils;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import com.mrp.sml.data.remote.wifi.WifiDirectManager;
import com.mrp.sml.domain.repository.ConnectionRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\nH\u0096@\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0096@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0013H\u0016J\u0010\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0013H\u0016J\u000e\u0010\u0017\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u0018\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/mrp/sml/data/repository/ConnectionRepositoryImpl;", "Lcom/mrp/sml/domain/repository/ConnectionRepository;", "deviceDiscoveryManager", "Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;", "wifiDirectManager", "Lcom/mrp/sml/data/remote/wifi/WifiDirectManager;", "(Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;Lcom/mrp/sml/data/remote/wifi/WifiDirectManager;)V", "connectToDevice", "", "deviceId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConnectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "getGroupOwnerIp", "getLocalIpAddress", "observeConnectionState", "Lkotlinx/coroutines/flow/Flow;", "observeDiscoveredDevices", "Lcom/mrp/sml/core/models/Device;", "observeGroupOwnerIp", "startDiscovery", "stopDiscovery", "app_debug"})
public final class ConnectionRepositoryImpl implements com.mrp.sml.domain.repository.ConnectionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager deviceDiscoveryManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.wifi.WifiDirectManager wifiDirectManager = null;
    
    @javax.inject.Inject()
    public ConnectionRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager deviceDiscoveryManager, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.wifi.WifiDirectManager wifiDirectManager) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.mrp.sml.core.models.ConnectionState> observeConnectionState() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.mrp.sml.core.models.Device> observeDiscoveredDevices() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.String> observeGroupOwnerIp() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object startDiscovery(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object stopDiscovery(@org.jetbrains.annotations.NotNull()
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
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getConnectionState(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.mrp.sml.core.models.ConnectionState> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getLocalIpAddress(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getGroupOwnerIp(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
}