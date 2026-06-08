package com.mrp.sml.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.TransferProgress;
import com.mrp.sml.core.models.TransferStatus;
import com.mrp.sml.data.remote.sockets.FileReceiver;
import com.mrp.sml.data.remote.sockets.FileSender;
import com.mrp.sml.domain.model.TransferModel;
import com.mrp.sml.domain.repository.TransferRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020!J\b\u0010#\u001a\u00020!H\u0014J\u0016\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000bJ\u0006\u0010\'\u001a\u00020!J$\u0010(\u001a\u00020!2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2\u0006\u0010*\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0019\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017\u00a8\u0006+"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/TransferViewModel;", "Landroidx/lifecycle/ViewModel;", "transferRepository", "Lcom/mrp/sml/domain/repository/TransferRepository;", "fileSender", "Lcom/mrp/sml/data/remote/sockets/FileSender;", "fileReceiver", "Lcom/mrp/sml/data/remote/sockets/FileReceiver;", "(Lcom/mrp/sml/domain/repository/TransferRepository;Lcom/mrp/sml/data/remote/sockets/FileSender;Lcom/mrp/sml/data/remote/sockets/FileReceiver;)V", "_currentFileName", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_errorMessage", "_transferHistory", "", "Lcom/mrp/sml/domain/model/TransferModel;", "_transferProgress", "Lcom/mrp/sml/core/models/TransferProgress;", "_transferStatus", "Lcom/mrp/sml/core/models/TransferStatus;", "currentFileName", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentFileName", "()Lkotlinx/coroutines/flow/StateFlow;", "errorMessage", "getErrorMessage", "transferHistory", "getTransferHistory", "transferProgress", "getTransferProgress", "transferStatus", "getTransferStatus", "cancelTransfer", "", "clearHistory", "onCleared", "receiveFiles", "outputDirectoryPath", "sessionToken", "resumeTransfer", "sendFiles", "filePaths", "destinationAddress", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TransferViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.domain.repository.TransferRepository transferRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.FileSender fileSender = null;
    @org.jetbrains.annotations.NotNull()
    private final com.mrp.sml.data.remote.sockets.FileReceiver fileReceiver = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.core.models.TransferStatus> _transferStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.TransferStatus> transferStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.core.models.TransferProgress> _transferProgress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.TransferProgress> transferProgress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _currentFileName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentFileName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.mrp.sml.domain.model.TransferModel>> _transferHistory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.mrp.sml.domain.model.TransferModel>> transferHistory = null;
    
    @javax.inject.Inject()
    public TransferViewModel(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.domain.repository.TransferRepository transferRepository, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.FileSender fileSender, @org.jetbrains.annotations.NotNull()
    com.mrp.sml.data.remote.sockets.FileReceiver fileReceiver) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.TransferStatus> getTransferStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.core.models.TransferProgress> getTransferProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentFileName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getErrorMessage() {
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
    java.lang.String sessionToken) {
    }
    
    public final void cancelTransfer() {
    }
    
    public final void resumeTransfer() {
    }
    
    public final void clearHistory() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}