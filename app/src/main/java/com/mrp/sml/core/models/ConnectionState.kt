package com.mrp.sml.core.models

enum class ConnectionState {
    IDLE,
    DISCOVERING,
    CONNECTING,
    CONNECTED,
    PAIRING,
    PAIRED,
    DISCONNECTING,
    DISCONNECTED,
    FAILED
}
