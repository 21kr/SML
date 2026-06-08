package com.mrp.sml.di

import android.content.Context
import com.mrp.sml.core.permissions.PermissionManager
import com.mrp.sml.core.utils.WifiUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providePermissionManager(context: Context): PermissionManager {
        return PermissionManager(context)
    }

    @Provides
    @Singleton
    fun provideWifiUtils(): WifiUtils = WifiUtils
}
