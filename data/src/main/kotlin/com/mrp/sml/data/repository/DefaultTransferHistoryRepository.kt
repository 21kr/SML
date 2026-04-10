package com.mrp.sml.data.repository

import com.mrp.sml.data.local.TransferDao
import com.mrp.sml.data.local.TransferEntity
import com.mrp.sml.domain.model.TransferDirection
import com.mrp.sml.domain.model.TransferRecord
import com.mrp.sml.domain.model.TransferStatus
import com.mrp.sml.domain.repository.TransferHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultTransferHistoryRepository @Inject constructor(
    private val transferDao: TransferDao,
) : TransferHistoryRepository {

    override fun observeTransferHistory(): Flow<List<TransferRecord>> {
        return transferDao.observeTransferHistory().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun saveTransferRecord(record: TransferRecord) {
        transferDao.insert(record.toEntity())
    }
}

private fun TransferRecord.toEntity(): TransferEntity = TransferEntity(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    direction = direction.name,
    status = status.name,
    timestampEpochMillis = timestampEpochMillis,
)

private fun TransferEntity.toDomain(): TransferRecord = TransferRecord(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    direction = TransferDirection.valueOf(direction),
    status = TransferStatus.valueOf(status),
    timestampEpochMillis = timestampEpochMillis,
)
