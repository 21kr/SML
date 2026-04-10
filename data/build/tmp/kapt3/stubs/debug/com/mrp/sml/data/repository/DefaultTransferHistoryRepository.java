package com.mrp.sml.data.repository;

import com.mrp.sml.data.local.TransferDao;
import com.mrp.sml.data.local.TransferEntity;
import com.mrp.sml.domain.model.TransferDirection;
import com.mrp.sml.domain.model.TransferRecord;
import com.mrp.sml.domain.model.TransferStatus;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\u0016J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/mrp/sml/data/repository/DefaultTransferHistoryRepository;", "Lcom/mrp/sml/domain/repository/TransferHistoryRepository;", "transferDao", "Lcom/mrp/sml/data/local/TransferDao;", "(Lcom/mrp/sml/data/local/TransferDao;)V", "observeTransferHistory", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/mrp/sml/domain/model/TransferRecord;", "saveTransferRecord", "", "record", "(Lcom/mrp/sml/domain/model/TransferRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
public final class DefaultTransferHistoryRepository implements com.mrp.sml.domain.repository.TransferHistoryRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.local.TransferDao transferDao = null;
    
    @javax.inject.Inject()
    public DefaultTransferHistoryRepository(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.TransferDao transferDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.mrp.sml.domain.model.TransferRecord>> observeTransferHistory() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveTransferRecord(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}