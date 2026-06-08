package com.mrp.sml.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import com.mrp.sml.MainActivity;
import com.mrp.sml.R;
import com.mrp.sml.core.constants.AppConstants;
import com.mrp.sml.core.utils.NotificationUtils;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\rH\u0002J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u001d\u001a\u00020\rH\u0016J\b\u0010\u001e\u001a\u00020\rH\u0016J\"\u0010\u001f\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u0013H\u0016J\b\u0010\"\u001a\u00020\rH\u0002J\u0018\u0010#\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0018\u00010\nR\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/mrp/sml/services/TransferForegroundService;", "Landroid/app/Service;", "()V", "notificationUtils", "Lcom/mrp/sml/core/utils/NotificationUtils;", "getNotificationUtils", "()Lcom/mrp/sml/core/utils/NotificationUtils;", "setNotificationUtils", "(Lcom/mrp/sml/core/utils/NotificationUtils;)V", "wakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "acquireWakeLock", "", "createNotification", "Landroid/app/Notification;", "text", "", "progress", "", "isOngoing", "", "handleStartTransfer", "intent", "Landroid/content/Intent;", "handleStopTransfer", "handleUpdateProgress", "onBind", "Landroid/os/IBinder;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "releaseWakeLock", "updateNotification", "Companion", "app_debug"})
public final class TransferForegroundService extends android.app.Service {
    @javax.inject.Inject()
    public com.mrp.sml.core.utils.NotificationUtils notificationUtils;
    @org.jetbrains.annotations.Nullable()
    private android.os.PowerManager.WakeLock wakeLock;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START_TRANSFER = "com.mrp.sml.action.START_TRANSFER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_UPDATE_PROGRESS = "com.mrp.sml.action.UPDATE_PROGRESS";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP_TRANSFER = "com.mrp.sml.action.STOP_TRANSFER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_FILE_NAME = "extra_file_name";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_PROGRESS = "extra_progress";
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.services.TransferForegroundService.Companion Companion = null;
    
    public TransferForegroundService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mrp.sml.core.utils.NotificationUtils getNotificationUtils() {
        return null;
    }
    
    public final void setNotificationUtils(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.core.utils.NotificationUtils p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void handleStartTransfer(android.content.Intent intent) {
    }
    
    private final void handleUpdateProgress(android.content.Intent intent) {
    }
    
    private final void handleStopTransfer() {
    }
    
    private final android.app.Notification createNotification(java.lang.String text, int progress, boolean isOngoing) {
        return null;
    }
    
    private final void updateNotification(java.lang.String text, int progress) {
    }
    
    private final void acquireWakeLock() {
    }
    
    private final void releaseWakeLock() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/mrp/sml/services/TransferForegroundService$Companion;", "", "()V", "ACTION_START_TRANSFER", "", "ACTION_STOP_TRANSFER", "ACTION_UPDATE_PROGRESS", "EXTRA_FILE_NAME", "EXTRA_PROGRESS", "start", "", "context", "Landroid/content/Context;", "fileName", "stop", "updateProgress", "progress", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String fileName) {
        }
        
        public final void updateProgress(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String fileName, int progress) {
        }
        
        public final void stop(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}