package com.example.verodigitaltask

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.journeyapps.barcodescanner.CaptureActivity

@Composable
fun QrScannerScreen(
    navHostController: NavHostController,
    onQRCodeScanned: (String) -> Unit
) {
    val context = LocalContext.current as Activity
    var qrCode by remember { mutableStateOf("") }
    val qrScannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            qrCode = intent?.getStringExtra("SCAN_RESULT") ?: ""
            onQRCodeScanned(qrCode)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val scanIntent = Intent(context, CaptureActivity::class.java)
            qrScannerLauncher.launch(scanIntent)
        }) {
            Text(" QR ")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (qrCode.isNotEmpty()) {
            Text("Scanned QR Code: $qrCode")
        }
    }
}