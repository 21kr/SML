package com.mrp.sml.data.remote.wifi

import com.mrp.sml.core.constants.NetworkConstants
import com.mrp.sml.core.constants.TransferConstants
import com.mrp.sml.core.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WifiServer @Inject constructor() {

    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null

    suspend fun startServer(
        port: Int = TransferConstants.TRANSFER_PORT,
        onClientConnected: suspend (DataInputStream, DataOutputStream) -> Unit
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            serverSocket = ServerSocket(port).apply {
                reuseAddress = true
                soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
            }
            Timber.i("Server started on port $port")

            val socket = serverSocket!!.accept().also {
                it.soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
                clientSocket = it
            }
            Timber.i("Client connected: ${socket.inetAddress.hostAddress}")

            val input = DataInputStream(BufferedInputStream(socket.getInputStream()))
            val output = DataOutputStream(BufferedOutputStream(socket.getOutputStream()))

            onClientConnected(input, output)

            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Server error")
            Result.failure(e)
        }
    }

    fun stopServer() {
        try { clientSocket?.close() } catch (_: Exception) {}
        try { serverSocket?.close() } catch (_: Exception) {}
        clientSocket = null
        serverSocket = null
    }
}
