package com.mrp.sml.core.common;

import android.os.Handler;
import java.util.concurrent.ExecutorService;

public interface DispatchersProvider {
    ExecutorService ioExecutor();
    ExecutorService computationExecutor();
    Handler mainHandler();
}
