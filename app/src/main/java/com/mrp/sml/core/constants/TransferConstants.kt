package com.mrp.sml.core.constants

object TransferConstants {
    const val TRANSFER_PORT = 8988
    const val HANDSHAKE_PORT = 8989
    const val CHUNK_SIZE = 4_194_304
    const val BUFFER_SIZE = 256 * 1024
    const val MAX_RETRY_ATTEMPTS = 3
    const val RETRY_DELAY_MS = 350L
    const val SOCKET_TIMEOUT_MS = 30_000
    const val AES_GCM_NONCE_LENGTH = 12
    const val AES_GCM_TAG_LENGTH = 128
}
