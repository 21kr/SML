package com.mrp.sml.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import com.mrp.sml.ui.filepicker.FilePickerViewModel
import com.mrp.sml.ui.theme.SMLTheme

@Composable
fun HelloScreen(
    filePickerViewModel: FilePickerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by filePickerViewModel.uiState.collectAsState()

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris: List<Uri> ->
            filePickerViewModel.onFilesSelected(context = context, uris = uris)
        },
    )

    MaterialTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SML File Share",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { 
                    pickerLauncher.launch(arrayOf("*/*"))
                }
            ) {
                Text("Select Files")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ready for P2P file transfer via WiFi Direct",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

