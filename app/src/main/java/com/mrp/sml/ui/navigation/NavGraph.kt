package com.mrp.sml.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrp.sml.ui.screens.home.HomeScreen
import com.mrp.sml.ui.screens.send.SendScreen
import com.mrp.sml.ui.screens.receive.ReceiveScreen
import com.mrp.sml.ui.screens.discovery.DiscoveryScreen
import com.mrp.sml.ui.screens.transfer.TransferScreen
import com.mrp.sml.ui.screens.history.HistoryScreen
import com.mrp.sml.ui.screens.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onSendClick = {
                    navController.navigate(Screen.Send.route)
                },
                onReceiveClick = {
                    navController.navigate(Screen.Receive.route)
                },
                onHistoryClick = {
                    navController.navigate(Screen.History.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Send.route) {
            SendScreen(
                onFilesSelected = { filePaths ->
                    navController.navigate(Screen.Discovery.route + "?filePaths=${java.net.URLEncoder.encode(filePaths.joinToString(","), "UTF-8")}&mode=send")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Receive.route) {
            ReceiveScreen(
                onBack = { navController.popBackStack() },
                onDeviceConnected = { sessionId ->
                    navController.navigate(Screen.Transfer.createRoute(sessionId))
                }
            )
        }

        composable(Screen.Discovery.route) {
            DiscoveryScreen(
                onDeviceConnected = { sessionId ->
                    navController.navigate(Screen.Transfer.createRoute(sessionId)) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Transfer.route) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            TransferScreen(
                sessionId = sessionId,
                onBack = {
                    navController.popBackStack(Screen.Home.route, false)
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
