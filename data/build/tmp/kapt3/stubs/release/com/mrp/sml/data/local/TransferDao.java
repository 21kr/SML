package com.mrp.sml.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'\u00a8\u0006\n"}, d2 = {"Lcom/mrp/sml/data/local/TransferDao;", "", "insert", "", "record", "Lcom/mrp/sml/data/local/TransferEntity;", "(Lcom/mrp/sml/data/local/TransferEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeTransferHistory", "Lkotlinx/coroutines/flow/Flow;", "", "data_release"})
@androidx.room.Dao()
public abstract interface TransferDao {
    
    @androidx.room.Query(value = "SELECT * FROM transfer_history ORDER BY timestampEpochMillis DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.mrp.sml.data.local.TransferEntity>> observeTransferHistory();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.TransferEntity record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}