package com.mrp.sml.di;

import com.mrp.sml.data.repository.ConnectionRepositoryImpl;
import com.mrp.sml.data.repository.DeviceRepositoryImpl;
import com.mrp.sml.data.repository.TransferRepositoryImpl;
import com.mrp.sml.domain.repository.ConnectionRepository;
import com.mrp.sml.domain.repository.DeviceRepository;
import com.mrp.sml.domain.repository.TransferRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\fH\'\u00a8\u0006\r"}, d2 = {"Lcom/mrp/sml/di/RepositoryModule;", "", "()V", "bindConnectionRepository", "Lcom/mrp/sml/domain/repository/ConnectionRepository;", "impl", "Lcom/mrp/sml/data/repository/ConnectionRepositoryImpl;", "bindDeviceRepository", "Lcom/mrp/sml/domain/repository/DeviceRepository;", "Lcom/mrp/sml/data/repository/DeviceRepositoryImpl;", "bindTransferRepository", "Lcom/mrp/sml/domain/repository/TransferRepository;", "Lcom/mrp/sml/data/repository/TransferRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.mrp.sml.domain.repository.TransferRepository bindTransferRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.repository.TransferRepositoryImpl impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.mrp.sml.domain.repository.DeviceRepository bindDeviceRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.repository.DeviceRepositoryImpl impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.mrp.sml.domain.repository.ConnectionRepository bindConnectionRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.repository.ConnectionRepositoryImpl impl);
}