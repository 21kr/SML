package com.mrp.sml.data.remote.sockets

import com.mrp.sml.core.constants.NetworkConstants
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
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileReceiver @Inject constructor() {

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
            }
            Timber.i("FileReceiver: connected to sender $senderAddress")

            val input = DataInputStream(BufferedInputStream(socket!!.getInputStream()))
            val output = DataOutputStream(BufferedOutputStream(socket!!.getOutputStream()))

            val msgType = input.readByte()
            if (msgType != 1.toByte()) throw Exception("Expected metadata, got $msgType")

            val metaLength = input.readInt()
            val metaBytes = ByteArray(metaLength).also { input.readFully(it) }
            val metaJson = String(metaBytes)

            output.writeByte(2) // TYPE_ACCEPT
            output.flush()

            val transferManager = SocketTransferManager()
            transferManager.setSessionToken(sessionToken)
            val receivedFiles = mutableListOf<File>()
            var totalTransferred = 0L
            val startTime = System.currentTimeMillis()

            // Parse metadata to get total size
            val totalBytes = parseTotalSize(metaJson)

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
                    if (!cancelled) Timber.e(it, "File receive failed")
                    shouldStop = true
                }

                if (shouldStop) break

                // Try to read ALL_DONE marker
                try {
                    input.mark(1)
                    val next = input.readByte()
                    if (next == 8.toByte()) { // TYPE_ALL_DONE
                        Timber.i("All files received")
                        break
                    }
                    input.reset()
                } catch (_: Exception) {
                    break
                }
            }

            Result.success(receivedFiles)
        } catch (e: Exception) {
            if (!cancelled) Timber.e(e, "FileReceiver failed")
            Result.failure(e)
        } finally {
            try { socket?.close() } catch (_: Exception) {}
            socket = null
        }
    }

    private fun parseTotalSize(json: String): Long {
        return try {
            val regex = "\"size\":(\\d+)".toRegex()
            regex.findAll(json).sumOf { it.groupValues[1].toLong() }
        } catch (e: Exception) {
            0L
        }
    }
}
