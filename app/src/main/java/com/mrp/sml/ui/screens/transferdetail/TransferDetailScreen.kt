package com.mrp.sml.ui.screens.transferdetail

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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mrp.sml.core.utils.FileUtils
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.ui.components.SMLTopBar
import com.mrp.sml.ui.theme.Error
import com.mrp.sml.ui.theme.Primary
import com.mrp.sml.ui.theme.StatusCompleted
import com.mrp.sml.ui.theme.StatusFailed
import com.mrp.sml.ui.theme.StatusReceived
import com.mrp.sml.ui.viewmodel.TransferDetailUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferDetailScreen(
    uiState: TransferDetailUiState = TransferDetailUiState(),
    onRetry: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())

    Scaffold(
        topBar = {
            SMLTopBar(
                title = "Transfer Details",
                showBackButton = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            val transfer = uiState.transfer
            if (transfer == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(32.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Transfer not found",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            DetailRow(label = "File", value = transfer.fileName)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            DetailRow(label = "Size", value = FileUtils.formatFileSize(transfer.fileSize))
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            DetailRow(label = "Direction", value = if (transfer.direction == TransferModel.TransferDirection.SENT) "Sent" else "Received")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            DetailRow(label = "Status", value = transfer.status.name)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            DetailRow(label = "Started", value = dateFormat.format(Date(transfer.startedAt)))
                            transfer.completedAt?.let {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                DetailRow(label = "Completed", value = dateFormat.format(Date(it)))
                            }
                            transfer.peerDeviceName.ifBlank { null }?.let {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                DetailRow(label = "Device", value = it)
                            }
                            transfer.errorMessage?.let {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                DetailRow(label = "Error", value = it)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            DetailRow(label = "Session ID", value = transfer.sessionToken)
                        }
                    }

                    if (transfer.status == TransferModel.TransferStatus.FAILED) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onRetry,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Retry transfer")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Retry Transfer")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
