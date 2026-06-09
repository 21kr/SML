package com.mrp.sml.di

import com.mrp.sml.data.remote.discovery.DeviceAdvertiser
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager
import com.mrp.sml.data.remote.nearby.NearbyManager
import com.mrp.sml.data.remote.sockets.FileReceiver
import com.mrp.sml.data.remote.sockets.FileSender
import com.mrp.sml.data.remote.sockets.SocketTransferManager
import com.mrp.sml.data.remote.wifi.WifiClient
import com.mrp.sml.data.remote.wifi.WifiDirectManager
import com.mrp.sml.data.remote.wifi.WifiServer
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // All managed classes have @Inject constructors with @Singleton scoping.
    // Hilt resolves them automatically — no @Provides methods needed here.
}
