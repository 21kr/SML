package com.mrp.sml.core.common;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultDispatchersProvider implements DispatchersProvider {

    private final ExecutorService ioExecutor = Executors.newFixedThreadPool(4);
    private final ExecutorService computationExecutor = Executors.newFixedThreadPool(2);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Inject
    public DefaultDispatchersProvider() {
    }

    @Override
    public ExecutorService ioExecutor() {
        return ioExecutor;
    }

    @Override
    public ExecutorService computationExecutor() {
        return computationExecutor;
    }

    @Override
    public Handler mainHandler() {
        return mainHandler;
    }
}
