package com.mrp.sml.ui.screens.receive

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrp.sml.core.models.ConnectionState
import com.mrp.sml.core.models.Device
import com.mrp.sml.ui.components.DeviceCard
import com.mrp.sml.ui.components.SMLTopBar
import com.mrp.sml.ui.theme.Primary
import com.mrp.sml.ui.viewmodel.ReceiveUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveScreen(
    uiState: ReceiveUiState = ReceiveUiState(),
    onStartListening: () -> Unit = {},
    onStartHotspot: () -> Unit = {},
    onStopListening: () -> Unit = {},
    onDeviceClick: (Device) -> Unit = {},
    onDeviceConnected: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onStartListening()
    }

    Scaffold(
        topBar = {
            SMLTopBar(
                title = "Receive Files",
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
                .verticalScroll(rememberScrollState())
        ) {
            uiState.qrBitmap?.let { bitmap ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Show this QR to sender",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (uiState.usingHotspot) "Sender will join your hotspot" else "Ask the sender to scan your QR code",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Your QR code",
                            modifier = Modifier.size(220.dp)
                        )
                        if (uiState.usingHotspot) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Hotspot: ${uiState.hotspotSsid}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Password: ${uiState.hotspotPassword}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
            }

            ScanningState(
                connectionState = uiState.connectionState,
                discoveredDevices = uiState.discoveredDevices,
                onDeviceClick = onDeviceClick,
                onStop = onStopListening,
                onStartHotspot = onStartHotspot,
                isUsingHotspot = uiState.usingHotspot,
                errorMessage = uiState.errorMessage
            )
        }
    }
}

@Composable
private fun ScanningState(
    connectionState: ConnectionState,
    discoveredDevices: List<Device>,
    onDeviceClick: (Device) -> Unit,
    onStop: () -> Unit,
    onStartHotspot: () -> Unit,
    isUsingHotspot: Boolean,
    errorMessage: String?
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = when {
                isUsingHotspot -> "Hotspot active"
                connectionState == ConnectionState.DISCOVERING -> "Searching for senders..."
                connectionState == ConnectionState.CONNECTING -> "Connecting..."
                connectionState == ConnectionState.CONNECTED -> "Connected"
                connectionState == ConnectionState.PAIRED -> "Paired"
                connectionState == ConnectionState.FAILED -> "Connection failed"
                else -> "Listening"
            },
            style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (!isUsingHotspot && connectionState == ConnectionState.DISCOVERING) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    if (isUsingHotspot) {
        Card(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WifiTethering, contentDescription = "Hotspot", tint = Primary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Waiting for sender to connect via hotspot...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onTertiaryContainer)
            }
        }
    } else {
        Card(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Wifi, contentDescription = "Listening for senders", tint = Primary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Listening for nearby senders...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
    OutlinedButton(onClick = onStop, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Icon(Icons.Default.Close, contentDescription = "Stop listening"); Spacer(modifier = Modifier.width(6.dp)); Text("Stop Listening")
    }
    Spacer(modifier = Modifier.height(16.dp))

    errorMessage?.let {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Text(
                text = it,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (discoveredDevices.isEmpty()) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("No senders found yet", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Make sure the sender is nearby and scanning", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            if (!isUsingHotspot) {
                Button(
                    onClick = onStartHotspot,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Icon(Icons.Default.WifiTethering, contentDescription = "Hotspot")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Use Hotspot Instead")
                }
            }
        }
    } else {
        Text("Nearby Senders (${discoveredDevices.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        discoveredDevices.forEach { device ->
            DeviceCard(device = device, onClick = { onDeviceClick(device) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
