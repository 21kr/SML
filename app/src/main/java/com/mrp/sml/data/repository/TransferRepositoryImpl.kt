package com.mrp.sml.data.repository

import com.mrp.sml.data.local.db.dao.TransferDao
import com.mrp.sml.data.local.db.entities.TransferEntity
import com.mrp.sml.data.mapper.TransferMapper
import com.mrp.sml.data.local.db.entities.TransferProgressEntity
import com.mrp.sml.data.local.db.dao.TransferProgressDao
import com.mrp.sml.data.remote.sockets.FileReceiver
import com.mrp.sml.data.remote.sockets.FileSender
import com.mrp.sml.data.remote.sockets.SocketTransferManager
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao,
    private val transferProgressDao: TransferProgressDao,
    private val fileSender: FileSender,
    private val fileReceiver: FileReceiver,
    private val socketTransferManager: SocketTransferManager
) : TransferRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun observeTransfers(): Flow<List<TransferModel>> {
        return transferDao.getTransferHistory().map { entities ->
            entities.map { TransferMapper.entityToDomain(it) }
        }
    }

    override suspend fun getTransferById(id: String): TransferModel? {
        return transferDao.getTransferById(id)?.let {
            TransferMapper.entityToDomain(it)
        }
    }

    override suspend fun saveTransfer(transfer: TransferModel) {
        val entity = TransferMapper.domainToEntity(transfer)
        transferDao.insert(entity)
    }

    override suspend fun updateTransferStatus(id: String, status: TransferModel.TransferStatus, error: String?) {
        transferDao.updateStatus(
            id = id,
            status = status.name,
            error = error,
            completedAt = if (status == TransferModel.TransferStatus.COMPLETED || status == TransferModel.TransferStatus.FAILED) System.currentTimeMillis() else null
        )
    }

    override suspend fun updateTransferProgress(id: String, progress: Float, speed: Double) {
        transferDao.updateProgress(id, progress)
    }

    override suspend fun deleteTransfer(id: String) {
        transferDao.delete(id)
    }

    override suspend fun clearHistory() {
        transferDao.clearAll()
    }

    override fun sendFiles(filePaths: List<String>, destinationAddress: String, sessionToken: String) {
        scope.launch {
            val files = filePaths.map { File(it) }.filter { it.exists() && it.isFile }
            if (files.isEmpty()) return@launch

            val entity = TransferEntity(
                id = sessionToken,
                fileName = files.first().name,
                fileSizeBytes = files.sumOf { it.length() },
                direction = "SENT",
                status = TransferModel.TransferStatus.TRANSFERRING.name,
                sessionToken = sessionToken
            )
            transferDao.insert(entity)

            val result = fileSender.sendFiles(files, sessionToken)
            result.onSuccess {
                transferDao.updateStatus(sessionToken, TransferModel.TransferStatus.COMPLETED.name)
            }.onFailure { e ->
                transferDao.updateStatus(sessionToken, TransferModel.TransferStatus.FAILED.name, e.message)
            }
        }
    }

    override fun receiveFiles(outputDirectoryPath: String, sessionToken: String) {
        scope.launch {
            val dir = File(outputDirectoryPath)
            if (!dir.exists()) dir.mkdirs()

            val entity = TransferEntity(
                id = sessionToken,
                fileName = "receiving...",
                fileSizeBytes = 0L,
                direction = "RECEIVED",
                status = TransferModel.TransferStatus.TRANSFERRING.name,
                sessionToken = sessionToken
            )
            transferDao.insert(entity)

            val result = fileReceiver.receiveFiles(dir, sessionToken = sessionToken)
            result.onSuccess { files ->
                if (files.isNotEmpty()) {
                    transferDao.updateStatus(sessionToken, TransferModel.TransferStatus.COMPLETED.name)
                }
            }.onFailure { e ->
                transferDao.updateStatus(sessionToken, TransferModel.TransferStatus.FAILED.name, e.message)
            }
        }
    }

    override fun cancelTransfer() {
        fileSender.cancel()
        fileReceiver.cancel()
        socketTransferManager.cancel()
    }

    override fun pauseTransfer() {
        socketTransferManager.pause()
    }

    override fun resumeTransfer() {
        scope.launch {
            val sessionToken = transferProgressDao.getLastPausedTransferId() ?: return@launch
            socketTransferManager.resume()
            transferDao.updateStatus(sessionToken, TransferModel.TransferStatus.TRANSFERRING.name)
        }
    }

    override fun retryTransfer(sessionId: String) {
        scope.launch {
            val transfer = transferDao.getTransferById(sessionId) ?: return@launch
            transferDao.updateStatus(
                id = transfer.id,
                status = TransferModel.TransferStatus.PENDING.name
            )
        }
    }
}
