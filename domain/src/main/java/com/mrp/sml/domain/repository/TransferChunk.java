package com.mrp.sml.domain.repository;

public class TransferChunk {
    private final int chunkIndex;
    private final long offset;
    private final int size;
    private final String fileHash;
    private final boolean isLast;

    public TransferChunk(int chunkIndex, long offset, int size, String fileHash, boolean isLast) {
        this.chunkIndex = chunkIndex;
        this.offset = offset;
        this.size = size;
        this.fileHash = fileHash;
        this.isLast = isLast;
    }

    public int getChunkIndex() { return chunkIndex; }
    public long getOffset() { return offset; }
    public int getSize() { return size; }
    public String getFileHash() { return fileHash; }
    public boolean isLast() { return isLast; }
}
