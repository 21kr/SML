package com.mrp.sml.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\"\u0010\u000f\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0014"}, d2 = {"Lcom/mrp/sml/services/DiscoveryService;", "Landroid/app/Service;", "()V", "discoveryManager", "Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;", "getDiscoveryManager", "()Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;", "setDiscoveryManager", "(Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;)V", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onDestroy", "", "onStartCommand", "", "flags", "startId", "Companion", "app_debug"})
public final class DiscoveryService extends android.app.Service {
    @javax.inject.Inject()
    public com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager discoveryManager;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "com.mrp.sml.action.START_DISCOVERY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "com.mrp.sml.action.STOP_DISCOVERY";
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.services.DiscoveryService.Companion Companion = null;
    
    public DiscoveryService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager getDiscoveryManager() {
        return null;
    }
    
    public final void setDiscoveryManager(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager p0) {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/mrp/sml/services/DiscoveryService$Companion;", "", "()V", "ACTION_START", "", "ACTION_STOP", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}