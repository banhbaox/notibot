# Notibot Android App

Companion Android app that sends phone notifications to Notibot device via Bluetooth Low Energy.

## Features

- Captures all phone notifications via NotificationListenerService
- Sends notifications to Notibot ESP32 device via BLE
- Persistent background service
- Per-app notification filtering
- Connection status monitoring
- Auto-reconnection

## Requirements

- Android 6.0 (API 23) or higher
- Bluetooth Low Energy support
- Notification access permission

## Development Environment

### Required Software
- Android Studio Arctic Fox or later
- Android SDK 23+
- Kotlin plugin
- Physical Android device (for BLE testing)

### Installation Steps

1. **Install Android Studio**
   - Download from https://developer.android.com/studio

2. **Open Project**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to `mobile-apps/android/`

3. **Sync Gradle**
   - Let Android Studio sync Gradle dependencies
   - Install any missing SDK components if prompted

4. **Enable Developer Mode on Phone**
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go to Settings → Developer Options
   - Enable USB Debugging

## Project Structure

```
android/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/notibot/
│   │       │   ├── MainActivity.kt
│   │       │   ├── NotificationListener.kt
│   │       │   ├── BleManager.kt
│   │       │   ├── BleService.kt
│   │       │   ├── NotificationData.kt
│   │       │   └── SettingsActivity.kt
│   │       ├── res/           # Resources (layouts, strings, etc.)
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── README.md                  # This file
```

## Permissions

The app requires the following permissions:

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<service android:name=".NotificationListener"
    android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE">
    <intent-filter>
        <action android:name="android.service.notification.NotificationListenerService" />
    </intent-filter>
</service>
```

## Building & Running

1. **Connect Android device via USB**
2. **Build project**: Build → Make Project
3. **Run app**: Run → Run 'app'

Or use Gradle:
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Setup & Configuration

### First-Time Setup
1. Install app on phone
2. Open app
3. Grant notification access permission:
   - Tap "Enable Notification Access"
   - Toggle on "Notibot" in settings
4. Grant Bluetooth permissions when prompted
5. Tap "Scan for Notibot" to connect to device

### Notification Access
The app needs special permission to read notifications:
- Settings → Apps & notifications → Special app access → Notification access
- Enable "Notibot"

## BLE Communication Protocol

### Service UUID
```
Service: 4fafc201-1fb5-459e-8fcc-c5c9c331914b
```

### Characteristics

**Notification Count** (Read)
```
UUID: beb5483e-36e1-4688-b7f5-ea07361b26a8
Type: Read, Notify
Format: Uint16 (2 bytes)
```

**Notification Data** (Write)
```
UUID: 1c95d5e3-d8f7-413a-bf3d-7a2e5d7be87e
Type: Write, Notify
Format: JSON string or binary protocol
```

### Data Format (JSON)
```json
{
  "app": "WhatsApp",
  "title": "John Doe",
  "text": "Hey, are you there?",
  "timestamp": 1234567890
}
```

## Architecture

### Components

1. **MainActivity**
   - Main UI showing connection status
   - List of current notifications
   - Navigation to settings

2. **NotificationListener** (Service)
   - Extends NotificationListenerService
   - Captures all notifications
   - Filters and processes notification data
   - Passes data to BLE service

3. **BleManager**
   - Manages BLE scanning and connection
   - Discovers Notibot device
   - Maintains connection state
   - Handles reconnection logic

4. **BleService** (Foreground Service)
   - Persistent background service
   - Sends notifications to ESP32 via BLE
   - Keeps BLE connection alive
   - Shows persistent notification

5. **SettingsActivity**
   - App filtering preferences
   - Connection management
   - About/help information

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist
- [ ] App captures notifications from various apps
- [ ] BLE connection establishes successfully
- [ ] Notifications sent to ESP32 appear on display
- [ ] App reconnects after disconnect
- [ ] Background service persists after app close
- [ ] Per-app filtering works
- [ ] No crashes with various notification types

## Troubleshooting

### BLE Not Scanning
- Ensure location permission granted (required for BLE on Android)
- Check Bluetooth is enabled
- Try disabling/enabling Bluetooth
- Check device supports BLE

### Notifications Not Captured
- Verify notification access permission is granted
- Check NotificationListener service is running
- Restart app or phone

### Connection Drops
- Check ESP32 is powered on
- Verify Bluetooth range (within 10 meters)
- Check for BLE interference
- Review logs in Logcat

### Battery Drain
- Optimize BLE scan frequency
- Use BLE notifications instead of polling
- Reduce foreground service priority when idle

## Development Phases

See [PLAN.md](../../docs/PLAN.md) Phase 4 for detailed development steps.

### Current Status: Planning

**Next Steps**:
1. Create new Android project
2. Set up project structure
3. Implement NotificationListener
4. Implement BLE communication
5. Create UI
6. Test with Notibot device

## Dependencies (to be added to build.gradle)

```gradle
dependencies {
    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.appcompat:appcompat:1.6.0'
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

    // BLE
    implementation 'no.nordicsemi.android:ble:2.6.0'

    // JSON parsing
    implementation 'com.google.code.gson:gson:2.10'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
}
```

## Resources

- [Android BLE Documentation](https://developer.android.com/guide/topics/connectivity/bluetooth-le)
- [NotificationListenerService Guide](https://developer.android.com/reference/android/service/notification/NotificationListenerService)
- [Nordic BLE Library](https://github.com/NordicSemiconductor/Android-BLE-Library)

## License

TBD
