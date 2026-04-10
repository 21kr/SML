package com.mrp.sml.data.repository

import com.mrp.sml.core.common.DispatchersProvider
import com.mrp.sml.domain.repository.FileTransferRepository
import com.mrp.sml.domain.repository.TransferProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultFileTransferRepository @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
) : FileTransferRepository {

    private val transferProgress = MutableStateFlow(
        TransferProgress(
            transferredBytes = 0L,
            totalBytes = 0L,
            speedBytesPerSecond = 0.0,
        ),
    )

    override fun observeTransferProgress(): Flow<TransferProgress> = transferProgress.asStateFlow()

    override suspend fun sendFile(sourcePath: String, destinationAddress: String) {
        withContext(dispatchersProvider.io) {
            if (sourcePath.isBlank() || destinationAddress.isBlank()) {
                throw IOException("Source path and destination address must be provided")
            }
        }
    }

    override suspend fun receiveFile(destinationPath: String) {
        withContext(dispatchersProvider.io) {
            if (destinationPath.isBlank()) {
                throw IOException("Destination path must be provided")
            }

            val bytes = ByteArray(DEFAULT_BUFFER_SIZE)
            BufferedInputStream(FileInputStream(destinationPath)).use { input ->
                BufferedOutputStream(FileOutputStream(destinationPath + ".copy")).use { output ->
                    var read = input.read(bytes)
                    var total = 0L
                    while (read >= 0) {
                        output.write(bytes, 0, read)
                        total += read
                        transferProgress.value = transferProgress.value.copy(transferredBytes = total)
                        read = input.read(bytes)
                    }
                }
            }
        }
    }
}
