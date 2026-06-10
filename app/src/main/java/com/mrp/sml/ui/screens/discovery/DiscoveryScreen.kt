package com.mrp.sml.ui.screens.discovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.ui.components.DeviceCard
import com.mrp.sml.ui.components.SMLTopBar
import com.mrp.sml.ui.theme.Error
import com.mrp.sml.ui.theme.Primary
import com.mrp.sml.ui.viewmodel.PairingMode
import com.mrp.sml.ui.viewmodel.PairingUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    uiState: PairingUiState = PairingUiState(),
    onDeviceClick: (Device) -> Unit = {},
    onDiscoverClick: () -> Unit = {},
    onDeviceConnected: (String) -> Unit = {},
    onShowQrCode: () -> Unit = {},
    onScanQr: () -> Unit = {},
    onPairingModeChange: (PairingMode) -> Unit = {},
    onCancel: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            SMLTopBar(
                title = if (uiState.mode.name == "SENDER") "Send to Device" else "Receive from Device",
                showBackButton = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            RoleAndFileSummary(
                mode = uiState.mode.name,
                selectedFileSummary = uiState.selectedFileSummary
            )

            Spacer(modifier = Modifier.height(12.dp))

            PairingModeSelector(
                currentMode = uiState.connectionMethod,
                onModeChange = onPairingModeChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            StatusSection(
                connectionState = uiState.connectionState,
                isDiscovering = uiState.isDiscovering
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.mode.name == "SENDER") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDiscoverClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !uiState.isDiscovering
                    ) {
                        Icon(
                            if (uiState.isDiscovering) Icons.Default.Refresh else Icons.Default.Search,
                            contentDescription = if (uiState.isDiscovering) "Searching" else "Discover devices"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(if (uiState.isDiscovering) "Searching..." else "Discover Devices")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onShowQrCode,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.QrCode, contentDescription = "Show my QR code")
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("My QR")
                    }
                    OutlinedButton(
                        onClick = onScanQr,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan QR code")
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Scan QR")
                    }
                }
            } else {
                Button(
                    onClick = onDiscoverClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !uiState.isDiscovering
                ) {
                    Icon(
                        if (uiState.isDiscovering) Icons.Default.Refresh else Icons.Default.Search,
                        contentDescription = if (uiState.isDiscovering) "Searching" else "Discover devices"
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(if (uiState.isDiscovering) "Searching..." else "Discover Devices")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isConnected = uiState.connectionState == ConnectionState.CONNECTED || uiState.connectionState == ConnectionState.PAIRED
            val hasTriggered = remember { mutableStateOf(false) }
            LaunchedEffect(isConnected, hasTriggered.value) {
                if (isConnected && !hasTriggered.value) {
                    hasTriggered.value = true
                    onDeviceConnected(java.util.UUID.randomUUID().toString())
                }
            }
            if (isConnected && uiState.mode.name == "SENDER") {
                WaitingForAcceptState(
                    deviceName = uiState.discoveredDevices.firstOrNull()?.name ?: "",
                    fileSummary = uiState.selectedFileSummary,
                    onCancel = onCancel
                )
            } else if (uiState.discoveredDevices.isEmpty() && uiState.isDiscovering) {
                SearchingState()
            } else {
                DeviceList(
                    devices = uiState.discoveredDevices,
                    onDeviceClick = onDeviceClick
                )
            }
        }
    }
}

@Composable
private fun RoleAndFileSummary(
    mode: String,
    selectedFileSummary: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (mode == "SENDER")
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = if (mode == "SENDER") "You are the sender" else "You are the receiver",
                    tint = if (mode == "SENDER") MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (mode == "SENDER") "You are the Sender" else "You are the Receiver",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (mode == "SENDER") MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            if (mode == "SENDER" && selectedFileSummary.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedFileSummary,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (mode == "SENDER") MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    else MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun PairingModeSelector(
    currentMode: PairingMode,
    onModeChange: (PairingMode) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = currentMode == PairingMode.WIFI_DIRECT,
            onClick = { onModeChange(PairingMode.WIFI_DIRECT) },
            label = { Text("WiFi Direct") }
        )
        FilterChip(
            selected = currentMode == PairingMode.HOTSPOT_FALLBACK,
            onClick = { onModeChange(PairingMode.HOTSPOT_FALLBACK) },
            label = { Text("Hotspot") }
        )
    }
}

@Composable
private fun StatusSection(
    connectionState: ConnectionState,
    isDiscovering: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = when (connectionState) {
                ConnectionState.DISCOVERING -> "Searching for devices..."
                ConnectionState.CONNECTING -> "Connecting..."
                ConnectionState.CONNECTED -> "Connected"
                ConnectionState.PAIRED -> "Paired"
                ConnectionState.FAILED -> "Connection failed"
                else -> "Ready to discover"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (isDiscovering) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        }
    }
}

@Composable
private fun SearchingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Looking for nearby devices...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ensure WiFi is enabled on both devices",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DeviceList(
    devices: List<Device>,
    onDeviceClick: (Device) -> Unit
) {
    Text(
        text = "Nearby Devices (${devices.size})",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(devices) { device ->
            DeviceCard(device = device, onClick = { onDeviceClick(device) })
        }
    }
}

@Composable
private fun WaitingForAcceptState(
    deviceName: String,
    fileSummary: String,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.HourglassEmpty,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Waiting for Receiver",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (deviceName.isNotBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Connected to $deviceName", modifier = Modifier.size(18.dp), tint = Primary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Connected to $deviceName",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Waiting for receiver to accept",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (fileSummary.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = fileSummary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressIndicator(modifier = Modifier.size(32.dp), strokeWidth = 3.dp)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedButton(
            onClick = onCancel,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Error)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Cancel")
            Spacer(modifier = Modifier.width(6.dp))
            Text("Cancel")
        }
    }
}
