package com.mrp.sml.ui.screens.qr

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Copy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrp.sml.core.utils.QrCodeUtils
import com.mrp.sml.ui.components.SMLTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrDisplayScreen(
    qrPayload: String,
    onBack: () -> Unit = {},
    onCopyPayload: (String) -> Unit = {}
) {
    val qrBitmap = remember(qrPayload) {
        QrCodeUtils.generateQrCode(qrPayload)
    }
    
    val parsedPayload = remember(qrPayload) {
        QrCodeUtils.parseQrPayload(qrPayload)
    }

    Scaffold(
        topBar = {
            SMLTopBar(
                title = "Your QR Code",
                showBackButton = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Scan to Connect",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ask the receiver to scan this code",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    qrBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(280.dp)
                        )
                    }
                    
                    parsedPayload?.let { payload ->
                        Spacer(modifier = Modifier.height(24.dp))
                        ConnectionInfoCard(payload = payload, onCopy = onCopyPayload)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Done")
                Spacer(modifier = Modifier.size(8.dp))
                Text("Done")
            }
        }
    }
}

@Composable
private fun ConnectionInfoCard(
    payload: QrCodeUtils.QrPayload,
    onCopy: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Connection Details",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                IconButton(onClick = { onCopy(payload.ipAddress) }) {
                    Icon(Icons.Default.Copy, contentDescription = "Copy IP", tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            InfoRow(label = "Device", value = payload.deviceName)
            InfoRow(label = "IP Address", value = payload.ipAddress, copyable = true, onCopy = { onCopy(payload.ipAddress) })
            InfoRow(label = "Port", value = payload.port.toString())
            InfoRow(label = "Session Token", value = payload.sessionToken.take(8) + "...", copyable = true, onCopy = { onCopy(payload.sessionToken) })
            InfoRow(label = "Role", value = payload.role.capitalize())
            if (payload.fileCount > 0) InfoRow(label = "Files", value = payload.fileCount.toString())
            if (payload.totalSize > 0) InfoRow(label = "Total Size", value = android.text.format.Formatter.formatFileSize(android.content.Context, payload.totalSize))
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    copyable: Boolean = false,
    onCopy: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
        Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            if (copyable && onCopy != null) {
                IconButton(onClick = onCopy) {
                    Icon(Icons.Default.Copy, contentDescription = "Copy $label", tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
