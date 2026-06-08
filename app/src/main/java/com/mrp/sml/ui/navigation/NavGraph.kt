package com.mrp.sml.ui.navigation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.PermissionChecker
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mrp.sml.ui.screens.discovery.DiscoveryScreen
import com.mrp.sml.ui.screens.history.HistoryScreen
import com.mrp.sml.ui.screens.home.HomeScreen
import com.mrp.sml.ui.screens.permissions.PermissionScreen
import com.mrp.sml.ui.screens.receive.ReceiveScreen
import com.mrp.sml.ui.screens.send.SendScreen
import com.mrp.sml.ui.screens.settings.SettingsScreen
import com.mrp.sml.ui.screens.splash.SplashScreen
import com.mrp.sml.ui.screens.transfer.TransferScreen
import com.mrp.sml.ui.screens.transferdetail.TransferDetailScreen
import com.mrp.sml.ui.viewmodel.DiscoveryViewModel
import com.mrp.sml.ui.viewmodel.HistoryViewModel
import com.mrp.sml.ui.viewmodel.HomeViewModel
import com.mrp.sml.ui.viewmodel.ReceiveViewModel
import com.mrp.sml.ui.viewmodel.SendViewModel
import com.mrp.sml.ui.viewmodel.SettingsViewModel
import com.mrp.sml.ui.viewmodel.TransferDetailViewModel
import com.mrp.sml.ui.viewmodel.TransferViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    val permissionsGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        PermissionChecker.checkSelfPermission(context, Manifest.permission.NEARBY_WIFI_DEVICES) == PermissionChecker.PERMISSION_GRANTED &&
                        PermissionChecker.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PermissionChecker.PERMISSION_GRANTED
                    } else {
                        PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED
                    }
                    val dest = if (permissionsGranted) Screen.Home.route else Screen.Permissions.route
                    navController.navigate(dest) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Permissions.route) {
            PermissionScreen(
                onContinue = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Permissions.route) { inclusive = true }
                    }
                },
                onOpenSettings = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            )
        }

        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                onSendClick = { navController.navigate(Screen.Send.route) },
                onReceiveClick = { navController.navigate(Screen.Receive.route) },
                onHistoryClick = { navController.navigate(Screen.History.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Send.route) {
            val viewModel: SendViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SendScreen(
                uiState = uiState,
                onFilesPicked = { viewModel.addFiles(it) },
                onRemoveFile = { viewModel.removeFile(it) },
                onPickFiles = { },
                onContinue = {
                    navController.navigate(Screen.Discovery.createRoute("send", viewModel.getFilePathsForDiscovery()))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Receive.route) {
            val viewModel: ReceiveViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ReceiveScreen(
                uiState = uiState,
                onStartListening = { viewModel.startListening() },
                onStopListening = { viewModel.stopListening() },
                onDeviceClick = { device -> viewModel.connectToDevice(device.id) },
                onDeviceConnected = { sessionId ->
                    navController.navigate(Screen.Transfer.createRoute(sessionId))
                },
                onScanQr = { navController.navigate(Screen.Discovery.createRoute("receive")) },
                onConnectManualIp = { navController.navigate(Screen.Discovery.createRoute("receive")) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Discovery.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType; defaultValue = "send" },
                navArgument("filePaths") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val mode = backStackEntry.arguments?.getString("mode") ?: "send"
            val filePaths = backStackEntry.arguments?.getString("filePaths")?.split(",")
                ?.filter { it.isNotEmpty() } ?: emptyList()
            val viewModel: DiscoveryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.setMode(
                if (mode == "send") com.mrp.sml.ui.viewmodel.PairingRole.SENDER
                else com.mrp.sml.ui.viewmodel.PairingRole.RECEIVER
            )
            if (filePaths.isNotEmpty()) {
                viewModel.setSelectedFileSummary("${filePaths.size} file(s) selected")
            }

            DiscoveryScreen(
                uiState = uiState,
                onDeviceClick = { device -> viewModel.connectToDevice(device.id) },
                onDiscoverClick = { viewModel.startDiscovery() },
                onDeviceConnected = { sessionId ->
                    navController.navigate(Screen.Transfer.createRoute(sessionId)) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onShowQrCode = {
                    if (uiState.qrPayload == null) {
                        viewModel.generateQrCode("sml://share?mode=$mode")
                    } else {
                        viewModel.clearQrCode()
                    }
                },
                onPairingModeChange = { viewModel.setConnectionMethod(it) },
                onCancel = { viewModel.stopDiscovery(); navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Transfer.route,
            arguments = listOf(
                navArgument("sessionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            val viewModel: TransferViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            TransferScreen(
                uiState = uiState,
                onPause = { viewModel.pauseTransfer() },
                onResume = { viewModel.resumeTransfer() },
                onCancel = { viewModel.cancelTransfer() },
                onRetry = { viewModel.retryTransfer(sessionId) },
                onBack = { navController.popBackStack() },
                onBackToHome = { navController.popBackStack(Screen.Home.route, false) },
                onViewDetails = { navController.navigate(Screen.TransferDetail.createRoute(sessionId)) }
            )
        }

        composable(
            route = Screen.TransferDetail.route,
            arguments = listOf(
                navArgument("transferId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val transferId = backStackEntry.arguments?.getString("transferId") ?: ""
            val viewModel: TransferDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.loadTransfer(transferId)

            TransferDetailScreen(
                uiState = uiState,
                onRetry = { viewModel.retryTransfer(transferId) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.History.route) {
            val viewModel: HistoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HistoryScreen(
                uiState = uiState,
                onFilterChange = { viewModel.setFilter(it) },
                onClearHistory = { viewModel.clearHistory() },
                onRetryTransfer = { viewModel.retryTransfer(it) },
                onOpenFile = { transferId ->
                    navController.navigate(Screen.TransferDetail.createRoute(transferId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SettingsScreen(
                uiState = uiState,
                onDeviceNameChange = { viewModel.setDeviceName(it) },
                onSaveHistoryChange = { viewModel.setSaveHistory(it) },
                onOpenPermissions = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                },
                onOpenSaveLocation = { },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
