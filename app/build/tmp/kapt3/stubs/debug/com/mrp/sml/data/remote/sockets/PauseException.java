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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/mrp/sml/data/remote/sockets/PauseException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "()V", "app_debug"})
public final class PauseException extends java.lang.Exception {
    
    public PauseException() {
        super();
    }
}