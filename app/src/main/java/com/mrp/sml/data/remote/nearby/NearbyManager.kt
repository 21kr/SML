package com.mrp.sml.data.remote.nearby

import android.content.Context
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy
import com.mrp.sml.core.constants.NetworkConstants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NearbyManager @Inject constructor(
    private val context: Context
) {
    private val connectionsClient: ConnectionsClient by lazy { Nearby.getConnectionsClient(context) }

    private val _discoveredEndpoints = MutableSharedFlow<String>(replay = 5)
    val discoveredEndpoints: SharedFlow<String> = _discoveredEndpoints.asSharedFlow()

    private val _connectionResult = MutableStateFlow<Boolean?>(null)
    val connectionResult: StateFlow<Boolean?> = _connectionResult.asStateFlow()

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            Timber.i("Nearby endpoint found: $endpointId (${info.endpointName})")
            _discoveredEndpoints.tryEmit(endpointId)
        }

        override fun onEndpointLost(endpointId: String) {
            Timber.i("Nearby endpoint lost: $endpointId")
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            Timber.i("Nearby connection initiated: $endpointId")
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    Timber.i("Nearby connected to: $endpointId")
                    _connectionResult.tryEmit(true)
                }
                else -> {
                    Timber.w("Nearby connection failed: ${result.status.statusCode}")
                    _connectionResult.tryEmit(false)
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            Timber.i("Nearby disconnected: $endpointId")
            _connectionResult.tryEmit(null)
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            Timber.i("Payload received from $endpointId: ${payload.type}")
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            Timber.i("Payload transfer update from $endpointId: ${update.status}")
        }
    }

    suspend fun startAdvertising(deviceName: String): Result<Unit> {
        return try {
            val options = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_POINT_TO_POINT).build()
            connectionsClient.startAdvertising(
                deviceName,
                NetworkConstants.NEARBY_SERVICE_ID,
                connectionLifecycleCallback,
                options
            ).await()
            Timber.i("Nearby advertising started as $deviceName")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Nearby advertising failed")
            Result.failure(e)
        }
    }

    suspend fun startDiscovery(): Result<Unit> {
        return try {
            val options = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_POINT_TO_POINT).build()
            connectionsClient.startDiscovery(
                NetworkConstants.NEARBY_SERVICE_ID,
                endpointDiscoveryCallback,
                options
            ).await()
            Timber.i("Nearby discovery started")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Nearby discovery failed")
            Result.failure(e)
        }
    }

    suspend fun stopAdvertising() {
        try { connectionsClient.stopAdvertising() } catch (_: Exception) {}
    }

    suspend fun stopDiscovery() {
        try { connectionsClient.stopDiscovery() } catch (_: Exception) {}
    }

    suspend fun disconnectAll() {
        try { connectionsClient.stopAllEndpoints() } catch (_: Exception) {}
    }

    fun cleanup() {
        connectionsClient.stopAllEndpoints()
    }
}
