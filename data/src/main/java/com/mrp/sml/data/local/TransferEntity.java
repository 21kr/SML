package com.mrp.sml.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transfer_history")
public class TransferEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String fileName;
    public long fileSizeBytes;
    public String mimeType;
    public String direction;
    public String status;
    public long timestampEpochMillis;

    public TransferEntity(String fileName, long fileSizeBytes, String mimeType, String direction,
                          String status, long timestampEpochMillis) {
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        this.mimeType = mimeType;
        this.direction = direction;
        this.status = status;
        this.timestampEpochMillis = timestampEpochMillis;
    }
}
