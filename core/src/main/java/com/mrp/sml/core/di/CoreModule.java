package com.mrp.sml.core.di;

import com.mrp.sml.core.common.DefaultDispatchersProvider;
import com.mrp.sml.core.common.DispatchersProvider;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class CoreModule {

    @Binds
    @Singleton
    public abstract DispatchersProvider bindDispatchersProvider(DefaultDispatchersProvider impl);
}
