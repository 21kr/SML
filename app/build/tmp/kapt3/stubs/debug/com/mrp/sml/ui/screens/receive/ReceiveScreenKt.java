package com.mrp.sml.ui.screens.receive;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.ui.viewmodel.ReceiveUiState;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u001a2\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a.\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\u008e\u0001\u0010\r\u001a\u00020\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00132\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00132\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a@\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\u001c2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u00a8\u0006\u001e"}, d2 = {"ChooseMethodState", "", "onScanQr", "Lkotlin/Function0;", "onDiscover", "onManualIp", "MethodCard", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "", "description", "onClick", "ReceiveScreen", "uiState", "Lcom/mrp/sml/ui/viewmodel/ReceiveUiState;", "onStartListening", "onStopListening", "onDeviceClick", "Lkotlin/Function1;", "Lcom/mrp/sml/core/models/Device;", "onDeviceConnected", "onConnectManualIp", "onBack", "ScanningState", "connectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "discoveredDevices", "", "onStop", "app_debug"})
public final class ReceiveScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ReceiveScreen(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.ReceiveUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartListening, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStopListening, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.mrp.sml.core.models.Device, kotlin.Unit> onDeviceClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeviceConnected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onScanQr, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConnectManualIp, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ChooseMethodState(kotlin.jvm.functions.Function0<kotlin.Unit> onScanQr, kotlin.jvm.functions.Function0<kotlin.Unit> onDiscover, kotlin.jvm.functions.Function0<kotlin.Unit> onManualIp) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MethodCard(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String description, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ScanningState(com.mrp.sml.core.models.ConnectionState connectionState, java.util.List<com.mrp.sml.core.models.Device> discoveredDevices, kotlin.jvm.functions.Function1<? super com.mrp.sml.core.models.Device, kotlin.Unit> onDeviceClick, kotlin.jvm.functions.Function0<kotlin.Unit> onStop) {
    }
}