package com.mrp.sml.data.repository

import com.mrp.sml.data.local.db.dao.TransferDao
import com.mrp.sml.data.local.db.entities.TransferEntity
import com.mrp.sml.data.mapper.TransferMapper
import com.mrp.sml.data.remote.sockets.FileReceiver
import com.mrp.sml.data.remote.sockets.FileSender
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.domain.repository.TransferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao,
    private val fileSender: FileSender,
    private val fileReceiver: FileReceiver
) : TransferRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val appContext: android.content.Context by lazy {
        com.mrp.sml.SMLApplication.instance
    }

    override fun observeTransfers(): Flow<List<TransferModel>> {
        return transferDao.getTransferHistory().map { entities ->
            entities.map { TransferMapper.entityToDomain(it) }
        }
    }

    override suspend fun getTransferById(id: String): TransferModel? {
        return transferDao.getTransferById(id.toLongOrNull() ?: 0L)?.let {
            TransferMapper.entityToDomain(it)
        }
    }

    override suspend fun saveTransfer(transfer: TransferModel) {
        val entity = TransferMapper.domainToEntity(transfer)
        transferDao.insert(entity)
    }

    override suspend fun updateTransferStatus(id: String, status: TransferModel.TransferStatus, error: String?) {
        val idLong = id.toLongOrNull() ?: return
        transferDao.updateStatus(
            id = idLong,
            status = status.name,
            error = error,
            completedAt = if (status == TransferModel.TransferStatus.COMPLETED || status == TransferModel.TransferStatus.FAILED) System.currentTimeMillis() else null
        )
    }

    override suspend fun updateTransferProgress(id: String, progress: Float, speed: Double) {
        val idLong = id.toLongOrNull() ?: return
        transferDao.updateProgress(idLong, progress)
    }

    override suspend fun deleteTransfer(id: String) {
        val idLong = id.toLongOrNull() ?: return
        transferDao.delete(idLong)
    }

    override suspend fun clearHistory() {
        transferDao.clearAll()
    }

    override fun sendFiles(filePaths: List<String>, destinationAddress: String, sessionToken: String) {
        scope.launch {
            val files = filePaths.map { File(it) }.filter { it.exists() && it.isFile }
            if (files.isEmpty()) return@launch

            val entity = TransferEntity(
                fileName = files.first().name,
                fileSizeBytes = files.sumOf { it.length() },
                direction = "SENT",
                status = TransferModel.TransferStatus.TRANSFERRING.name,
                sessionToken = sessionToken
            )
            val id = transferDao.insert(entity)

            val result = fileSender.sendFiles(files, sessionToken)
            result.onSuccess {
                transferDao.updateStatus(id, TransferModel.TransferStatus.COMPLETED.name)
            }.onFailure { e ->
                transferDao.updateStatus(id, TransferModel.TransferStatus.FAILED.name, e.message)
            }
        }
    }

    override fun receiveFiles(outputDirectoryPath: String, sessionToken: String) {
        scope.launch {
            val dir = File(outputDirectoryPath)
            if (!dir.exists()) dir.mkdirs()

            val entity = TransferEntity(
                fileName = "receiving...",
                fileSizeBytes = 0L,
                direction = "RECEIVED",
                status = TransferModel.TransferStatus.TRANSFERRING.name,
                sessionToken = sessionToken
            )
            val id = transferDao.insert(entity)

            val result = fileReceiver.receiveFiles(dir, sessionToken = sessionToken)
            result.onSuccess { files ->
                val firstFile = files.firstOrNull()
                if (firstFile != null) {
                    transferDao.updateStatus(id, TransferModel.TransferStatus.COMPLETED.name)
                }
            }.onFailure { e ->
                transferDao.updateStatus(id, TransferModel.TransferStatus.FAILED.name, e.message)
            }
        }
    }

    override fun cancelTransfer() {
        fileSender.cancel()
        fileReceiver.cancel()
    }

    override fun resumeTransfer() {
        // Resume last transfer from DB
        scope.launch {
            val last = transferDao.getTransferHistory().let { flow ->
                var entity: TransferEntity? = null
                flow.collect { list ->
                    entity = list.firstOrNull { it.status == TransferModel.TransferStatus.PAUSED.name }
                    return@collect
                }
                entity
            }

            last?.let {
                if (it.direction == "SENT") {
                    // Resume send - would need stored file paths
                } else {
                    // Resume receive
                }
            }
        }
    }
}
