package com.mrp.sml.data.mapper

import com.mrp.sml.core.models.TransferDirection
import com.mrp.sml.core.models.TransferFile
import com.mrp.sml.core.models.TransferSession
import com.mrp.sml.core.models.TransferStatus
import com.mrp.sml.data.local.db.entities.TransferEntity
import com.mrp.sml.domain.model.TransferModel

object TransferMapper {

    fun sessionToDomain(session: TransferSession): TransferModel {
        return TransferModel(
            id = session.id,
            fileName = session.files.firstOrNull()?.name ?: "Unknown",
            fileSize = session.files.sumOf { it.size },
            direction = if (session.direction == TransferDirection.SEND)
                TransferModel.TransferDirection.SENT
            else
                TransferModel.TransferDirection.RECEIVED,
            status = mapStatus(session.status),
            progress = session.progress,
            speedBytesPerSecond = session.speedBytesPerSecond,
            startedAt = session.startedAt,
            completedAt = session.completedAt,
            errorMessage = session.errorMessage,
            sessionToken = session.id,
            peerDeviceName = session.deviceName,
            totalFiles = session.files.size
        )
    }

    fun entityToDomain(entity: TransferEntity): TransferModel {
        return TransferModel(
            id = entity.id.toString(),
            fileName = entity.fileName,
            fileSize = entity.fileSizeBytes,
            mimeType = entity.mimeType,
            direction = if (entity.direction == "SENT")
                TransferModel.TransferDirection.SENT
            else
                TransferModel.TransferDirection.RECEIVED,
            status = TransferModel.TransferStatus.valueOf(entity.status),
            progress = entity.progress,
            startedAt = entity.timestampEpochMillis,
            completedAt = entity.completedAtMillis,
            errorMessage = entity.errorMessage,
            sessionToken = entity.sessionToken,
            peerDeviceName = entity.peerDeviceName,
            totalFiles = entity.totalFiles
        )
    }

    fun domainToEntity(model: TransferModel): TransferEntity {
        return TransferEntity(
            fileName = model.fileName,
            fileSizeBytes = model.fileSize,
            mimeType = model.mimeType,
            direction = model.direction.name,
            status = model.status.name,
            progress = model.progress,
            sessionToken = model.sessionToken,
            timestampEpochMillis = model.startedAt,
            completedAtMillis = model.completedAt,
            errorMessage = model.errorMessage,
            peerDeviceName = model.peerDeviceName,
            totalFiles = model.totalFiles
        )
    }

    private fun mapStatus(status: TransferStatus): TransferModel.TransferStatus {
        return when (status) {
            TransferStatus.PENDING -> TransferModel.TransferStatus.PENDING
            TransferStatus.TRANSFERRING -> TransferModel.TransferStatus.TRANSFERRING
            TransferStatus.PAUSED -> TransferModel.TransferStatus.PAUSED
            TransferStatus.COMPLETED -> TransferModel.TransferStatus.COMPLETED
            TransferStatus.FAILED -> TransferModel.TransferStatus.FAILED
            TransferStatus.CANCELLED -> TransferModel.TransferStatus.CANCELLED
            TransferStatus.DISCOVERING,
            TransferStatus.CONNECTING,
            TransferStatus.RESUMING,
            TransferStatus.VERIFYING -> TransferModel.TransferStatus.PENDING
        }
    }
}
