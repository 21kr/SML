package com.mrp.sml.ui.navigation;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import androidx.core.content.PermissionChecker;
import dagger.hilt.android.EntryPointAccessors;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;
import com.mrp.sml.core.constants.NetworkConstants;
import com.mrp.sml.core.utils.QrCodeUtils;
import com.mrp.sml.data.remote.hotspot.HotspotManagerEntryPoint;
import com.mrp.sml.ui.viewmodel.DiscoveryViewModel;
import com.mrp.sml.ui.viewmodel.HistoryViewModel;
import com.mrp.sml.ui.viewmodel.HomeViewModel;
import com.mrp.sml.ui.viewmodel.ReceiveViewModel;
import com.mrp.sml.ui.viewmodel.SendViewModel;
import com.mrp.sml.ui.viewmodel.SettingsViewModel;
import com.mrp.sml.ui.viewmodel.TransferDetailViewModel;
import com.mrp.sml.ui.viewmodel.TransferViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007\u00a8\u0006\u0004"}, d2 = {"NavGraph", "", "navController", "Landroidx/navigation/NavHostController;", "app_debug"})
public final class NavGraphKt {
    
    @androidx.compose.runtime.Composable()
    public static final void NavGraph(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
    }
}