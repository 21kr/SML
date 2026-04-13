package com.mrp.sml.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TransferEntity.class}, version = 1, exportSchema = false)
public abstract class SmlDatabase extends RoomDatabase {
    public abstract TransferDao transferDao();
}
