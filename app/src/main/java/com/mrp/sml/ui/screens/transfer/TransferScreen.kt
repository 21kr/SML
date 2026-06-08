package com.mrp.sml.ui.screens.transfer

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrp.sml.core.models.TransferStatus
import com.mrp.sml.core.utils.FileUtils
import com.mrp.sml.core.utils.TransferUtils
import com.mrp.sml.ui.components.SMLTopBar
import com.mrp.sml.ui.theme.Error
import com.mrp.sml.ui.theme.Primary
import com.mrp.sml.ui.theme.StateConnected
import com.mrp.sml.ui.theme.StateConnecting
import com.mrp.sml.ui.viewmodel.TransferUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    uiState: TransferUiState = TransferUiState(),
    onPause: () -> Unit = {},
    onResume: () -> Unit = {},
    onCancel: () -> Unit = {},
    onRetry: () -> Unit = {},
    onBack: () -> Unit = {},
    onBackToHome: () -> Unit = {},
    onViewDetails: () -> Unit = {},
    onSendMore: () -> Unit = {},
    onViewFiles: () -> Unit = {}
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (uiState.totalBytes > 0) (uiState.transferredBytes.toFloat() / uiState.totalBytes).coerceIn(0f, 1f) else 0f,
        label = "progress"
    )

    Scaffold(
        topBar = {
            SMLTopBar(
                title = when (uiState.status) {
                    TransferStatus.TRANSFERRING -> "Sending"
                    TransferStatus.PAUSED -> "Paused"
                    TransferStatus.RESUMING -> "Reconnecting"
                    TransferStatus.COMPLETED -> "Complete"
                    TransferStatus.FAILED -> "Failed"
                    TransferStatus.CANCELLED -> "Cancelled"
                    TransferStatus.VERIFYING -> "Verifying"
                    else -> "Preparing"
                },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState.status) {
                TransferStatus.COMPLETED -> CompletedState(
                    totalFiles = uiState.totalFiles,
                    totalBytes = uiState.totalBytes,
                    onViewFiles = onViewFiles,
                    onSendMore = onSendMore,
                    onBackToHome = onBackToHome
                )

                TransferStatus.FAILED, TransferStatus.CANCELLED -> FailedState(
                    status = uiState.status,
                    errorMessage = uiState.errorMessage,
                    transferredBytes = uiState.transferredBytes,
                    onRetry = onRetry,
                    onBackToHome = onBackToHome,
                    onViewDetails = onViewDetails
                )

                TransferStatus.PAUSED -> PausedState(
                    transferredBytes = uiState.transferredBytes,
                    totalBytes = uiState.totalBytes,
                    progressPercent = uiState.progressPercent,
                    fileName = uiState.currentFileName,
                    onResume = onResume,
                    onCancel = onCancel
                )

                TransferStatus.VERIFYING -> VerifyingState()

                TransferStatus.RESUMING -> ReconnectingState(
                    attempt = uiState.retryAttempt,
                    onCancel = onCancel
                )

                TransferStatus.PENDING,
                TransferStatus.DISCOVERING,
                TransferStatus.CONNECTING -> PreparingState()

                TransferStatus.TRANSFERRING -> ActiveTransferState(
                    currentFileName = uiState.currentFileName,
                    currentFileIndex = uiState.currentFileIndex,
                    totalFiles = uiState.totalFiles,
                    transferredBytes = uiState.transferredBytes,
                    totalBytes = uiState.totalBytes,
                    progressPercent = uiState.progressPercent,
                    speed = uiState.speed,
                    eta = uiState.eta,
                    animatedProgress = animatedProgress,
                    canPause = uiState.canPause,
                    onPause = onPause,
                    onCancel = onCancel
                )
            }
        }
    }
}

