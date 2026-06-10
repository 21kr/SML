package com.mrp.sml.core.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object QrCodeUtils {

    @Serializable
    data class QrPayload(
        val deviceName: String,
        val ipAddress: String,
        val port: Int = 8988,
        val sessionToken: String,
        val role: String,
        val fileCount: Int = 0,
        val totalSize: Long = 0L,
        val version: Int = 1,
        val ssid: String = "",
        val password: String = ""
    )

    private val json = Json { ignoreUnknownKeys = true }

    fun generateQrCode(
        data: String,
        width: Int = 512,
        height: Int = 512
    ): Bitmap? {
        return try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(
                        x, y,
                        if (bitMatrix[x, y]) android.graphics.Color.BLACK
                        else android.graphics.Color.WHITE
                    )
                }
            }
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    fun buildQrPayload(
        deviceName: String,
        ipAddress: String,
        port: Int = 8988,
        sessionToken: String,
        role: String,
        fileCount: Int = 0,
        totalSize: Long = 0L,
        ssid: String = "",
        password: String = ""
    ): String {
        val payload = QrPayload(
            deviceName = deviceName,
            ipAddress = ipAddress,
            port = port,
            sessionToken = sessionToken,
            role = role,
            fileCount = fileCount,
            totalSize = totalSize,
            ssid = ssid,
            password = password
        )
        return "sml://connect?${json.encodeToString(payload)}"
    }

    fun parseQrPayload(qrString: String): QrPayload? {
        return try {
            val uri = android.net.Uri.parse(qrString)
            val query = uri.getQueryParameter("data") ?: uri.encodedQuery ?: ""
            if (query.isEmpty()) return null
            json.decodeFromString<QrPayload>(query)
        } catch (e: Exception) {
            null
        }
    }

    fun buildLegacyPayload(deviceName: String, ipAddress: String, port: Int = 8988): String {
        return "sml://connect?device=$deviceName&ip=$ipAddress&port=$port"
    }
}
