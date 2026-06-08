package com.mrp.sml.domain.repository

import com.mrp.sml.domain.model.TransferModel
import kotlinx.coroutines.flow.Flow

interface TransferRepository {

    fun observeTransfers(): Flow<List<TransferModel>>

    suspend fun getTransferById(id: String): TransferModel?

    suspend fun saveTransfer(transfer: TransferModel)

    suspend fun updateTransferStatus(id: String, status: TransferModel.TransferStatus, error: String? = null)

    suspend fun updateTransferProgress(id: String, progress: Float, speed: Double)

    suspend fun deleteTransfer(id: String)

    suspend fun clearHistory()

    fun sendFiles(filePaths: List<String>, destinationAddress: String, sessionToken: String)

    fun receiveFiles(outputDirectoryPath: String, sessionToken: String)

    fun cancelTransfer()

    fun pauseTransfer()

    fun resumeTransfer()

    fun retryTransfer(sessionId: String)
}
