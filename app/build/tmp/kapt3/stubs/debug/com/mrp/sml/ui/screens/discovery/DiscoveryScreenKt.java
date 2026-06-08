package com.mrp.sml.ui.screens.discovery;

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
import com.mrp.sml.ui.viewmodel.PairingMode;
import com.mrp.sml.ui.viewmodel.PairingUiState;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u0094\u0001\u0010\u0007\u001a\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\t2\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a$\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00102\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u0018\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\rH\u0003\u001a\b\u0010\u0019\u001a\u00020\u0001H\u0003\u001a\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0003\u001a&\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u00a8\u0006\""}, d2 = {"DeviceList", "", "devices", "", "Lcom/mrp/sml/core/models/Device;", "onDeviceClick", "Lkotlin/Function1;", "DiscoveryScreen", "uiState", "Lcom/mrp/sml/ui/viewmodel/PairingUiState;", "onDiscoverClick", "Lkotlin/Function0;", "onDeviceConnected", "", "onShowQrCode", "onPairingModeChange", "Lcom/mrp/sml/ui/viewmodel/PairingMode;", "onCancel", "onBack", "PairingModeSelector", "currentMode", "onModeChange", "RoleAndFileSummary", "mode", "selectedFileSummary", "SearchingState", "StatusSection", "connectionState", "Lcom/mrp/sml/core/models/ConnectionState;", "isDiscovering", "", "WaitingForAcceptState", "deviceName", "fileSummary", "app_debug"})
public final class DiscoveryScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DiscoveryScreen(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.PairingUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.mrp.sml.core.models.Device, kotlin.Unit> onDeviceClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDiscoverClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeviceConnected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShowQrCode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.mrp.sml.ui.viewmodel.PairingMode, kotlin.Unit> onPairingModeChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RoleAndFileSummary(java.lang.String mode, java.lang.String selectedFileSummary) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PairingModeSelector(com.mrp.sml.ui.viewmodel.PairingMode currentMode, kotlin.jvm.functions.Function1<? super com.mrp.sml.ui.viewmodel.PairingMode, kotlin.Unit> onModeChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusSection(com.mrp.sml.core.models.ConnectionState connectionState, boolean isDiscovering) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SearchingState() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DeviceList(java.util.List<com.mrp.sml.core.models.Device> devices, kotlin.jvm.functions.Function1<? super com.mrp.sml.core.models.Device, kotlin.Unit> onDeviceClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WaitingForAcceptState(java.lang.String deviceName, java.lang.String fileSummary, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
}