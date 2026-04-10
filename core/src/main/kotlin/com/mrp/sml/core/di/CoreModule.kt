package com.mrp.sml.core.di

import com.mrp.sml.core.common.DefaultDispatchersProvider
import com.mrp.sml.core.common.DispatchersProvider
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindDispatchersProvider(
        impl: DefaultDispatchersProvider
    ): DispatchersProvider
}

