package com.mrp.sml.di

import android.content.Context
import androidx.room.Room
import com.mrp.sml.data.local.db.AppDatabase
import com.mrp.sml.data.local.db.dao.DeviceDao
import com.mrp.sml.data.local.db.dao.TransferDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sml_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTransferDao(database: AppDatabase): TransferDao {
        return database.transferDao()
    }

    @Provides
    fun provideDeviceDao(database: AppDatabase): DeviceDao {
        return database.deviceDao()
    }
}
