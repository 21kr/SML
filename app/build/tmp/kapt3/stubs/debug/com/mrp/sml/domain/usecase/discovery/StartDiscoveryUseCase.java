package com.mrp.sml.domain.usecase.discovery;

import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.domain.repository.ConnectionRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0086B\u00a2\u0006\u0002\u0010\u0007J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/mrp/sml/domain/usecase/discovery/StartDiscoveryUseCase;", "", "connectionRepository", "Lcom/mrp/sml/domain/repository/ConnectionRepository;", "(Lcom/mrp/sml/domain/repository/ConnectionRepository;)V", "invoke", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeConnectionState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/mrp/sml/core/models/ConnectionState;", "observeDiscoveredDevices", "Lcom/mrp/sml/core/models/Device;", "app_debug"})
public final class StartDiscoveryUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.ConnectionRepository connectionRepository = null;
    
    @javax.inject.Inject()
    public StartDiscoveryUseCase(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.ConnectionRepository connectionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.mrp.sml.core.models.ConnectionState> observeConnectionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.mrp.sml.core.models.Device> observeDiscoveredDevices() {
        return null;
    }
}