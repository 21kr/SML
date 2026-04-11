package com.mrp.sml.domain.repository;

public class TransferStatusUpdate {
    private final TransferExecutionStatus status;
    private final String userMessage;

    public TransferStatusUpdate(TransferExecutionStatus status, String userMessage) {
        this.status = status;
        this.userMessage = userMessage;
    }

    public TransferExecutionStatus getStatus() { return status; }
    public String getUserMessage() { return userMessage; }
}
