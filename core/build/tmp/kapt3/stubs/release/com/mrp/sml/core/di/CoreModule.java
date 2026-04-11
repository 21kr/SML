package com.mrp.sml.core.di;

import com.mrp.sml.core.common.DefaultDispatchersProvider;
import com.mrp.sml.core.common.DispatchersProvider;
import dagger.Module;
import dagger.Binds;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lcom/mrp/sml/core/di/CoreModule;", "", "()V", "bindDispatchersProvider", "Lcom/mrp/sml/core/common/DispatchersProvider;", "impl", "Lcom/mrp/sml/core/common/DefaultDispatchersProvider;", "core_release"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class CoreModule {
    
    public CoreModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.mrp.sml.core.common.DispatchersProvider bindDispatchersProvider(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.common.DefaultDispatchersProvider impl);
}