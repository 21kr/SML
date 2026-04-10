package com.mrp.sml.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mrp.sml.ui.filepicker.FilePickerViewModel
import com.mrp.sml.ui.filepicker.SelectedFileUiModel
import com.mrp.sml.ui.transfer.TransferDirection
import com.mrp.sml.ui.transfer.TransferProgressViewModel

@Composable
fun HelloScreen(
    filePickerViewModel: FilePickerViewModel = hiltViewModel(),
    transferProgressViewModel: TransferProgressViewModel = hiltViewModel(),
) {
    val uiState by filePickerViewModel.uiState.collectAsStateWithLifecycle()
    val transferUiState by transferProgressViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris: List<Uri> ->
            filePickerViewModel.onFilesSelected(context = context, uris = uris)
        },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "SML File Picker",
                style = MaterialTheme.typography.headlineSmall,
            )

            Button(
                onClick = {
                    pickerLauncher.launch(arrayOf("*/*"))
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Choose files")
            }

            TransferProgressSection(
                uiState = transferUiState,
                onDirectionSelected = transferProgressViewModel::setDirection,
            )

            if (uiState.selectedFiles.isEmpty()) {
                Text(
                    text = "No files selected",
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(uiState.selectedFiles, key = { it.uri }) { file ->
                        SelectedFileCard(file = file)
                    }
                }
            }
        }
    }
}

@Composable
private fun TransferProgressSection(
    uiState: com.mrp.sml.ui.transfer.TransferProgressUiState,
    onDirectionSelected: (TransferDirection) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "Transfer Progress",
                style = MaterialTheme.typography.titleMedium,
            )

            LinearProgressIndicator(
                progress = { uiState.progressPercent / 100f },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "Progress: ${uiState.progressPercentText}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Speed: ${uiState.speedText}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Status: ${uiState.statusLabel}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = uiState.direction == TransferDirection.SENDING,
                    onClick = { onDirectionSelected(TransferDirection.SENDING) },
                    label = { Text("Sending") },
                )
                FilterChip(
                    selected = uiState.direction == TransferDirection.RECEIVING,
                    onClick = { onDirectionSelected(TransferDirection.RECEIVING) },
                    label = { Text("Receiving") },
                )
            }
        }
    }
}

@Composable
private fun SelectedFileCard(file: SelectedFileUiModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = file.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Size: ${file.readableSize}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Type: ${file.type}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
