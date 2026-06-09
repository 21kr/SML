package com.mrp.sml.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrp.sml.data.local.db.entities.TransferProgressEntity

@Dao
interface TransferProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: TransferProgressEntity)

    @Query("SELECT * FROM transfer_progress WHERE transfer_id = :transferId")
    suspend fun getProgress(transferId: String): TransferProgressEntity?

    @Query("SELECT transfer_id FROM transfer_progress ORDER BY last_chunk_index DESC LIMIT 1")
    suspend fun getLastPausedTransferId(): String?

    @Query("DELETE FROM transfer_progress WHERE transfer_id = :transferId")
    suspend fun delete(transferId: String)

    @Query("DELETE FROM transfer_progress")
    suspend fun clearAll()
}
