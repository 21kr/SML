package com.mrp.sml.ui.screens.history

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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mrp.sml.core.utils.FileUtils
import com.mrp.sml.domain.model.TransferModel
import com.mrp.sml.ui.components.SMLTopBar
import com.mrp.sml.ui.theme.Error
import com.mrp.sml.ui.theme.Primary
import com.mrp.sml.ui.theme.StatusCompleted
import com.mrp.sml.ui.theme.StatusFailed
import com.mrp.sml.ui.theme.StatusReceived
import com.mrp.sml.ui.viewmodel.HistoryUiState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class HistoryFilter {
    ALL, SENT, RECEIVED, FAILED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    uiState: HistoryUiState = HistoryUiState(),
    onFilterChange: (HistoryFilter) -> Unit = {},
    onClearHistory: () -> Unit = {},
    onRetryTransfer: (String) -> Unit = {},
    onOpenFile: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            SMLTopBar(
                title = "History",
                showBackButton = true,
                onBackClick = onBack,
                actions = {
                    if (uiState.filteredTransfers.isNotEmpty()) {
                        IconButton(onClick = onClearHistory) {
                            Icon(
                                Icons.Default.DeleteSweep,
                                contentDescription = "Clear history"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.filteredTransfers.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No transfers yet",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your transfer history will appear here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                FilterRow(
                    currentFilter = uiState.filter,
                    onFilterChange = onFilterChange
                )
                Spacer(modifier = Modifier.height(8.dp))
                TransferList(
                    transfers = uiState.filteredTransfers,
                    onRetryTransfer = onRetryTransfer,
                    onOpenFile = onOpenFile
                )
            }
        }
    }
}

@Composable
private fun FilterRow(
    currentFilter: HistoryFilter,
    onFilterChange: (HistoryFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HistoryFilter.entries.forEach { filterOption ->
            FilterChip(
                selected = currentFilter == filterOption,
                onClick = { onFilterChange(filterOption) },
                label = {
                    Text(
                        when (filterOption) {
                            HistoryFilter.ALL -> "All"
                            HistoryFilter.SENT -> "Sent"
                            HistoryFilter.RECEIVED -> "Received"
                            HistoryFilter.FAILED -> "Failed"
                        }
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary.copy(alpha = 0.15f)
                )
            )
        }
    }
}

@Composable
private fun TransferList(
    transfers: List<TransferModel>,
    onRetryTransfer: (String) -> Unit,
    onOpenFile: (String) -> Unit
) {
    val grouped = groupByDate(transfers)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        grouped.forEach { (label, items) ->
            item {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            items(items, key = { it.id }) { transfer ->
                HistoryItem(
                    transfer = transfer,
                    onRetry = { onRetryTransfer(transfer.id) },
                    onOpenFile = { onOpenFile(transfer.id) }
                )
            }
        }
    }
}

private data class DateGroup(
    val label: String,
    val items: List<TransferModel>
)

private fun groupByDate(transfers: List<TransferModel>): List<DateGroup> {
    val calendar = Calendar.getInstance()
    val today = calendar.apply { set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0) }.timeInMillis
    val yesterday = today - 86400000L

    val groups = mutableListOf<DateGroup>()
    val todayItems = mutableListOf<TransferModel>()
    val yesterdayItems = mutableListOf<TransferModel>()
    val olderItems = mutableListOf<TransferModel>()

    transfers.forEach { transfer ->
        val date = transfer.completedAt ?: transfer.startedAt
        when {
            date >= today -> todayItems.add(transfer)
            date >= yesterday -> yesterdayItems.add(transfer)
            else -> olderItems.add(transfer)
        }
    }

    if (todayItems.isNotEmpty()) groups.add(DateGroup("Today", todayItems))
    if (yesterdayItems.isNotEmpty()) groups.add(DateGroup("Yesterday", yesterdayItems))
    if (olderItems.isNotEmpty()) {
        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val olderGrouped = olderItems.groupBy {
            dateFormat.format(Date(it.completedAt ?: it.startedAt))
        }
        olderGrouped.forEach { (dateLabel, items) ->
            groups.add(DateGroup(dateLabel, items))
        }
    }

    return groups
}

@Composable
private fun HistoryItem(
    transfer: TransferModel,
    onRetry: () -> Unit = {},
    onOpenFile: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val directionLabel = if (transfer.direction == TransferModel.TransferDirection.SENT) "Sent" else "Received"
            Icon(
                imageVector = if (transfer.direction == TransferModel.TransferDirection.SENT)
                    Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = directionLabel,
                tint = if (transfer.direction == TransferModel.TransferDirection.SENT) Primary else StatusReceived,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transfer.fileName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (transfer.peerDeviceName.isNotBlank()) {
                        Icon(
                            Icons.Default.Wifi,
                            contentDescription = "Device: ${transfer.peerDeviceName}",
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = transfer.peerDeviceName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = FileUtils.formatFileSize(transfer.fileSize),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                val statusLabel = when (transfer.status) {
                    TransferModel.TransferStatus.COMPLETED -> "Completed"
                    TransferModel.TransferStatus.FAILED -> "Failed"
                    TransferModel.TransferStatus.CANCELLED -> "Cancelled"
                    else -> transfer.status.name
                }
                val statusColor = when (transfer.status) {
                    TransferModel.TransferStatus.COMPLETED -> StatusCompleted
                    TransferModel.TransferStatus.FAILED -> StatusFailed
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
                Card(
                    shape = RoundedCornerShape(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = statusColor.copy(alpha = 0.15f)
                    )
                ) {
                    Text(
                        text = statusLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = timeFormat.format(Date(transfer.startedAt)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 46.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            when (transfer.status) {
                TransferModel.TransferStatus.FAILED -> {
                    IconButton(
                        onClick = onRetry,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Retry transfer",
                            tint = Error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                TransferModel.TransferStatus.COMPLETED -> {
                    if (transfer.direction == TransferModel.TransferDirection.RECEIVED) {
                        IconButton(
                            onClick = onOpenFile,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Default.FolderOpen,
                                contentDescription = "Open received file",
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