@Composable
private fun CompletedState(
    totalFiles: Int,
    totalBytes: Long,
    onViewFiles: () -> Unit,
    onSendMore: () -> Unit,
    onBackToHome: () -> Unit
) {
    Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = "Transfer completed successfully",
        tint = StateConnected,
        modifier = Modifier.size(80.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text("Transfer Complete", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
    Text("$totalFiles file(s) transferred", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(4.dp))
    Text(FileUtils.formatFileSize(totalBytes), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(32.dp))
    Button(onClick = onViewFiles, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Default.Home, contentDescription = "View received files"); Spacer(modifier = Modifier.width(8.dp)); Text("View Files")
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(onClick = onSendMore, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send more files"); Spacer(modifier = Modifier.width(8.dp)); Text("Send More")
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextButton(onClick = onBackToHome) { Text("Back to Home") }
}

@Composable
private fun FailedState(
    status: TransferStatus,
    errorMessage: String?,
    transferredBytes: Long,
    onRetry: () -> Unit,
    onBackToHome: () -> Unit,
    onViewDetails: () -> Unit
) {
    Icon(imageVector = Icons.Default.Error, contentDescription = "Transfer failed", tint = Error, modifier = Modifier.size(80.dp))
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = if (status == TransferStatus.CANCELLED) "Transfer Cancelled" else "Transfer Failed",
        style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = errorMessage ?: "An unexpected error occurred.",
        style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center
    )
    if (transferredBytes > 0) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${FileUtils.formatFileSize(transferredBytes)} transferred before interruption",
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Button(onClick = onRetry, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Default.Refresh, contentDescription = "Retry transfer"); Spacer(modifier = Modifier.width(8.dp)); Text("Retry")
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(onClick = onBackToHome, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Default.Home, contentDescription = "Go back to home"); Spacer(modifier = Modifier.width(8.dp)); Text("Back to Home")
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextButton(onClick = onViewDetails) { Text("View Details") }
}

@Composable
private fun PausedState(
    transferredBytes: Long,
    totalBytes: Long,
    progressPercent: Float,
    fileName: String,
    onResume: () -> Unit,
    onCancel: () -> Unit
) {
    Icon(imageVector = Icons.Default.Pause, contentDescription = "Transfer paused", tint = Primary, modifier = Modifier.size(80.dp))
    Spacer(modifier = Modifier.height(16.dp))
    Text("Transfer Paused", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
    if (fileName.isNotBlank()) {
        Text(fileName, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(4.dp))
    }
    Text("%.1f%% transferred".format(progressPercent), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(4.dp))
    Text("${FileUtils.formatFileSize(transferredBytes)} of ${FileUtils.formatFileSize(totalBytes)}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(32.dp))
    Button(onClick = onResume, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Default.PlayArrow, contentDescription = "Resume transfer"); Spacer(modifier = Modifier.width(8.dp)); Text("Resume")
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(onClick = onCancel, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = Error)) {
        Icon(Icons.Default.Close, contentDescription = "Cancel transfer"); Spacer(modifier = Modifier.width(6.dp)); Text("Cancel Transfer")
    }
}

@Composable
private fun VerifyingState() {
    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = "Verifying file integrity", tint = Primary, modifier = Modifier.size(80.dp))
    Spacer(modifier = Modifier.height(16.dp))
    Text("Verifying Integrity", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    CircularProgressIndicator(modifier = Modifier.size(32.dp), strokeWidth = 3.dp)
    Spacer(modifier = Modifier.height(12.dp))
    Text("Checking file integrity after transfer...", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(4.dp))
    Text("Do not close the app", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
}

@Composable
private fun ReconnectingState(
    attempt: Int,
    onCancel: () -> Unit
) {
    Spacer(modifier = Modifier.height(40.dp))
    Icon(imageVector = Icons.Default.Error, contentDescription = "Connection lost, reconnecting", tint = StateConnecting, modifier = Modifier.size(80.dp))
    Spacer(modifier = Modifier.height(16.dp))
    Text("Connection Lost", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Trying to reconnect...", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text("Attempt $attempt", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(32.dp))
    OutlinedButton(onClick = onCancel, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = Error)) {
        Icon(Icons.Default.Close, contentDescription = null); Spacer(modifier = Modifier.width(6.dp)); Text("Cancel")
    }
}

@Composable
private fun PreparingState() {
    Spacer(modifier = Modifier.height(40.dp))
    CircularProgressIndicator(modifier = Modifier.size(48.dp), strokeWidth = 4.dp)
    Spacer(modifier = Modifier.height(16.dp))
    Text("Preparing transfer...", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
    Text("Checking connection\nGenerating metadata", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
}

@Composable
private fun ActiveTransferState(
    currentFileName: String,
    currentFileIndex: Int,
    totalFiles: Int,
    transferredBytes: Long,
    totalBytes: Long,
    progressPercent: Float,
    speed: Double,
    eta: Long,
    animatedProgress: Float,
    canPause: Boolean,
    onPause: () -> Unit,
    onCancel: () -> Unit
) {
    Text(
        text = currentFileName.ifBlank { "Transferring..." },
        style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
    )
    if (totalFiles > 1) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("File ${currentFileIndex + 1} of $totalFiles", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Spacer(modifier = Modifier.height(24.dp))
    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier.fillMaxWidth().height(8.dp),
        color = Primary, trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("%.1f%%".format(progressPercent), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(TransferUtils.formatSpeed(speed), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    if (eta > 0) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(TransferUtils.formatEta(eta), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(4.dp))
    }
    Text(
        text = "${FileUtils.formatFileSize(transferredBytes)} / ${FileUtils.formatFileSize(totalBytes)}",
        style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Stable connection", tint = StateConnected, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Stable connection", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = onPause, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp), enabled = canPause) {
            Icon(Icons.Default.Pause, contentDescription = "Pause transfer"); Spacer(modifier = Modifier.width(6.dp)); Text("Pause")
        }
        Button(onClick = onCancel, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Error), shape = RoundedCornerShape(16.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Cancel transfer"); Spacer(modifier = Modifier.width(6.dp)); Text("Cancel")
        }
    }
}
