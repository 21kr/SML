package com.mrp.sml.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mrp.sml.data.local.db.entities.TransferEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

    @Query("SELECT * FROM transfer_history ORDER BY timestamp_epoch_millis DESC")
    fun getTransferHistory(): Flow<List<TransferEntity>>

    @Query("SELECT * FROM transfer_history WHERE id = :id")
    suspend fun getTransferById(id: Long): TransferEntity?

    @Query("SELECT * FROM transfer_history WHERE session_token = :sessionToken LIMIT 1")
    suspend fun getTransferBySession(sessionToken: String): TransferEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transfer: TransferEntity): Long

    @Query("UPDATE transfer_history SET status = :status, error_message = :error, completed_at_millis = :completedAt WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String, error: String? = null, completedAt: Long? = System.currentTimeMillis())

    @Query("UPDATE transfer_history SET progress = :progress WHERE id = :id")
    suspend fun updateProgress(id: Long, progress: Float)

    @Query("DELETE FROM transfer_history WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM transfer_history")
    suspend fun clearAll()
}
