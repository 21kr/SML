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

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 /2\u00020\u0001:\u0001/B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0002J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fH\u0002J4\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00150\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010#J\u0006\u0010$\u001a\u00020\u0011J\u0010\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u0013H\u0002J<\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00110\u001c2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010(\u001a\u00020)2\u0006\u0010 \u001a\u00020!2\u0006\u0010*\u001a\u00020!H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b+\u0010,J\u000e\u0010-\u001a\u00020\u00112\u0006\u0010.\u001a\u00020\u0013R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00060"}, d2 = {"Lcom/mrp/sml/data/remote/sockets/SocketTransferManager;", "", "()V", "_progress", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/core/models/TransferProgress;", "cancelled", "", "progress", "Lkotlinx/coroutines/flow/StateFlow;", "getProgress", "()Lkotlinx/coroutines/flow/StateFlow;", "secureRandom", "Ljava/security/SecureRandom;", "sessionKey", "", "cancel", "", "computeSha256", "", "file", "Ljava/io/File;", "decryptChunk", "encrypted", "nonce", "encryptChunk", "data", "receiveFile", "Lkotlin/Result;", "input", "Ljava/io/DataInputStream;", "outputDir", "fileIndex", "", "receiveFile-BWLJW6A", "(Ljava/io/DataInputStream;Ljava/io/File;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reset", "sanitizeFileName", "name", "sendFile", "output", "Ljava/io/DataOutputStream;", "totalFiles", "sendFile-yxL6bBk", "(Ljava/io/File;Ljava/io/DataOutputStream;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setSessionToken", "token", "Companion", "app_debug"})
public final class SocketTransferManager {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.core.models.TransferProgress> _progress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.TransferProgress> progress = null;
    @org.jetbrains.annotations.NotNull()
    private final java.security.SecureRandom secureRandom = null;
    @org.jetbrains.annotations.Nullable()
    private byte[] sessionKey;
    private boolean cancelled = false;
    public static final byte TYPE_FILE_START = (byte)4;
    public static final byte TYPE_CHUNK = (byte)5;
    public static final byte TYPE_FILE_DONE = (byte)7;
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.data.remote.sockets.SocketTransferManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public SocketTransferManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.TransferProgress> getProgress() {
        return null;
    }
    
    public final void setSessionToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    public final void cancel() {
    }
    
    public final void reset() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String computeSha256(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return null;
    }
    
    private final byte[] encryptChunk(byte[] data, byte[] nonce) {
        return null;
    }
    
    private final byte[] decryptChunk(byte[] encrypted, byte[] nonce) {
        return null;
    }
    
    private final java.lang.String sanitizeFileName(java.lang.String name) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/mrp/sml/data/remote/sockets/SocketTransferManager$Companion;", "", "()V", "TYPE_CHUNK", "", "TYPE_FILE_DONE", "TYPE_FILE_START", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}