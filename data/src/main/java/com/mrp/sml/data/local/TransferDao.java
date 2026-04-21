package com.mrp.sml.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TransferDao {
    @Query("SELECT * FROM transfer_history ORDER BY timestampEpochMillis DESC")
    List<TransferEntity> getTransferHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransferEntity record);
}
