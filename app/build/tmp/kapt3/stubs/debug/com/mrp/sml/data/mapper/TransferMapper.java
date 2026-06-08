package com.mrp.sml.data.mapper;

import com.mrp.sml.core.models.TransferDirection;
import com.mrp.sml.core.models.TransferFile;
import com.mrp.sml.core.models.TransferSession;
import com.mrp.sml.core.models.TransferStatus;
import com.mrp.sml.data.local.db.entities.TransferEntity;
import com.mrp.sml.domain.model.TransferModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004J\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/mrp/sml/data/mapper/TransferMapper;", "", "()V", "domainToEntity", "Lcom/mrp/sml/data/local/db/entities/TransferEntity;", "model", "Lcom/mrp/sml/domain/model/TransferModel;", "entityToDomain", "entity", "mapStatus", "error/NonExistentClass", "status", "Lcom/mrp/sml/core/models/TransferStatus;", "(Lcom/mrp/sml/core/models/TransferStatus;)Lerror/NonExistentClass;", "sessionToDomain", "session", "Lcom/mrp/sml/core/models/TransferSession;", "app_debug"})
public final class TransferMapper {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.data.mapper.TransferMapper INSTANCE = null;
    
    private TransferMapper() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel sessionToDomain(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.TransferSession session) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.TransferModel entityToDomain(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.entities.TransferEntity entity) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.db.entities.TransferEntity domainToEntity(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.TransferModel model) {
        return null;
    }
    
    private final com.mrp.sml.domain.model.TransferModel.TransferStatus mapStatus(com.mrp.sml.core.models.TransferStatus status) {
        return null;
    }
}