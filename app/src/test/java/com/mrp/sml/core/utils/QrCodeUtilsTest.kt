package com.mrp.sml.core.utils

import org.junit.Assert.assertTrue
import org.junit.Test

class QrCodeUtilsTest {

    @Test
    fun buildQrPayload_containsDeviceInfo() {
        val payload = QrCodeUtils.buildQrPayload("Pixel8", "192.168.49.1")
        assertTrue("Payload should contain device name", payload.contains("Pixel8"))
        assertTrue("Payload should contain IP address", payload.contains("192.168.49.1"))
        assertTrue("Payload should start with sml://", payload.startsWith("sml://"))
    }

    @Test
    fun buildQrPayload_withPort() {
        val payload = QrCodeUtils.buildQrPayload("Test", "10.0.0.1", 8988)
        assertTrue("Payload should contain port", payload.contains("port=8988"))
    }

    @Test
    fun buildQrPayload_emptyDeviceName() {
        val payload = QrCodeUtils.buildQrPayload("", "")
        assertTrue("Payload should handle empty values", payload.isNotEmpty())
    }
}
