package com.mrp.sml.di

import com.mrp.sml.data.repository.ConnectionRepositoryImpl
import com.mrp.sml.data.repository.DeviceRepositoryImpl
import com.mrp.sml.data.repository.TransferRepositoryImpl
import com.mrp.sml.domain.repository.ConnectionRepository
import com.mrp.sml.domain.repository.DeviceRepository
import com.mrp.sml.domain.repository.TransferRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransferRepository(impl: TransferRepositoryImpl): TransferRepository

    @Binds
    @Singleton
    abstract fun bindDeviceRepository(impl: DeviceRepositoryImpl): DeviceRepository

    @Binds
    @Singleton
    abstract fun bindConnectionRepository(impl: ConnectionRepositoryImpl): ConnectionRepository
}
