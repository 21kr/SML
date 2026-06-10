package com.mrp.sml.ui.screens.qr;

import android.graphics.Bitmap;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.Copy;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.mrp.sml.core.utils.QrCodeUtils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a4\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0003\u001a6\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00062\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0011"}, d2 = {"ConnectionInfoCard", "", "payload", "Lcom/mrp/sml/core/utils/QrCodeUtils$QrPayload;", "onCopy", "Lkotlin/Function1;", "", "InfoRow", "label", "value", "copyable", "", "Lkotlin/Function0;", "QrDisplayScreen", "qrPayload", "onBack", "onCopyPayload", "app_debug"})
public final class QrDisplayScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void QrDisplayScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String qrPayload, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCopyPayload) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ConnectionInfoCard(com.mrp.sml.core.utils.QrCodeUtils.QrPayload payload, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCopy) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InfoRow(java.lang.String label, java.lang.String value, boolean copyable, kotlin.jvm.functions.Function0<kotlin.Unit> onCopy) {
    }
}