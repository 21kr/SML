package com.mrp.sml.data.di;

import android.content.Context;
import androidx.room.Room;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.data.local.SmlDatabase;
import com.mrp.sml.data.local.TransferDao;
import com.mrp.sml.data.repository.DefaultDeviceConnectionRepository;
import com.mrp.sml.data.repository.DefaultFileTransferRepository;
import com.mrp.sml.data.repository.DefaultTransferHistoryRepository;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0012\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0007J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0010H\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/mrp/sml/data/di/DataModule;", "", "()V", "provideDeviceConnectionRepository", "Lcom/mrp/sml/domain/repository/DeviceConnectionRepository;", "context", "Landroid/content/Context;", "dispatchersProvider", "Lcom/mrp/sml/core/common/DispatchersProvider;", "provideFileTransferRepository", "Lcom/mrp/sml/domain/repository/FileTransferRepository;", "transferHistoryRepository", "Lcom/mrp/sml/domain/repository/TransferHistoryRepository;", "provideSmlDatabase", "Lcom/mrp/sml/data/local/SmlDatabase;", "provideTransferDao", "Lcom/mrp/sml/data/local/TransferDao;", "database", "provideTransferHistoryRepository", "transferDao", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DataModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.data.di.DataModule INSTANCE = null;
    
    private DataModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.SmlDatabase provideSmlDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.TransferDao provideTransferDao(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.SmlDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.repository.TransferHistoryRepository provideTransferHistoryRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.TransferDao transferDao) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.repository.FileTransferRepository provideFileTransferRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.common.DispatchersProvider dispatchersProvider, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.TransferHistoryRepository transferHistoryRepository) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.repository.DeviceConnectionRepository provideDeviceConnectionRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.common.DispatchersProvider dispatchersProvider) {
        return null;
    }
}