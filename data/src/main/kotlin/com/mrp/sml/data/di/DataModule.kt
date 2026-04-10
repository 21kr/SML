package com.mrp.sml.data.di

import android.content.Context
import androidx.room.Room
import com.mrp.sml.core.common.DispatchersProvider
import com.mrp.sml.data.local.SmlDatabase
import com.mrp.sml.data.local.TransferDao
import com.mrp.sml.data.repository.DefaultDeviceConnectionRepository
import com.mrp.sml.data.repository.DefaultFileTransferRepository
import com.mrp.sml.data.repository.DefaultTransferHistoryRepository
import com.mrp.sml.domain.repository.DeviceConnectionRepository
import com.mrp.sml.domain.repository.FileTransferRepository
import com.mrp.sml.domain.repository.TransferHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSmlDatabase(@ApplicationContext context: Context): SmlDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = SmlDatabase::class.java,
            name = "sml.db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTransferDao(database: SmlDatabase): TransferDao {
        return database.transferDao()
    }

    @Provides
    @Singleton
    fun provideTransferHistoryRepository(
        transferDao: TransferDao
    ): TransferHistoryRepository {
        return DefaultTransferHistoryRepository(transferDao)
    }

    @Provides
    @Singleton
    fun provideFileTransferRepository(
        dispatchersProvider: DispatchersProvider,
        transferHistoryRepository: TransferHistoryRepository
    ): FileTransferRepository {
        return DefaultFileTransferRepository(dispatchersProvider, transferHistoryRepository)
    }

    @Provides
    @Singleton
    fun provideDeviceConnectionRepository(
        @ApplicationContext context: Context,
        dispatchersProvider: DispatchersProvider
    ): DeviceConnectionRepository {
        return DefaultDeviceConnectionRepository(context, dispatchersProvider)
    }
}

