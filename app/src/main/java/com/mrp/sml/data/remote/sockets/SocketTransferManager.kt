package com.mrp.sml.data.remote.sockets

import com.mrp.sml.core.constants.TransferConstants
import com.mrp.sml.core.models.TransferProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketTransferManager @Inject constructor() {

    private val _progress = MutableStateFlow(TransferProgress())
    val progress: StateFlow<TransferProgress> = _progress.asStateFlow()

    private val secureRandom = SecureRandom()
    private var sessionKey: ByteArray? = null
    private var cancelled = false

    fun setSessionToken(token: String) {
        sessionKey = MessageDigest.getInstance("SHA-256").digest(token.toByteArray())
    }

    fun cancel() {
        cancelled = true
    }

    fun reset() {
        cancelled = false
        sessionKey = null
    }

    suspend fun sendFile(
        file: File,
        output: java.io.DataOutputStream,
        fileIndex: Int,
        totalFiles: Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val fileSize = file.length()
            val sha256 = computeSha256(file)
            val totalChunks = ((fileSize + TransferConstants.CHUNK_SIZE - 1) / TransferConstants.CHUNK_SIZE).toInt()
            var transferred = 0L
            val startTime = System.currentTimeMillis()

            output.writeByte(TYPE_FILE_START.toInt())
            output.writeInt(fileIndex)
            output.writeUTF(file.name)
            output.writeLong(fileSize)
            output.writeUTF(sha256)
            output.flush()

            file.inputStream().use { fileInput ->
                val buffer = ByteArray(TransferConstants.CHUNK_SIZE)
                for (chunkIndex in 0 until totalChunks) {
                    if (cancelled) throw Exception("Transfer cancelled")

                    val bytesToRead = minOf(TransferConstants.CHUNK_SIZE, (fileSize - transferred).toInt())
                    var totalRead = 0
                    while (totalRead < bytesToRead) {
                        val read = fileInput.read(buffer, totalRead, bytesToRead - totalRead)
                        if (read < 0) break
                        totalRead += read
                    }

                    val nonce = ByteArray(TransferConstants.AES_GCM_NONCE_LENGTH).also { secureRandom.nextBytes(it) }
                    val encrypted = encryptChunk(buffer.copyOf(totalRead), nonce)

                    output.writeByte(TYPE_CHUNK.toInt())
                    output.writeInt(fileIndex)
                    output.writeInt(chunkIndex)
                    output.writeInt(encrypted.size)
                    output.writeBoolean(chunkIndex == totalChunks - 1)
                    output.write(nonce)
                    output.write(encrypted)
                    output.flush()

                    transferred += totalRead
                    val progressPercent = if (fileSize > 0) (transferred * 100f / fileSize) else 0f
                    val elapsed = System.currentTimeMillis() - startTime
                    val speed = if (elapsed > 0) transferred * 1000.0 / elapsed else 0.0

                    _progress.value = TransferProgress(
                        transferredBytes = transferred,
                        totalBytes = fileSize,
                        speedBytesPerSecond = speed,
                        progressPercent = progressPercent,
                        currentFileName = file.name,
                        currentFileIndex = fileIndex,
                        totalFiles = totalFiles
                    )
                }
            }

            output.writeByte(TYPE_FILE_DONE.toInt())
            output.writeInt(fileIndex)
            output.flush()

            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Send file failed: ${file.name}")
            Result.failure(e)
        }
    }

    suspend fun receiveFile(
        input: java.io.DataInputStream,
        outputDir: File,
        fileIndex: Int
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val type = input.readByte()
            if (type != TYPE_FILE_START) throw Exception("Expected FILE_START, got $type")

            val idx = input.readInt()
            val fileName = input.readUTF()
            val fileSize = input.readLong()
            val expectedSha256 = input.readUTF()

            val outputFile = File(outputDir, sanitizeFileName(fileName))
            val totalChunks = ((fileSize + TransferConstants.CHUNK_SIZE - 1) / TransferConstants.CHUNK_SIZE).toInt()

            outputFile.outputStream().use { fileOutput ->
                for (chunkIndex in 0 until totalChunks) {
                    if (cancelled) throw Exception("Transfer cancelled")

                    val chunkType = input.readByte()
                    if (chunkType != TYPE_CHUNK) throw Exception("Expected CHUNK, got $chunkType")

                    input.readInt() // file index
                    input.readInt() // chunk index
                    val chunkSize = input.readInt()
                    input.readBoolean() // isLast

                    val nonce = ByteArray(TransferConstants.AES_GCM_NONCE_LENGTH).also { input.readFully(it) }
                    val encrypted = ByteArray(chunkSize).also { input.readFully(it) }
                    val decrypted = decryptChunk(encrypted, nonce)

                    fileOutput.write(decrypted)
                }
            }

            val actualSha256 = computeSha256(outputFile)
            if (expectedSha256 != actualSha256) {
                outputFile.delete()
                throw Exception("SHA-256 mismatch for $fileName")
            }

            Result.success(outputFile)
        } catch (e: Exception) {
            Timber.e(e, "Receive file failed")
            Result.failure(e)
        }
    }

    fun computeSha256(file: File): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            file.inputStream().use { input ->
                val buffer = ByteArray(8192)
                var read: Int
                while (input.read(buffer).also { read = it } >= 0) {
                    digest.update(buffer, 0, read)
                }
            }
            digest.digest().joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            ""
        }
    }

    private fun encryptChunk(data: ByteArray, nonce: ByteArray): ByteArray {
        val key = sessionKey ?: return data
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, nonce))
        return cipher.doFinal(data)
    }

    private fun decryptChunk(encrypted: ByteArray, nonce: ByteArray): ByteArray {
        val key = sessionKey ?: return encrypted
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, nonce))
        return cipher.doFinal(encrypted)
    }

    private fun sanitizeFileName(name: String): String {
        return name.replace("..", "").replace('/', '_').replace('\\', '_')
    }

    companion object {
        const val TYPE_FILE_START: Byte = 4
        const val TYPE_CHUNK: Byte = 5
        const val TYPE_FILE_DONE: Byte = 7
    }
}
