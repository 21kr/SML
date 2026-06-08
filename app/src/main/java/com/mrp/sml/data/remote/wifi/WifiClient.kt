package com.mrp.sml.data.remote.wifi

import com.mrp.sml.core.constants.TransferConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WifiClient @Inject constructor() {

    private var socket: Socket? = null

    suspend fun connectToServer(
        host: String,
        port: Int = TransferConstants.TRANSFER_PORT,
        onConnected: suspend (DataInputStream, DataOutputStream) -> Unit
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            socket = Socket().apply {
                connect(InetSocketAddress(host, port), 10000)
                soTimeout = TransferConstants.SOCKET_TIMEOUT_MS
            }
            Timber.i("Connected to server $host:$port")

            val input = DataInputStream(BufferedInputStream(socket!!.getInputStream()))
            val output = DataOutputStream(BufferedOutputStream(socket!!.getOutputStream()))

            onConnected(input, output)

            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Client connection error")
            Result.failure(e)
        }
    }

    fun disconnect() {
        try { socket?.close() } catch (_: Exception) {}
        socket = null
    }
}
