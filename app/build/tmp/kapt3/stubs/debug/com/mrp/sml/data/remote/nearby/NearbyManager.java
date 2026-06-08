package com.mrp.sml.data.remote.nearby;

import android.content.Context;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.mrp.sml.core.constants.NetworkConstants;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010\"J$\u0010#\u001a\b\u0012\u0004\u0012\u00020 0$2\u0006\u0010%\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\'J\u001c\u0010(\u001a\b\u0012\u0004\u0012\u00020 0$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b)\u0010\"J\u000e\u0010*\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010\"J\u000e\u0010+\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010\"R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006,"}, d2 = {"Lcom/mrp/sml/data/remote/nearby/NearbyManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_connectionResult", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_discoveredEndpoints", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "connectionLifecycleCallback", "Lcom/google/android/gms/nearby/connection/ConnectionLifecycleCallback;", "connectionResult", "Lkotlinx/coroutines/flow/StateFlow;", "getConnectionResult", "()Lkotlinx/coroutines/flow/StateFlow;", "connectionsClient", "Lcom/google/android/gms/nearby/connection/ConnectionsClient;", "getConnectionsClient", "()Lcom/google/android/gms/nearby/connection/ConnectionsClient;", "connectionsClient$delegate", "Lkotlin/Lazy;", "discoveredEndpoints", "Lkotlinx/coroutines/flow/SharedFlow;", "getDiscoveredEndpoints", "()Lkotlinx/coroutines/flow/SharedFlow;", "endpointDiscoveryCallback", "Lcom/google/android/gms/nearby/connection/EndpointDiscoveryCallback;", "payloadCallback", "Lcom/google/android/gms/nearby/connection/PayloadCallback;", "cleanup", "", "disconnectAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startAdvertising", "Lkotlin/Result;", "deviceName", "startAdvertising-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startDiscovery", "startDiscovery-IoAF18A", "stopAdvertising", "stopDiscovery", "app_debug"})
public final class NearbyManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy connectionsClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.String> _discoveredEndpoints = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<java.lang.String> discoveredEndpoints = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _connectionResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> connectionResult = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.EndpointDiscoveryCallback endpointDiscoveryCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.ConnectionLifecycleCallback connectionLifecycleCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.PayloadCallback payloadCallback = null;
    
    @javax.inject.Inject()
    public NearbyManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final com.google.android.gms.nearby.connection.ConnectionsClient getConnectionsClient() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<java.lang.String> getDiscoveredEndpoints() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getConnectionResult() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object stopAdvertising(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object stopDiscovery(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object disconnectAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void cleanup() {
    }
}