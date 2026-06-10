package com.mrp.sml.data.remote.sockets

import com.mrp.sml.core.constants.TransferConstants
import com.mrp.sml.core.models.TransferProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class FileEntryJson(val name: String, val size: Long, val sha256: String)

@Serializable
data class FileMetadataJson(val files: List<FileEntryJson>)

@Singleton
class FileSender @Inject constructor(
    private val transferManager: SocketTransferManager
) {

    private var serverSocket: ServerSocket? = null
    private var cancelled = false

    private val _progress = MutableStateFlow(TransferProgress())
    val progress: StateFlow<TransferProgress> = _progress.asStateFlow()

    fun cancel() { cancelled = true }

    suspend fun sendFiles(
        files: List<File>,
        sessionToken: String,
        destinationAddress: String? = null
    ): Result<List<File>> = withContext(Dispatchers.IO) {
        cancelled = false
        try {
            val socket: Socket
            if (destinationAddress != null) {
                socket = Socket().apply {
                    connect(InetSocketAddress(destinationAddress, TransferConstants.TRANSFER_PORT), 10000)
                    soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
                    tcpNoDelay = true
                    setSendBufferSize(TransferConstants.BUFFER_SIZE)
                }
                Timber.i("FileSender: connected to receiver at $destinationAddress")
            } else {
                serverSocket = ServerSocket(TransferConstants.TRANSFER_PORT).apply { reuseAddress = true }
                Timber.i("FileSender: waiting for receiver on port ${TransferConstants.TRANSFER_PORT}")
                socket = serverSocket!!.accept().apply {
                    soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
                    tcpNoDelay = true
                    setSendBufferSize(TransferConstants.BUFFER_SIZE)
                }
                Timber.i("FileSender: receiver connected from ${socket.inetAddress.hostAddress}")
            }

            val output = DataOutputStream(BufferedOutputStream(socket.getOutputStream()))
            val input = DataInputStream(BufferedInputStream(socket.getInputStream()))

            val totalBytes = files.sumOf { it.length() }
            val metadata = FileMetadataJson(
                files = files.map { FileEntryJson(it.name, it.length(), transferManager.computeSha256(it)) }
            )
            val metadataBytes = Json.encodeToString(metadata).toByteArray()

            output.writeByte(1)
            output.writeInt(metadataBytes.size)
            output.write(metadataBytes)
            output.flush()

            val response = input.readByte()
            if (response == 3.toByte()) {
                throw Exception("Receiver rejected the transfer")
            }
            if (response != 2.toByte()) {
                throw Exception("Unexpected response from receiver")
            }

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

            output.writeByte(8)
            output.flush()

            Result.success(files)
        } catch (e: PauseException) {
            Result.failure(e)
        } catch (e: Exception) {
            if (!cancelled) Timber.e(e, "FileSender failed")
            Result.failure(e)
        } finally {
            try { serverSocket?.close() } catch (_: Exception) {}
            serverSocket = null
        }
    }
}
