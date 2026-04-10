package com.mrp.sml.domain.repository

import com.mrp.sml.domain.model.TransferRecord
import kotlinx.coroutines.flow.Flow

interface TransferHistoryRepository {
    fun observeTransferHistory(): Flow<List<TransferRecord>>

    suspend fun saveTransferRecord(record: TransferRecord)
}
