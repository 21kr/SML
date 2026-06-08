package com.mrp.sml.data.mapper;

import com.mrp.sml.core.models.Device;
import com.mrp.sml.core.models.DeviceType;
import com.mrp.sml.data.local.db.entities.DeviceEntity;
import com.mrp.sml.domain.model.DeviceModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\n\u00a8\u0006\r"}, d2 = {"Lcom/mrp/sml/data/mapper/DeviceMapper;", "", "()V", "coreToDomain", "Lcom/mrp/sml/domain/model/DeviceModel;", "device", "Lcom/mrp/sml/core/models/Device;", "domainToCore", "model", "domainToEntity", "Lcom/mrp/sml/data/local/db/entities/DeviceEntity;", "entityToDomain", "entity", "app_debug"})
public final class DeviceMapper {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.data.mapper.DeviceMapper INSTANCE = null;
    
    private DeviceMapper() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.DeviceModel coreToDomain(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.Device device) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.models.Device domainToCore(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.DeviceModel model) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.domain.model.DeviceModel entityToDomain(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.local.db.entities.DeviceEntity entity) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.data.local.db.entities.DeviceEntity domainToEntity(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.model.DeviceModel model) {
        return null;
    }
}