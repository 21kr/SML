package com.mrp.sml.data.remote.wifi;

import com.mrp.sml.core.constants.NetworkConstants;
import com.mrp.sml.core.constants.TransferConstants;
import com.mrp.sml.core.utils.FileUtils;
import kotlinx.coroutines.Dispatchers;
import timber.log.Timber;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002JP\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2(\u0010\f\u001a$\b\u0001\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00010\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0006\u0010\u0013\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0014"}, d2 = {"Lcom/mrp/sml/data/remote/wifi/WifiServer;", "", "()V", "clientSocket", "Ljava/net/Socket;", "serverSocket", "Ljava/net/ServerSocket;", "startServer", "Lkotlin/Result;", "", "port", "", "onClientConnected", "Lkotlin/Function3;", "Ljava/io/DataInputStream;", "Ljava/io/DataOutputStream;", "Lkotlin/coroutines/Continuation;", "startServer-0E7RQCE", "(ILkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopServer", "app_debug"})
public final class WifiServer {
    @org.jetbrains.annotations.Nullable()
    private java.net.ServerSocket serverSocket;
    @org.jetbrains.annotations.Nullable()
    private java.net.Socket clientSocket;
    
    @javax.inject.Inject()
    public WifiServer() {
        super();
    }
    
    public final void stopServer() {
    }
}