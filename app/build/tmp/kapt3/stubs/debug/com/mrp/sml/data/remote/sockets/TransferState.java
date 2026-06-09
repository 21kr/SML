package com.mrp.sml.data.remote.sockets;

import com.mrp.sml.core.constants.TransferConstants;
import com.mrp.sml.core.models.TransferProgress;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import java.io.File;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/mrp/sml/data/remote/sockets/TransferState;", "", "(Ljava/lang/String;I)V", "IDLE", "TRANSFERRING", "PAUSED", "CANCELLED", "COMPLETED", "app_debug"})
public enum TransferState {
    /*public static final*/ IDLE /* = new IDLE() */,
    /*public static final*/ TRANSFERRING /* = new TRANSFERRING() */,
    /*public static final*/ PAUSED /* = new PAUSED() */,
    /*public static final*/ CANCELLED /* = new CANCELLED() */,
    /*public static final*/ COMPLETED /* = new COMPLETED() */;
    
    TransferState() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.mrp.sml.data.remote.sockets.TransferState> getEntries() {
        return null;
    }
}