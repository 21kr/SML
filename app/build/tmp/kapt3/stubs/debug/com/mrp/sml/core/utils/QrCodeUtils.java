package com.mrp.sml.core.utils;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import kotlinx.serialization.Serializable;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nJX\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u0006J$\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00062\b\b\u0002\u0010\u0016\u001a\u00020\n2\b\b\u0002\u0010\u0017\u001a\u00020\nJ\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/mrp/sml/core/utils/QrCodeUtils;", "", "()V", "json", "Lkotlinx/serialization/json/Json;", "buildLegacyPayload", "", "deviceName", "ipAddress", "port", "", "buildQrPayload", "sessionToken", "role", "fileCount", "totalSize", "", "ssid", "password", "generateQrCode", "Landroid/graphics/Bitmap;", "data", "width", "height", "parseQrPayload", "Lcom/mrp/sml/core/utils/QrCodeUtils$QrPayload;", "qrString", "QrPayload", "app_debug"})
public final class QrCodeUtils {
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.serialization.json.Json json = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.mrp.sml.core.utils.QrCodeUtils INSTANCE = null;
    
    private QrCodeUtils() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap generateQrCode(@org.jetbrains.annotations.NotNull()
    java.lang.String data, int width, int height) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String buildQrPayload(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    java.lang.String ipAddress, int port, @org.jetbrains.annotations.NotNull()
    java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
    java.lang.String role, int fileCount, long totalSize, @org.jetbrains.annotations.NotNull()
    java.lang.String ssid, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.mrp.sml.core.utils.QrCodeUtils.QrPayload parseQrPayload(@org.jetbrains.annotations.NotNull()
    java.lang.String qrString) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String buildLegacyPayload(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    java.lang.String ipAddress, int port) {
        return null;
    }
    
    @kotlinx.serialization.Serializable()
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 :2\u00020\u0001:\u00029:Bu\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012Ba\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0013J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\fH\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003Jm\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010/\u001a\u00020\u0003H\u00d6\u0001J\t\u00100\u001a\u00020\u0005H\u00d6\u0001J&\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00002\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u00c1\u0001\u00a2\u0006\u0002\b8R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017\u00a8\u0006;"}, d2 = {"Lcom/mrp/sml/core/utils/QrCodeUtils$QrPayload;", "", "seen1", "", "deviceName", "", "ipAddress", "port", "sessionToken", "role", "fileCount", "totalSize", "", "version", "ssid", "password", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;IJILjava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;IJILjava/lang/String;Ljava/lang/String;)V", "getDeviceName", "()Ljava/lang/String;", "getFileCount", "()I", "getIpAddress", "getPassword", "getPort", "getRole", "getSessionToken", "getSsid", "getTotalSize", "()J", "getVersion", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$app_debug", "$serializer", "Companion", "app_debug"})
    public static final class QrPayload {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String deviceName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String ipAddress = null;
        private final int port = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String sessionToken = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String role = null;
        private final int fileCount = 0;
        private final long totalSize = 0L;
        private final int version = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String ssid = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String password = null;
        @org.jetbrains.annotations.NotNull()
        public static final com.mrp.sml.core.utils.QrCodeUtils.QrPayload.Companion Companion = null;
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component10() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final int component3() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        public final int component6() {
            return 0;
        }
        
        public final long component7() {
            return 0L;
        }
        
        public final int component8() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mrp.sml.core.utils.QrCodeUtils.QrPayload copy(@org.jetbrains.annotations.NotNull()
        java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
        java.lang.String ipAddress, int port, @org.jetbrains.annotations.NotNull()
        java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
        java.lang.String role, int fileCount, long totalSize, int version, @org.jetbrains.annotations.NotNull()
        java.lang.String ssid, @org.jetbrains.annotations.NotNull()
        java.lang.String password) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
        
        @kotlin.jvm.JvmStatic()
        public static final void write$Self$app_debug(@org.jetbrains.annotations.NotNull()
        com.mrp.sml.core.utils.QrCodeUtils.QrPayload self, @org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
        kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
        }
        
        public QrPayload(@org.jetbrains.annotations.NotNull()
        java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
        java.lang.String ipAddress, int port, @org.jetbrains.annotations.NotNull()
        java.lang.String sessionToken, @org.jetbrains.annotations.NotNull()
        java.lang.String role, int fileCount, long totalSize, int version, @org.jetbrains.annotations.NotNull()
        java.lang.String ssid, @org.jetbrains.annotations.NotNull()
        java.lang.String password) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDeviceName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getIpAddress() {
            return null;
        }
        
        public final int getPort() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSessionToken() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getRole() {
            return null;
        }
        
        public final int getFileCount() {
            return 0;
        }
        
        public final long getTotalSize() {
            return 0L;
        }
        
        public final int getVersion() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSsid() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPassword() {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/mrp/sml/core/utils/QrCodeUtils.QrPayload.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/mrp/sml/core/utils/QrCodeUtils$QrPayload;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_debug"})
        @java.lang.Deprecated()
        public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.mrp.sml.core.utils.QrCodeUtils.QrPayload> {
            @org.jetbrains.annotations.NotNull()
            public static final com.mrp.sml.core.utils.QrCodeUtils.QrPayload.$serializer INSTANCE = null;
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public kotlinx.serialization.KSerializer<?>[] childSerializers() {
                return null;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public com.mrp.sml.core.utils.QrCodeUtils.QrPayload deserialize(@org.jetbrains.annotations.NotNull()
            kotlinx.serialization.encoding.Decoder decoder) {
                return null;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
                return null;
            }
            
            @java.lang.Override()
            public void serialize(@org.jetbrains.annotations.NotNull()
            kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull()
            com.mrp.sml.core.utils.QrCodeUtils.QrPayload value) {
            }
            
            private $serializer() {
                super();
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/mrp/sml/core/utils/QrCodeUtils$QrPayload$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/mrp/sml/core/utils/QrCodeUtils$QrPayload;", "app_debug"})
        public static final class Companion {
            
            @org.jetbrains.annotations.NotNull()
            public final kotlinx.serialization.KSerializer<com.mrp.sml.core.utils.QrCodeUtils.QrPayload> serializer() {
                return null;
            }
            
            private Companion() {
                super();
            }
        }
    }
}