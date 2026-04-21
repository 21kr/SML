package com.mrp.sml.domain.repository;

public enum TransferExecutionStatus {
    IDLE,
    SENDING,
    RECEIVING,
    RETRYING,
    COMPLETED,
    FAILED
}
