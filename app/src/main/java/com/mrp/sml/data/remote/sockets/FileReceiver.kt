package com.mrp.sml.data.remote.sockets

import com.mrp.sml.core.constants.NetworkConstants
import com.mrp.sml.core.constants.TransferConstants
import com.mrp.sml.core.models.TransferProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
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

@Singleton
class FileReceiver @Inject constructor(
    private val transferManager: SocketTransferManager
) {

    private var socket: Socket? = null
    private var cancelled = false

    private val _progress = MutableStateFlow(TransferProgress())
    val progress: StateFlow<TransferProgress> = _progress.asStateFlow()

    fun cancel() { cancelled = true }

    suspend fun receiveFiles(
        outputDirectory: File,
        senderAddress: String = NetworkConstants.DEFAULT_GROUP_OWNER_IP,
        sessionToken: String = ""
    ): Result<List<File>> = withContext(Dispatchers.IO) {
        cancelled = false
        try {
            socket = Socket().apply {
                connect(InetSocketAddress(senderAddress, TransferConstants.TRANSFER_PORT), 10000)
                soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
                tcpNoDelay = true
                setReceiveBufferSize(TransferConstants.BUFFER_SIZE)
            }
            Timber.i("FileReceiver: connected to sender $senderAddress")
            receiveFromSocket(socket!!, outputDirectory, sessionToken)
        } catch (e: PauseException) {
            Result.failure(e)
        } catch (e: Exception) {
            if (!cancelled) Timber.e(e, "FileReceiver failed")
            Result.failure(e)
        } finally {
            try { socket?.close() } catch (_: Exception) {}
            socket = null
        }
    }

    suspend fun listenForFiles(
        outputDirectory: File,
        sessionToken: String = ""
    ): Result<List<File>> = withContext(Dispatchers.IO) {
        cancelled = false
        val serverSocket = ServerSocket(TransferConstants.TRANSFER_PORT).apply { reuseAddress = true }
        try {
            Timber.i("FileReceiver: listening for sender on port ${TransferConstants.TRANSFER_PORT}")
            socket = serverSocket.accept().apply {
                soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
                tcpNoDelay = true
                setReceiveBufferSize(TransferConstants.BUFFER_SIZE)
            }
            Timber.i("FileReceiver: sender connected from ${socket!!.inetAddress.hostAddress}")
            receiveFromSocket(socket!!, outputDirectory, sessionToken)
        } catch (e: PauseException) {
            Result.failure(e)
        } catch (e: Exception) {
            if (!cancelled) Timber.e(e, "FileReceiver failed")
            Result.failure(e)
        } finally {
            try { serverSocket.close() } catch (_: Exception) {}
            try { socket?.close() } catch (_: Exception) {}
            socket = null
        }
    }

    private suspend fun receiveFromSocket(
        socket: Socket,
        outputDirectory: File,
        sessionToken: String
    ): Result<List<File>> {
        val input = DataInputStream(BufferedInputStream(socket.getInputStream()))
        val output = DataOutputStream(BufferedOutputStream(socket.getOutputStream()))

        val msgType = input.readByte()
        if (msgType != 1.toByte()) throw Exception("Expected metadata, got $msgType")

        val metaLength = input.readInt()
        val metaBytes = ByteArray(metaLength).also { input.readFully(it) }
        val metaJson = String(metaBytes)

        output.writeByte(2)
        output.flush()

        transferManager.setSessionToken(sessionToken)
        val receivedFiles = mutableListOf<File>()
        var totalTransferred = 0L
        val startTime = System.currentTimeMillis()

        val totalBytes = try {
            Json.decodeFromString<FileMetadataJson>(metaJson).files.sumOf { it.size }
        } catch (e: Exception) {
            0L
        }

        var fileIndex = 0
        var shouldStop = false
        while (!shouldStop) {
            if (cancelled) throw Exception("Transfer cancelled by user")

            val result = transferManager.receiveFile(input, outputDirectory, fileIndex)
            result.onSuccess { file ->
                receivedFiles.add(file)
                totalTransferred += file.length()

                val elapsed = System.currentTimeMillis() - startTime
                val speed = if (elapsed > 0) totalTransferred * 1000.0 / elapsed else 0.0
                _progress.value = TransferProgress(
                    transferredBytes = totalTransferred,
                    totalBytes = totalBytes,
                    speedBytesPerSecond = speed,
                    progressPercent = if (totalBytes > 0) (totalTransferred * 100f / totalBytes).coerceAtMost(100f) else 0f,
                    currentFileIndex = receivedFiles.size
                )
                fileIndex++
            }
            result.onFailure {
                if (it !is PauseException && !cancelled) Timber.e(it, "File receive failed")
                shouldStop = true
            }

            if (shouldStop) break

            try {
                input.mark(1)
                val next = input.readByte()
                if (next == 8.toByte()) {
                    Timber.i("All files received")
                    break
                }
                input.reset()
            } catch (_: Exception) {
                break
            }
        }

        return Result.success(receivedFiles)
    }
}
