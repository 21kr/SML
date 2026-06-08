package com.mrp.sml.ui.viewmodel;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.TransferFile;
import com.mrp.sml.core.utils.FileUtils;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\u0006\u0010\u0011\u001a\u00020\rJ\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000fJ\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/SendViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/mrp/sml/ui/viewmodel/SendUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addFiles", "", "uris", "", "Landroid/net/Uri;", "clearFiles", "getFilePathsForDiscovery", "", "removeFile", "file", "Lcom/mrp/sml/core/models/TransferFile;", "validateFiles", "files", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SendViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.mrp.sml.ui.viewmodel.SendUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.SendUiState> uiState = null;
    
    @javax.inject.Inject()
    public SendViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.mrp.sml.ui.viewmodel.SendUiState> getUiState() {
        return null;
    }
    
    public final void addFiles(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris) {
    }
    
    public final void removeFile(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.models.TransferFile file) {
    }
    
    public final void clearFiles() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getFilePathsForDiscovery() {
        return null;
    }
    
    private final java.util.List<java.lang.String> validateFiles(java.util.List<com.mrp.sml.core.models.TransferFile> files) {
        return null;
    }
}