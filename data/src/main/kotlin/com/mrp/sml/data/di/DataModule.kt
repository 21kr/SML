package com.mrp.sml.data.di

import android.content.Context
import androidx.room.Room
import com.mrp.sml.data.local.SmlDatabase
import com.mrp.sml.data.local.TransferDao
import com.mrp.sml.data.repository.DefaultDeviceConnectionRepository
import com.mrp.sml.data.repository.DefaultFileTransferRepository
import com.mrp.sml.data.repository.DefaultTransferHistoryRepository
import com.mrp.sml.domain.repository.DeviceConnectionRepository
import com.mrp.sml.domain.repository.FileTransferRepository
import com.mrp.sml.domain.repository.TransferHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Binds
    @Singleton
    abstract fun bindTransferHistoryRepository(
        impl: DefaultTransferHistoryRepository,
    ): TransferHistoryRepository

    companion object {
        @Provides
        @Singleton
        fun provideSmlDatabase(
            @ApplicationContext context: Context,
        ): SmlDatabase {
            return Room.databaseBuilder(
                context,
                SmlDatabase::class.java,
                "sml_database",
            ).build()
        }

        @Provides
        @Singleton
        fun provideTransferDao(database: SmlDatabase): TransferDao = database.transferDao()
    }
}
