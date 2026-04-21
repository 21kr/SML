package com.mrp.sml.domain.repository;

public class TransferProgress {
    private final long transferredBytes;
    private final long totalBytes;
    private final double speedBytesPerSecond;
    private final float progressPercent;

    public TransferProgress(long transferredBytes, long totalBytes, double speedBytesPerSecond, float progressPercent) {
        this.transferredBytes = transferredBytes;
        this.totalBytes = totalBytes;
        this.speedBytesPerSecond = speedBytesPerSecond;
        this.progressPercent = progressPercent;
    }

    public long getTransferredBytes() { return transferredBytes; }
    public long getTotalBytes() { return totalBytes; }
    public double getSpeedBytesPerSecond() { return speedBytesPerSecond; }
    public float getProgressPercent() { return progressPercent; }
}
