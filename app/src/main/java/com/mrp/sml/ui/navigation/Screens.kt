package com.mrp.sml.ui.navigation

import java.net.URLEncoder

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Permissions : Screen("permissions")
    data object Home : Screen("home")
    data object Send : Screen("send")
    data object Receive : Screen("receive")
    data object Discovery : Screen("discovery?mode={mode}&filePaths={filePaths}") {
        fun createRoute(mode: String, filePaths: List<String> = emptyList()): String {
            val encoded = filePaths.joinToString(",").let {
                if (it.isNotEmpty()) "&filePaths=${URLEncoder.encode(it, "UTF-8")}" else ""
            }
            return "discovery?mode=$mode$encoded"
        }
    }
    data object Transfer : Screen("transfer/{sessionId}") {
        fun createRoute(sessionId: String) = "transfer/$sessionId"
    }
    data object TransferDetail : Screen("transfer_detail/{transferId}") {
        fun createRoute(transferId: String) = "transfer_detail/$transferId"
    }
    data object History : Screen("history")
    data object Settings : Screen("settings")
    data object QrDisplay : Screen("qr_display/{payload}") {
        fun createRoute(payload: String) = "qr_display/${java.net.URLEncoder.encode(payload, "UTF-8")}"
    }
}
