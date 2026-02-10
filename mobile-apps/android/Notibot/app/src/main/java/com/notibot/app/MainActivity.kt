package com.notibot.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.notibot.app.ui.theme.NotibotTheme

class MainActivity : ComponentActivity() {

    private lateinit var bleManager: BleManager
    private var isConnected by mutableStateOf(false)
    private var statusText by mutableStateOf("Not connected")
    private var isScanning by mutableStateOf(false)

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            startScanning()
        } else {
            Toast.makeText(this, "Bluetooth permissions required", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bleManager = BleManager(this)
        NotificationService.bleManager = bleManager

        bleManager.onConnectionStateChange = { connected ->
            isConnected = connected
            statusText = if (connected) "Connected to Notibot!" else "Disconnected"
            isScanning = false
        }

        bleManager.onDeviceFound = { device ->
            statusText = "Found Notibot, connecting..."
        }

        setContent {
            NotibotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotibotScreen()
                }
            }
        }
    }

    @Composable
    fun NotibotScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Notibot",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Connection status
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isConnected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isConnected) "Connected" else "Disconnected",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Connect button
            Button(
                onClick = { checkPermissionsAndScan() },
                enabled = !isConnected && !isScanning,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isScanning) "Scanning..." else "Connect to Notibot")
            }

            // Disconnect button
            if (isConnected) {
                OutlinedButton(
                    onClick = { bleManager.disconnect() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Disconnect")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notification access button
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Notification Access",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Required to read your notifications",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { openNotificationSettings() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Grant Access")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Test button
            if (isConnected) {
                OutlinedButton(
                    onClick = { sendTestNotification() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Test Notification")
                }
            }
        }
    }

    private fun checkPermissionsAndScan() {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (permissions.isNotEmpty()) {
            permissionLauncher.launch(permissions.toTypedArray())
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        if (!bleManager.isBluetoothEnabled()) {
            Toast.makeText(this, "Please enable Bluetooth", Toast.LENGTH_LONG).show()
            return
        }

        isScanning = true
        statusText = "Scanning for Notibot..."
        bleManager.startScan()
    }

    private fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        startActivity(intent)
    }

    private fun sendTestNotification() {
        bleManager.sendNotification(3, "Test", "Hello!", "This is a test from Notibot app")
    }

    override fun onDestroy() {
        super.onDestroy()
        bleManager.disconnect()
    }
}
