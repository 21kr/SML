package com.mrp.sml.data.remote.hotspot

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.mrp.sml.core.utils.WifiUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

data class HotspotInfo(
    val ssid: String,
    val password: String,
    val ipAddress: String
)

@Singleton
class HotspotManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var hotspotReservation: Any? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var connectedNetwork: Network? = null

    private val wifiManager: WifiManager?
        get() = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager

    private val connectivityManager: ConnectivityManager?
        get() = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    suspend fun startHotspot(): Result<HotspotInfo> = suspendCancellableCoroutine { continuation ->
        val manager = wifiManager
        if (manager == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            continuation.resume(Result.failure(Exception("Hotspot not supported on this device")))
            return@suspendCancellableCoroutine
        }

        manager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
            override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
                hotspotReservation = reservation
                val ssid = reservation.wifiConfiguration?.SSID?.trim('"') ?: ""
                val password = reservation.wifiConfiguration?.preSharedKey?.trim('"') ?: ""
                val ip = WifiUtils.getLocalIpAddress() ?: "192.168.43.1"
                Timber.i("Hotspot started: SSID=$ssid, IP=$ip")
                continuation.resume(Result.success(HotspotInfo(ssid, password, ip)))
            }

            override fun onFailed(reason: Int) {
                val msg = when (reason) {
                    WifiManager.LocalOnlyHotspotCallback.ERROR_GENERIC -> "Generic failure"
                    WifiManager.LocalOnlyHotspotCallback.ERROR_NO_CHANNEL -> "No channel available"
                    WifiManager.LocalOnlyHotspotCallback.ERROR_INCOMPATIBLE_MODE -> "Incompatible mode"
                    else -> "Unknown error ($reason)"
                }
                Timber.e("Hotspot start failed: $msg")
                continuation.resume(Result.failure(Exception(msg)))
            }

            override fun onStopped() {
                Timber.i("Hotspot stopped")
                hotspotReservation = null
            }
        }, Handler(Looper.getMainLooper()))
    }

    fun stopHotspot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (hotspotReservation as? WifiManager.LocalOnlyHotspotReservation)?.close()
        }
        hotspotReservation = null
    }

    fun observeHotspotConnection(ssid: String, password: String): Flow<Unit> = callbackFlow {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val specifier = android.net.wifi.WifiNetworkSpecifier.Builder()
                .setSsid(ssid)
                .setWpa2Passphrase(password)
                .build()

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(specifier)
                .build()

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Timber.i("Connected to hotspot: $ssid")
                    connectedNetwork = network
                    trySend(Unit)
                }

                override fun onLost(network: Network) {
                    Timber.i("Lost hotspot connection: $ssid")
                    connectedNetwork = null
                }

                override fun onUnavailable() {
                    Timber.e("Hotspot connection unavailable: $ssid")
                }
            }
            networkCallback = callback
            connectivityManager?.requestNetwork(request, callback)
        } else {
            @Suppress("DEPRECATION")
            val wifiConfig = WifiConfiguration().apply {
                SSID = "\"$ssid\""
                preSharedKey = "\"$password\""
                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            }
            val netId = wifiManager?.addNetwork(wifiConfig) ?: -1
            if (netId >= 0) {
                wifiManager?.disconnect()
                wifiManager?.enableNetwork(netId, true)
                wifiManager?.reconnect()
                trySend(Unit)
            }
        }

        awaitClose { disconnect() }
    }

    fun disconnect() {
        networkCallback?.let { connectivityManager?.unregisterNetworkCallback(it) }
        networkCallback = null
        connectedNetwork = null
    }

    fun getConnectedNetwork(): Network? = connectedNetwork
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HotspotManagerEntryPoint {
    fun hotspotManager(): HotspotManager
}
