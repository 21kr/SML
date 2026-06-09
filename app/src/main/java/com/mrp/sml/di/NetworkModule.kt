package com.mrp.sml.di

import android.content.Context
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
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWifiDirectManager(@ApplicationContext context: Context): WifiDirectManager {
        return WifiDirectManager(context)
    }

    @Provides
    @Singleton
    fun provideNearbyManager(@ApplicationContext context: Context): NearbyManager {
        return NearbyManager(context)
    }

    @Provides
    @Singleton
    fun provideDeviceDiscoveryManager(
        @ApplicationContext context: Context,
        wifiDirectManager: WifiDirectManager,
        nearbyManager: NearbyManager
    ): DeviceDiscoveryManager {
        return DeviceDiscoveryManager(context, wifiDirectManager, nearbyManager)
    }

    @Provides
    @Singleton
    fun provideDeviceAdvertiser(@ApplicationContext context: Context): DeviceAdvertiser {
        return DeviceAdvertiser(context)
    }

    @Provides
    @Singleton
    fun provideWifiServer(): WifiServer = WifiServer()

    @Provides
    @Singleton
    fun provideWifiClient(): WifiClient = WifiClient()

    @Provides
    @Singleton
    fun provideSocketTransferManager(): SocketTransferManager = SocketTransferManager()

    @Provides
    @Singleton
    fun provideFileSender(transferManager: SocketTransferManager): FileSender = FileSender(transferManager)

    @Provides
    @Singleton
    fun provideFileReceiver(transferManager: SocketTransferManager): FileReceiver = FileReceiver(transferManager)
}
