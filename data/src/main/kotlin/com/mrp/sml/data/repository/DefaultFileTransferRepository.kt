package com.mrp.sml.data.repository

import com.mrp.sml.core.common.DispatchersProvider
import com.mrp.sml.domain.model.TransferDirection
import com.mrp.sml.domain.model.TransferRecord
import com.mrp.sml.domain.model.TransferStatus
import com.mrp.sml.domain.repository.FileTransferRepository
import com.mrp.sml.domain.repository.TransferExecutionStatus
import com.mrp.sml.domain.repository.TransferHistoryRepository
import com.mrp.sml.domain.repository.TransferProgress
import com.mrp.sml.domain.repository.TransferStatusUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultFileTransferRepository @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val transferHistoryRepository: TransferHistoryRepository,
) : FileTransferRepository {

    private val transferProgress = MutableStateFlow(emptyTransferProgress())
    private val transferStatus = MutableStateFlow(TransferStatusUpdate(status = TransferExecutionStatus.IDLE))

    override fun observeTransferProgress(): Flow<TransferProgress> = transferProgress.asStateFlow()

    override fun observeTransferStatus(): Flow<TransferStatusUpdate> = transferStatus.asStateFlow()

    override suspend fun sendFile(sourcePath: String, destinationAddress: String) {
        sendFiles(sourcePaths = listOf(sourcePath), destinationAddress = destinationAddress)
    }

    override suspend fun sendFiles(sourcePaths: List<String>, destinationAddress: String) {
        withContext(dispatchersProvider.io) {
            transferStatus.value = TransferStatusUpdate(
                status = TransferExecutionStatus.SENDING,
                userMessage = "Preparing files for transfer",
            )

            runWithRetry(operationName = "file sending") {
                performSendFiles(
                    sourcePaths = sourcePaths,
                    destinationAddress = destinationAddress,
                )
            if (sourcePaths.isEmpty()) {
                throw IOException("At least one source path must be provided")
            }
            if (destinationAddress.isBlank()) {
                throw IOException("Destination address must be provided")
            }

            val files = sourcePaths.map(::validateReadableFile)
            val totalBytes = files.sumOf { it.length() }
            resetProgress(totalBytes = totalBytes)

            Socket(destinationAddress, DEFAULT_TRANSFER_PORT).use { socket ->
                socket.soTimeout = SOCKET_TIMEOUT_MS
                DataOutputStream(BufferedOutputStream(socket.getOutputStream())).use { output ->
                    output.writeInt(files.size)

                    val startTimeMs = System.currentTimeMillis()
                    var transferredBytes = 0L

                    files.forEach { file ->
                        output.writeUTF(file.name)
                        output.writeLong(file.length())

                        BufferedInputStream(FileInputStream(file), BUFFER_SIZE_BYTES).use { input ->
                            val buffer = ByteArray(BUFFER_SIZE_BYTES)
                            var read = input.read(buffer)
                            while (read >= 0) {
                                output.write(buffer, 0, read)
                                transferredBytes += read
                                publishProgress(
                                    transferredBytes = transferredBytes,
                                    totalBytes = totalBytes,
                                    startTimeMs = startTimeMs,
                                )
                                read = input.read(buffer)
                            }
                        }

                        transferHistoryRepository.saveTransferRecord(
                            TransferRecord(
                                fileName = file.name,
                                fileSizeBytes = file.length(),
                                mimeType = file.extension.ifBlank { "application/octet-stream" },
                                direction = TransferDirection.SENT,
                                status = TransferStatus.COMPLETED,
                                timestampEpochMillis = System.currentTimeMillis(),
                            ),
                        )
                    }

                    output.flush()
                }
            }

            transferStatus.value = TransferStatusUpdate(
                status = TransferExecutionStatus.COMPLETED,
                userMessage = "Files sent successfully",
            )
        }
    }

    override suspend fun receiveFile(destinationPath: String) {
        receiveFiles(destinationDirectoryPath = destinationPath)
    }

    override suspend fun receiveFiles(destinationDirectoryPath: String) {
        withContext(dispatchersProvider.io) {
            transferStatus.value = TransferStatusUpdate(
                status = TransferExecutionStatus.RECEIVING,
                userMessage = "Waiting for incoming files",
            )

            runWithRetry(operationName = "file receiving") {
                performReceiveFiles(destinationDirectoryPath = destinationDirectoryPath)
            }

            transferStatus.value = TransferStatusUpdate(
                status = TransferExecutionStatus.COMPLETED,
                userMessage = "Files received successfully",
            )
        }
    }

    private suspend fun performSendFiles(sourcePaths: List<String>, destinationAddress: String) {
        if (sourcePaths.isEmpty()) {
            throw IOException("At least one source path must be provided")
        }
        if (destinationAddress.isBlank()) {
            throw IOException("Destination address must be provided")
        }

        val files = sourcePaths.map(::validateReadableFile)
        val totalBytes = files.sumOf { it.length() }
        resetProgress(totalBytes = totalBytes)

        Socket(destinationAddress, DEFAULT_TRANSFER_PORT).use { socket ->
            socket.soTimeout = SOCKET_TIMEOUT_MS
            DataOutputStream(BufferedOutputStream(socket.getOutputStream())).use { output ->
                output.writeInt(files.size)

                val startTimeMs = System.currentTimeMillis()
                var transferredBytes = 0L

                files.forEach { file ->
                    output.writeUTF(file.name)
                    output.writeLong(file.length())

                    BufferedInputStream(FileInputStream(file), BUFFER_SIZE_BYTES).use { input ->
                        val buffer = ByteArray(BUFFER_SIZE_BYTES)
                        var read = input.read(buffer)
                        while (read >= 0) {
                            output.write(buffer, 0, read)
                            transferredBytes += read
                            publishProgress(
                                transferredBytes = transferredBytes,
                                totalBytes = totalBytes,
                                startTimeMs = startTimeMs,
                            )
                            read = input.read(buffer)
            val destinationDirectory = validateDestinationDirectory(destinationDirectoryPath)

            ServerSocket(DEFAULT_TRANSFER_PORT).use { serverSocket ->
                serverSocket.soTimeout = SOCKET_TIMEOUT_MS
                serverSocket.accept().use { socket ->
                    socket.soTimeout = SOCKET_TIMEOUT_MS
                    DataInputStream(BufferedInputStream(socket.getInputStream())).use { input ->
                        val fileCount = input.readInt()
                        if (fileCount <= 0) {
                            throw IOException("Sender did not provide any files")
                        }

                        val incomingFiles = mutableListOf<IncomingFile>()
                        var totalBytes = 0L
                        repeat(fileCount) {
                            val name = input.readUTF()
                            val fileSize = input.readLong()
                            if (fileSize < 0) {
                                throw IOException("Invalid incoming file size: $fileSize")
                            }
                            incomingFiles += IncomingFile(name = name, sizeBytes = fileSize)
                            totalBytes += fileSize
                        }

                        resetProgress(totalBytes = totalBytes)
                        val startTimeMs = System.currentTimeMillis()
                        var transferredBytes = 0L

                        incomingFiles.forEach { incomingFile ->
                            val outputFile = resolveSafeOutputFile(
                                destinationDirectory = destinationDirectory,
                                fileName = incomingFile.name,
                            )

                            BufferedOutputStream(FileOutputStream(outputFile), BUFFER_SIZE_BYTES).use { output ->
                                var remainingBytes = incomingFile.sizeBytes
                                val buffer = ByteArray(BUFFER_SIZE_BYTES)

                                while (remainingBytes > 0) {
                                    val bytesToRead = minOf(buffer.size.toLong(), remainingBytes).toInt()
                                    val read = input.read(buffer, 0, bytesToRead)
                                    if (read < 0) {
                                        throw IOException("Stream ended before file transfer completed")
                                    }

                                    output.write(buffer, 0, read)
                                    remainingBytes -= read
                                    transferredBytes += read
                                    publishProgress(
                                        transferredBytes = transferredBytes,
                                        totalBytes = totalBytes,
                                        startTimeMs = startTimeMs,
                                    )
                                }

                                output.flush()
                            }

                            transferHistoryRepository.saveTransferRecord(
                                TransferRecord(
                                    fileName = outputFile.name,
                                    fileSizeBytes = outputFile.length(),
                                    mimeType = outputFile.extension.ifBlank { "application/octet-stream" },
                                    direction = TransferDirection.RECEIVED,
                                    status = TransferStatus.COMPLETED,
                                    timestampEpochMillis = System.currentTimeMillis(),
                                ),
                            )
                        }
                    }

                    transferHistoryRepository.saveTransferRecord(
                        TransferRecord(
                            fileName = file.name,
                            fileSizeBytes = file.length(),
                            mimeType = file.extension.ifBlank { "application/octet-stream" },
                            direction = TransferDirection.SENT,
                            status = TransferStatus.COMPLETED,
                            timestampEpochMillis = System.currentTimeMillis(),
                        ),
                    )
                }

                output.flush()
            }
        }
    }

    private suspend fun performReceiveFiles(destinationDirectoryPath: String) {
        val destinationDirectory = validateDestinationDirectory(destinationDirectoryPath)

        ServerSocket(DEFAULT_TRANSFER_PORT).use { serverSocket ->
            serverSocket.soTimeout = SOCKET_TIMEOUT_MS
            serverSocket.accept().use { socket ->
                socket.soTimeout = SOCKET_TIMEOUT_MS
                DataInputStream(BufferedInputStream(socket.getInputStream())).use { input ->
                    val fileCount = input.readInt()
                    if (fileCount <= 0) {
                        throw IOException("Sender did not provide any files")
                    }

                    val incomingFiles = mutableListOf<IncomingFile>()
                    var totalBytes = 0L
                    repeat(fileCount) {
                        val name = input.readUTF()
                        val fileSize = input.readLong()
                        if (fileSize < 0) {
                            throw IOException("Invalid incoming file size: $fileSize")
                        }
                        incomingFiles += IncomingFile(name = name, sizeBytes = fileSize)
                        totalBytes += fileSize
                    }

                    resetProgress(totalBytes = totalBytes)
                    val startTimeMs = System.currentTimeMillis()
                    var transferredBytes = 0L

                    incomingFiles.forEach { incomingFile ->
                        val outputFile = resolveSafeOutputFile(
                            destinationDirectory = destinationDirectory,
                            fileName = incomingFile.name,
                        )

                        BufferedOutputStream(FileOutputStream(outputFile), BUFFER_SIZE_BYTES).use { output ->
                            var remainingBytes = incomingFile.sizeBytes
                            val buffer = ByteArray(BUFFER_SIZE_BYTES)

                            while (remainingBytes > 0) {
                                val bytesToRead = minOf(buffer.size.toLong(), remainingBytes).toInt()
                                val read = input.read(buffer, 0, bytesToRead)
                                if (read < 0) {
                                    throw IOException("Connection dropped before file transfer completed")
                                }

                                output.write(buffer, 0, read)
                                remainingBytes -= read
                                transferredBytes += read
                                publishProgress(
                                    transferredBytes = transferredBytes,
                                    totalBytes = totalBytes,
                                    startTimeMs = startTimeMs,
                                )
                            }

                            output.flush()
                        }

                        transferHistoryRepository.saveTransferRecord(
                            TransferRecord(
                                fileName = outputFile.name,
                                fileSizeBytes = outputFile.length(),
                                mimeType = outputFile.extension.ifBlank { "application/octet-stream" },
                                direction = TransferDirection.RECEIVED,
                                status = TransferStatus.COMPLETED,
                                timestampEpochMillis = System.currentTimeMillis(),
                            ),
                        )
                    }
                }
            }
        }
    }

    private suspend fun runWithRetry(operationName: String, block: suspend () -> Unit) {
        var attempt = 1
        var lastError: IOException? = null

        while (attempt <= MAX_RETRY_ATTEMPTS) {
            try {
                block()
                return
            } catch (error: IOException) {
                lastError = error
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    transferStatus.value = TransferStatusUpdate(
                        status = TransferExecutionStatus.RETRYING,
                        userMessage = "${friendlyError(error)} Retrying ${attempt + 1}/$MAX_RETRY_ATTEMPTS...",
                    )
                }
            }
            attempt += 1
        }

        val userMessage = "Unable to complete $operationName. ${friendlyError(lastError)}"
        transferStatus.value = TransferStatusUpdate(
            status = TransferExecutionStatus.FAILED,
            userMessage = userMessage,
        )
        throw IOException(userMessage, lastError)
    }

    private fun friendlyError(error: IOException?): String {
        if (error == null) return "Unknown connection error."

        val message = error.message.orEmpty().lowercase()
        return when {
            "timed out" in message -> "Connection timed out."
            "refused" in message -> "Could not connect to target device."
            "dropped" in message || "reset" in message -> "Connection dropped during transfer."
            "not exist" in message -> "Selected file is no longer available."
            else -> "Transfer failed due to a network or file issue."
        }
    }

    private fun validateReadableFile(sourcePath: String): File {
        if (sourcePath.isBlank()) {
            throw IOException("Source path must not be blank")
        }

        val file = File(sourcePath)
        if (!file.exists() || !file.isFile) {
            throw IOException("Source file does not exist: $sourcePath")
        }
        if (!file.canRead()) {
            throw IOException("Source file is not readable: $sourcePath")
        }

        return file
    }

    private fun validateDestinationDirectory(destinationPath: String): File {
        if (destinationPath.isBlank()) {
            throw IOException("Destination path must not be blank")
        }

        val directory = File(destinationPath)
        if (!directory.exists() && !directory.mkdirs()) {
            throw IOException("Unable to create destination directory: $destinationPath")
        }
        if (!directory.isDirectory) {
            throw IOException("Destination path is not a directory: $destinationPath")
        }

        return directory
    }

    private fun resolveSafeOutputFile(destinationDirectory: File, fileName: String): File {
        val safeName = fileName.substringAfterLast('/').substringAfterLast('\\').ifBlank {
            "received_${System.currentTimeMillis()}"
        }
        return File(destinationDirectory, safeName)
    }

    private fun publishProgress(transferredBytes: Long, totalBytes: Long, startTimeMs: Long) {
        val elapsedSeconds = ((System.currentTimeMillis() - startTimeMs).coerceAtLeast(1L)) / 1000.0
        val speedBytesPerSecond = transferredBytes / elapsedSeconds
        val progressPercent = if (totalBytes > 0L) {
            (transferredBytes.toDouble() / totalBytes.toDouble() * 100.0).toFloat().coerceIn(0f, 100f)
        } else {
            0f
        }

        transferProgress.value = TransferProgress(
            transferredBytes = transferredBytes,
            totalBytes = totalBytes,
            speedBytesPerSecond = speedBytesPerSecond,
            progressPercent = progressPercent,
        )
    }

    private fun resetProgress(totalBytes: Long) {
        transferProgress.value = TransferProgress(
            transferredBytes = 0L,
            totalBytes = totalBytes,
            speedBytesPerSecond = 0.0,
            progressPercent = 0f,
        )
    }

    private fun emptyTransferProgress(): TransferProgress = TransferProgress(
        transferredBytes = 0L,
        totalBytes = 0L,
        speedBytesPerSecond = 0.0,
        progressPercent = 0f,
    )

    private data class IncomingFile(
        val name: String,
        val sizeBytes: Long,
    )

    private companion object {
        private const val DEFAULT_TRANSFER_PORT = 8988
        private const val BUFFER_SIZE_BYTES = 64 * 1024
        private const val SOCKET_TIMEOUT_MS = 20_000
        private const val MAX_RETRY_ATTEMPTS = 3
    }

    private fun validateReadableFile(sourcePath: String): File {
        if (sourcePath.isBlank()) {
            throw IOException("Source path must not be blank")
        }

        val file = File(sourcePath)
        if (!file.exists() || !file.isFile) {
            throw IOException("Source file does not exist: $sourcePath")
        }
        if (!file.canRead()) {
            throw IOException("Source file is not readable: $sourcePath")
        }

        return file
    }

    private fun validateDestinationDirectory(destinationPath: String): File {
        if (destinationPath.isBlank()) {
            throw IOException("Destination path must not be blank")
        }

        val directory = File(destinationPath)
        if (!directory.exists() && !directory.mkdirs()) {
            throw IOException("Unable to create destination directory: $destinationPath")
        }
        if (!directory.isDirectory) {
            throw IOException("Destination path is not a directory: $destinationPath")
        }

        return directory
    }

    private fun resolveSafeOutputFile(destinationDirectory: File, fileName: String): File {
        val safeName = fileName.substringAfterLast('/').substringAfterLast('\\').ifBlank {
            "received_${System.currentTimeMillis()}"
        }
        return File(destinationDirectory, safeName)
    }

    private fun publishProgress(transferredBytes: Long, totalBytes: Long, startTimeMs: Long) {
        val elapsedSeconds = ((System.currentTimeMillis() - startTimeMs).coerceAtLeast(1L)) / 1000.0
        val speedBytesPerSecond = transferredBytes / elapsedSeconds
        val progressPercent = if (totalBytes > 0L) {
            (transferredBytes.toDouble() / totalBytes.toDouble() * 100.0).toFloat().coerceIn(0f, 100f)
        } else {
            0f
        }

        transferProgress.value = TransferProgress(
            transferredBytes = transferredBytes,
            totalBytes = totalBytes,
            speedBytesPerSecond = speedBytesPerSecond,
            progressPercent = progressPercent,
        )
    }

    private fun resetProgress(totalBytes: Long) {
        transferProgress.value = TransferProgress(
            transferredBytes = 0L,
            totalBytes = totalBytes,
            speedBytesPerSecond = 0.0,
            progressPercent = 0f,
        )
    }

    private fun emptyTransferProgress(): TransferProgress = TransferProgress(
        transferredBytes = 0L,
        totalBytes = 0L,
        speedBytesPerSecond = 0.0,
        progressPercent = 0f,
    )

    private data class IncomingFile(
        val name: String,
        val sizeBytes: Long,
    )

    private companion object {
        private const val DEFAULT_TRANSFER_PORT = 8988
        private const val BUFFER_SIZE_BYTES = 64 * 1024
        private const val SOCKET_TIMEOUT_MS = 20_000
    }
}
