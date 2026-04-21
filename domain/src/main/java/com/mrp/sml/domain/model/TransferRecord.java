package com.mrp.sml.domain.model;

public class TransferRecord {
    private final long id;
    private final String fileName;
    private final long fileSizeBytes;
    private final String mimeType;
    private final TransferDirection direction;
    private final TransferStatus status;
    private final long timestampEpochMillis;

    public TransferRecord(long id, String fileName, long fileSizeBytes, String mimeType,
                          TransferDirection direction, TransferStatus status, long timestampEpochMillis) {
        this.id = id;
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        this.mimeType = mimeType;
        this.direction = direction;
        this.status = status;
        this.timestampEpochMillis = timestampEpochMillis;
    }

    public long getId() { return id; }
    public String getFileName() { return fileName; }
    public long getFileSizeBytes() { return fileSizeBytes; }
    public String getMimeType() { return mimeType; }
    public TransferDirection getDirection() { return direction; }
    public TransferStatus getStatus() { return status; }
    public long getTimestampEpochMillis() { return timestampEpochMillis; }
}
