package com.mrp.sml.core.utils

import android.os.Build

object DeviceUtils {

    fun getDeviceName(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }

    fun getDeviceId(): String {
        return "${Build.MANUFACTURER}_${Build.MODEL}_${Build.SERIAL}"
    }

    fun getAndroidVersion(): String {
        return "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    }
}
