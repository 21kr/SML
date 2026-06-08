package com.mrp.sml.ui.screens.transfer;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.mrp.sml.core.models.TransferStatus;
import com.mrp.sml.core.utils.FileUtils;
import com.mrp.sml.core.utils.TransferUtils;
import com.mrp.sml.ui.viewmodel.TransferUiState;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001at\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001aB\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001aL\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001aD\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001a\b\u0010\"\u001a\u00020\u0001H\u0003\u001a\u001e\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001a\u00a2\u0001\u0010%\u001a\u00020\u00012\b\b\u0002\u0010&\u001a\u00020\'2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001a\b\u0010)\u001a\u00020\u0001H\u0003\u00a8\u0006*"}, d2 = {"ActiveTransferState", "", "currentFileName", "", "currentFileIndex", "", "totalFiles", "transferredBytes", "", "totalBytes", "progressPercent", "", "speed", "", "eta", "animatedProgress", "canPause", "", "onPause", "Lkotlin/Function0;", "onCancel", "CompletedState", "onViewFiles", "onSendMore", "onBackToHome", "FailedState", "status", "Lcom/mrp/sml/core/models/TransferStatus;", "errorMessage", "onRetry", "onViewDetails", "PausedState", "fileName", "onResume", "PreparingState", "ReconnectingState", "attempt", "TransferScreen", "uiState", "Lcom/mrp/sml/ui/viewmodel/TransferUiState;", "onBack", "VerifyingState", "app_debug"})
public final class TransferScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TransferScreen(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.TransferUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPause, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onResume, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewDetails, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSendMore, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewFiles) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CompletedState(int totalFiles, long totalBytes, kotlin.jvm.functions.Function0<kotlin.Unit> onViewFiles, kotlin.jvm.functions.Function0<kotlin.Unit> onSendMore, kotlin.jvm.functions.Function0<kotlin.Unit> onBackToHome) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FailedState(com.mrp.sml.core.models.TransferStatus status, java.lang.String errorMessage, long transferredBytes, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, kotlin.jvm.functions.Function0<kotlin.Unit> onBackToHome, kotlin.jvm.functions.Function0<kotlin.Unit> onViewDetails) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PausedState(long transferredBytes, long totalBytes, float progressPercent, java.lang.String fileName, kotlin.jvm.functions.Function0<kotlin.Unit> onResume, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VerifyingState() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReconnectingState(int attempt, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PreparingState() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ActiveTransferState(java.lang.String currentFileName, int currentFileIndex, int totalFiles, long transferredBytes, long totalBytes, float progressPercent, double speed, long eta, float animatedProgress, boolean canPause, kotlin.jvm.functions.Function0<kotlin.Unit> onPause, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
}