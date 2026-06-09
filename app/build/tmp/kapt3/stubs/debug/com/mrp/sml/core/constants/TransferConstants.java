package com.mrp.sml.core.constants;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/mrp/sml/core/constants/TransferConstants;", "", "()V", "AES_GCM_NONCE_LENGTH", "", "AES_GCM_TAG_LENGTH", "BUFFER_SIZE", "CHUNK_SIZE", "HANDSHAKE_PORT", "MAX_RETRY_ATTEMPTS", "RETRY_DELAY_MS", "", "SOCKET_TIMEOUT_MS", "TRANSFER_PORT", "app_debug"})
public final class TransferConstants {
    public static final int TRANSFER_PORT = 8988;
    public static final int HANDSHAKE_PORT = 8989;
    public static final int CHUNK_SIZE = 4194304;
    public static final int BUFFER_SIZE = 262144;
    public static final int MAX_RETRY_ATTEMPTS = 3;
    public static final long RETRY_DELAY_MS = 350L;
    public static final int SOCKET_TIMEOUT_MS = 30000;
    public static final int AES_GCM_NONCE_LENGTH = 12;
    public static final int AES_GCM_TAG_LENGTH = 128;
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.core.constants.TransferConstants INSTANCE = null;
    
    private TransferConstants() {
        super();
    }
}