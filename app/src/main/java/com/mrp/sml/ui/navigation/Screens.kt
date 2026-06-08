package com.mrp.sml.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Send : Screen("send")
    data object Receive : Screen("receive")
    data object Discovery : Screen("discovery")
    data object Transfer : Screen("transfer/{sessionId}") {
        fun createRoute(sessionId: String) = "transfer/$sessionId"
    }
    data object History : Screen("history")
    data object Settings : Screen("settings")
}
