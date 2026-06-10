package com.mrp.sml.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.TransferProgress;
import com.mrp.sml.core.models.TransferStatus;
import com.mrp.sml.data.remote.sockets.SocketTransferManager;
import com.mrp.sml.domain.model.TransferModel;
import com.mrp.sml.domain.repository.TransferRepository;
import com.mrp.sml.services.TransferForegroundService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\b\u0010\u001c\u001a\u00020\u0016H\u0014J\u0006\u0010\u001d\u001a\u00020\u0016J \u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\b\b\u0002\u0010\u001f\u001a\u00020\u001aJ\u0006\u0010 \u001a\u00020\u0016J\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u001aJ$\u0010#\u001a\u00020\u00162\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000b2\u0006\u0010%\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\u0010\u0010&\u001a\u00020\u00162\u0006\u0010\'\u001a\u00020(H\u0002R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006)"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/TransferViewModel;", "Landroidx/lifecycle/ViewModel;", "transferRepository", "Lcom/mrp/sml/domain/repository/TransferRepository;", "socketTransferManager", "Lcom/mrp/sml/data/remote/sockets/SocketTransferManager;", "context", "Landroid/content/Context;", "(Lcom/mrp/sml/domain/repository/TransferRepository;Lcom/mrp/sml/data/remote/sockets/SocketTransferManager;Landroid/content/Context;)V", "_transferHistory", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/mrp/sml/domain/model/TransferModel;", "_uiState", "Lcom/mrp/sml/ui/viewmodel/TransferUiState;", "transferHistory", "Lkotlinx/coroutines/flow/StateFlow;", "getTransferHistory", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "cancelTransfer", "", "clearHistory", "listenForFiles", "outputDirectoryPath", "", "sessionToken", "onCleared", "pauseTransfer", "receiveFiles", "senderIp", "resumeTransfer", "retryTransfer", "sessionId", "sendFiles", "filePaths", "destinationAddress", "updateFromProgress", "progress", "Lcom/mrp/sml/core/models/TransferProgress;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TransferViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.TransferRepository transferRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.SocketTransferManager socketTransferManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.viewmodel.TransferUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.TransferUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.mrp.sml.domain.model.TransferModel>> _transferHistory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.mrp.sml.domain.model.TransferModel>> transferHistory = null;
    
    @javax.inject.Inject()
    public TransferViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.TransferRepository transferRepository, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.SocketTransferManager socketTransferManager, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.TransferUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.mrp.sml.domain.model.TransferModel>> getTransferHistory() {
        return null;
    }
    
    public final void sendFiles(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> filePaths, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken) {
    }
    
    public final void receiveFiles(@org.jetbrains.annotations.NotNull()
    java.lang.String outputDirectoryPath, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
    java.lang.String senderIp) {
    }
    
    public final void listenForFiles(@org.jetbrains.annotations.NotNull()
    java.lang.String outputDirectoryPath, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken) {
    }
    
    private final void updateFromProgress(com.mrp.sml.core.models.TransferProgress progress) {
    }
    
    public final void pauseTransfer() {
    }
    
    public final void resumeTransfer() {
    }
    
    public final void cancelTransfer() {
    }
    
    public final void retryTransfer(@org.jetbrains.annotations.NotNull()
    java.lang.String sessionId) {
    }
    
    public final void clearHistory() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}