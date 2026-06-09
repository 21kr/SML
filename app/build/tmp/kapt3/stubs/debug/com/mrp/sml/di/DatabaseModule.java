package com.mrp.sml.di;

import android.content.Context;
import androidx.room.Room;
import com.mrp.sml.data.local.db.AppDatabase;
import com.mrp.sml.data.local.db.dao.DeviceDao;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.local.db.dao.TransferProgressDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/mrp/sml/di/DatabaseModule;", "", "()V", "provideDatabase", "Lcom/mrp/sml/data/local/db/AppDatabase;", "context", "Landroid/content/Context;", "provideDeviceDao", "Lcom/mrp/sml/data/local/db/dao/DeviceDao;", "database", "provideTransferDao", "Lcom/mrp/sml/data/local/db/dao/TransferDao;", "provideTransferProgressDao", "Lcom/mrp/sml/data/local/db/dao/TransferProgressDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.db.AppDatabase provideDatabase(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.db.dao.TransferDao provideTransferDao(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.db.dao.DeviceDao provideDeviceDao(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.db.dao.TransferProgressDao provideTransferProgressDao(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.AppDatabase database) {
        return null;
    }
}