package com.notibot.app

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.UUID

@SuppressLint("MissingPermission")
class BleManager(private val context: Context) {

    companion object {
        private const val TAG = "BleManager"
        private const val DEVICE_NAME = "Notibot"
        private val SERVICE_UUID = UUID.fromString("12345678-1234-1234-1234-123456789abc")
        private val CHAR_UUID = UUID.fromString("12345678-1234-1234-1234-123456789abd")
        private const val SCAN_TIMEOUT = 10000L
    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private var characteristic: BluetoothGattCharacteristic? = null
    private var isScanning = false
    private val handler = Handler(Looper.getMainLooper())

    var onConnectionStateChange: ((Boolean) -> Unit)? = null
    var onDeviceFound: ((BluetoothDevice) -> Unit)? = null
    var isConnected = false
        private set

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val name = device.name ?: return
            Log.d(TAG, "Found device: $name")
            if (name == DEVICE_NAME) {
                stopScan()
                onDeviceFound?.invoke(device)
                connect(device)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e(TAG, "Scan failed: $errorCode")
            isScanning = false
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d(TAG, "Connected to Notibot")
                    isConnected = true
                    handler.post { onConnectionStateChange?.invoke(true) }
                    gatt.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.d(TAG, "Disconnected from Notibot")
                    isConnected = false
                    handler.post { onConnectionStateChange?.invoke(false) }
                    bluetoothGatt?.close()
                    bluetoothGatt = null
                    characteristic = null
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val service = gatt.getService(SERVICE_UUID)
                if (service != null) {
                    characteristic = service.getCharacteristic(CHAR_UUID)
                    Log.d(TAG, "Found Notibot characteristic")
                } else {
                    Log.e(TAG, "Service not found")
                }
            }
        }
    }

    fun startScan() {
        if (isScanning) return
        val scanner = bluetoothAdapter?.bluetoothLeScanner ?: return

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        Log.d(TAG, "Starting BLE scan...")
        isScanning = true
        scanner.startScan(null, settings, scanCallback)

        handler.postDelayed({
            if (isScanning) {
                stopScan()
                Log.d(TAG, "Scan timeout")
            }
        }, SCAN_TIMEOUT)
    }

    fun stopScan() {
        if (!isScanning) return
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
        isScanning = false
        Log.d(TAG, "Scan stopped")
    }

    fun connect(device: BluetoothDevice) {
        Log.d(TAG, "Connecting to ${device.name}...")
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    fun disconnect() {
        bluetoothGatt?.disconnect()
    }

    fun sendNotification(count: Int, app: String, title: String, text: String) {
        val char = characteristic ?: return
        val gatt = bluetoothGatt ?: return

        // Format: "COUNT|APP|TITLE|TEXT"
        val message = "$count|$app|$title|$text"
        Log.d(TAG, "Sending: $message")

        char.setValue(message.toByteArray(Charsets.UTF_8))
        gatt.writeCharacteristic(char)
    }

    fun sendCount(count: Int) {
        val char = characteristic ?: return
        val gatt = bluetoothGatt ?: return

        val message = "$count|cleared"
        char.setValue(message.toByteArray(Charsets.UTF_8))
        gatt.writeCharacteristic(char)
    }

    fun isBluetoothEnabled(): Boolean = bluetoothAdapter?.isEnabled == true
}
