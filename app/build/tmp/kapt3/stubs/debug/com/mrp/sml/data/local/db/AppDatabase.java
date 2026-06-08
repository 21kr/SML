package com.mrp.sml.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.mrp.sml.data.local.db.dao.DeviceDao;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.local.db.entities.DeviceEntity;
import com.mrp.sml.data.local.db.entities.TransferEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/mrp/sml/data/local/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "deviceDao", "Lcom/mrp/sml/data/local/db/dao/DeviceDao;", "transferDao", "Lcom/mrp/sml/data/local/db/dao/TransferDao;", "app_debug"})
@androidx.room.Database(entities = {com.mrp.sml.data.local.db.entities.TransferEntity.class, com.mrp.sml.data.local.db.entities.DeviceEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.mrp.sml.data.local.db.dao.TransferDao transferDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.mrp.sml.data.local.db.dao.DeviceDao deviceDao();
}