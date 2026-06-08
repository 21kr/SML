package com.mrp.sml.core.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.mrp.sml.MainActivity;
import com.mrp.sml.R;
import com.mrp.sml.core.constants.AppConstants;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J(\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/mrp/sml/core/utils/NotificationUtils;", "", "()V", "createDiscoveryChannel", "", "context", "Landroid/content/Context;", "createTransferChannel", "createTransferNotification", "Landroidx/core/app/NotificationCompat$Builder;", "fileName", "", "progress", "", "isOngoing", "", "app_debug"})
public final class NotificationUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.core.utils.NotificationUtils INSTANCE = null;
    
    private NotificationUtils() {
        super();
    }
    
    public final void createTransferChannel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void createDiscoveryChannel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.core.app.NotificationCompat.Builder createTransferNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, int progress, boolean isOngoing) {
        return null;
    }
}