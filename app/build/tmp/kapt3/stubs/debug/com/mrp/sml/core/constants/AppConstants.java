package com.mrp.sml.core.constants;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/mrp/sml/core/constants/AppConstants;", "", "()V", "APP_NAME", "", "NOTIFICATION_CHANNEL_DISCOVERY", "NOTIFICATION_CHANNEL_TRANSFER", "NOTIFICATION_ID_DISCOVERY", "", "NOTIFICATION_ID_TRANSFER", "PACKAGE_NAME", "PREF_FILE_NAME", "REQUEST_CODE_PERMISSIONS", "app_debug"})
public final class AppConstants {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_NAME = "SML File Share";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PACKAGE_NAME = "com.mrp.sml";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION_CHANNEL_TRANSFER = "transfer_progress";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION_CHANNEL_DISCOVERY = "device_discovery";
    public static final int NOTIFICATION_ID_TRANSFER = 1001;
    public static final int NOTIFICATION_ID_DISCOVERY = 1002;
    public static final int REQUEST_CODE_PERMISSIONS = 100;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREF_FILE_NAME = "sml_preferences";
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.core.constants.AppConstants INSTANCE = null;
    
    private AppConstants() {
        super();
    }
}