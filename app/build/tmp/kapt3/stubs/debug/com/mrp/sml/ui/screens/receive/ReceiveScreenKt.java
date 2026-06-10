package com.mrp.sml.ui.screens.receive;

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
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.ui.viewmodel.ReceiveUiState;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a~\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a`\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u00122\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\fH\u0003\u00a8\u0006\u0017"}, d2 = {"ReceiveScreen", "", "uiState", "Lcom/mrp/sml/ui/viewmodel/ReceiveUiState;", "onStartListening", "Lkotlin/Function0;", "onStartHotspot", "onStopListening", "onDeviceClick", "Lkotlin/Function1;", "Lcom/mrp/sml/core/models/Device;", "onDeviceConnected", "", "onBack", "ScanningState", "connectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "discoveredDevices", "", "onStop", "isUsingHotspot", "", "errorMessage", "app_debug"})
public final class ReceiveScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ReceiveScreen(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.ReceiveUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartListening, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartHotspot, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStopListening, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.mrp.sml.core.models.Device, kotlin.Unit> onDeviceClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeviceConnected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ScanningState(com.mrp.sml.core.models.ConnectionState connectionState, java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, kotlin.jvm.functions.Function1<? super com.mrp.sml.core.models.Device, kotlin.Unit> onDeviceClick, kotlin.jvm.functions.Function0<kotlin.Unit> onStop, kotlin.jvm.functions.Function0<kotlin.Unit> onStartHotspot, boolean isUsingHotspot, java.lang.String errorMessage) {
    }
}