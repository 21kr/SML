package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.db.dao.DeviceDao;
import com.mrp.sml.data.mapper.DeviceMapper;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import com.mrp.sml.domain.model.DeviceModel;
import com.mrp.sml.domain.repository.DeviceRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096@\u00a2\u0006\u0002\u0010\rJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00140\u0013H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/mrp/sml/data/repository/DeviceRepositoryImpl;", "Lcom/mrp/sml/domain/repository/DeviceRepository;", "deviceDiscoveryManager", "Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;", "deviceDao", "Lcom/mrp/sml/data/local/db/dao/DeviceDao;", "(Lcom/mrp/sml/data/remote/discovery/DeviceDiscoveryManager;Lcom/mrp/sml/data/local/db/dao/DeviceDao;)V", "connectToDevice", "", "deviceId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConnectedDevice", "Lcom/mrp/sml/domain/model/DeviceModel;", "isConnected", "", "observeDiscoveredDevices", "Lkotlinx/coroutines/flow/Flow;", "", "app_debug"})
public final class DeviceRepositoryImpl implements com.mrp.sml.domain.repository.DeviceRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager deviceDiscoveryManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.local.db.dao.DeviceDao deviceDao = null;
    
    @javax.inject.Inject()
    public DeviceRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager deviceDiscoveryManager, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.dao.DeviceDao deviceDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.mrp.sml.domain.model.DeviceModel>> observeDiscoveredDevices() {
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
    public java.lang.Object getConnectedDevice(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.mrp.sml.domain.model.DeviceModel> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public boolean isConnected() {
        return false;
    }
}