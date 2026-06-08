package com.mrp.sml.data.remote.sockets

import com.mrp.sml.core.constants.TransferConstants
import com.mrp.sml.core.models.TransferProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.net.ServerSocket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileSender @Inject constructor() {

    private var serverSocket: ServerSocket? = null
    private var cancelled = false

    private val _progress = MutableStateFlow(TransferProgress())
    val progress: StateFlow<TransferProgress> = _progress.asStateFlow()

    fun cancel() { cancelled = true }

    suspend fun sendFiles(
        files: List<File>,
        sessionToken: String
    ): Result<List<File>> = withContext(Dispatchers.IO) {
        cancelled = false
        try {
            serverSocket = ServerSocket(TransferConstants.TRANSFER_PORT).apply { reuseAddress = true }
            Timber.i("FileSender: waiting for receiver on port ${TransferConstants.TRANSFER_PORT}")

            val socket = serverSocket!!.accept().apply {
                soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
            }
            Timber.i("FileSender: receiver connected from ${socket.inetAddress.hostAddress}")

            val output = DataOutputStream(BufferedOutputStream(socket.getOutputStream()))
            val input = DataInputStream(BufferedInputStream(socket.getInputStream()))

            val totalBytes = files.sumOf { it.length() }
            val metadata = buildMetadata(files)
            val metadataBytes = metadata.toJson().toByteArray()

            output.writeByte(1) // TYPE_METADATA
            output.writeInt(metadataBytes.size)
            output.write(metadataBytes)
            output.flush()

            val response = input.readByte()
            if (response == 3.toByte()) { // TYPE_REJECT
                throw Exception("Receiver rejected the transfer")
            }
            if (response != 2.toByte()) { // TYPE_ACCEPT
                throw Exception("Unexpected response from receiver")
            }

            val transferManager = SocketTransferManager()
            transferManager.setSessionToken(sessionToken)

            var totalTransferred = 0L
            val startTime = System.currentTimeMillis()

            for ((index, file) in files.withIndex()) {
                if (cancelled) throw Exception("Transfer cancelled by user")
                transferManager.sendFile(file, output, index, files.size)
                totalTransferred += file.length()

                val elapsed = System.currentTimeMillis() - startTime
                val speed = if (elapsed > 0) totalTransferred * 1000.0 / elapsed else 0.0
                _progress.value = TransferProgress(
                    transferredBytes = totalTransferred,
                    totalBytes = totalBytes,
                    speedBytesPerSecond = speed,
                    progressPercent = (totalTransferred * 100f / totalBytes).coerceAtMost(100f),
                    totalFiles = files.size,
                    currentFileIndex = index + 1
                )
            }

            output.writeByte(8) // TYPE_ALL_DONE
            output.flush()

            Result.success(files)
        } catch (e: Exception) {
            if (!cancelled) Timber.e(e, "FileSender failed")
            Result.failure(e)
        } finally {
            try { serverSocket?.close() } catch (_: Exception) {}
            serverSocket = null
        }
    }

    private fun buildMetadata(files: List<File>): FileMetadataJson {
        return FileMetadataJson(
            files = files.map { FileEntryJson(it.name, it.length(), "") }
        )
    }

    private data class FileMetadataJson(
        val files: List<FileEntryJson>
    ) {
        fun toJson(): String {
            val entries = files.joinToString(",") { f ->
                """{"name":"${f.name}","size":${f.size},"sha256":"${f.sha256}"}"""
            }
            return """{"files":[$entries]}"""
        }
    }

    private data class FileEntryJson(
        val name: String,
        val size: Long,
        val sha256: String
    )
}
