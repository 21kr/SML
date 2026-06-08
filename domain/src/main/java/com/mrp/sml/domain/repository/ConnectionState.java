package com.mrp.sml.domain.repository;

public enum ConnectionState {
    IDLE,
    DISCOVERING,
    CONNECTED,
    PAIRING,
    PAIRED,
    FAILED,
    DISCONNECTED
}
