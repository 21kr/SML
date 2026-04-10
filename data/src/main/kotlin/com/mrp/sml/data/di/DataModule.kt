package com.mrp.sml.data.di

import com.mrp.sml.data.repository.DefaultDeviceConnectionRepository
import com.mrp.sml.data.repository.DefaultFileTransferRepository
import com.mrp.sml.domain.repository.DeviceConnectionRepository
import com.mrp.sml.domain.repository.FileTransferRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindDeviceConnectionRepository(
        impl: DefaultDeviceConnectionRepository,
    ): DeviceConnectionRepository

    @Binds
    @Singleton
    abstract fun bindFileTransferRepository(
        impl: DefaultFileTransferRepository,
    ): FileTransferRepository
}
