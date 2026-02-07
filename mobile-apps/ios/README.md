# Notibot iOS App

Companion iOS app that sends phone notifications to Notibot device via Bluetooth Low Energy.

## Features

- Monitors user notifications
- Sends notifications to Notibot ESP32 device via BLE
- Connection status monitoring
- Auto-reconnection
- SwiftUI interface

## Requirements

- iOS 12.0 or higher
- iPhone with Bluetooth Low Energy support
- Xcode 13.0 or later
- Mac computer (for development)

## iOS Limitations

**Important**: iOS has stricter notification access compared to Android:
- Apps cannot access all system notifications like Android's NotificationListenerService
- User must explicitly share notifications with the app
- Cannot read notification history
- Limited background notification access

### Workarounds
1. **Manual notification forwarding**: User manually taps to share notification
2. **Current notifications only**: Display only currently visible notifications
3. **Direct notification integration**: If user receives notification while app is active, we can capture it

## Development Environment

### Required Software
- macOS (required for iOS development)
- Xcode 13.0 or later
- iOS device (Simulator doesn't support BLE)
- Apple Developer account (free account works for testing)

### Installation Steps

1. **Install Xcode**
   - Download from Mac App Store or https://developer.apple.com/xcode/

2. **Open Project**
   - Open Xcode
   - Select "Open a project or file"
   - Navigate to `mobile-apps/ios/Notibot.xcodeproj`

3. **Configure Signing**
   - Select project in navigator
   - Go to "Signing & Capabilities"
   - Select your Team (Apple ID)
   - Xcode will automatically create provisioning profile

4. **Connect iOS Device**
   - Connect iPhone via USB
   - Trust computer on iPhone if prompted
   - Select device as build target in Xcode

## Project Structure

```
ios/
├── Notibot/
│   ├── NotibotApp.swift          # App entry point
│   ├── ContentView.swift          # Main view
│   ├── SettingsView.swift         # Settings view
│   ├── BLEManager.swift           # BLE communication
│   ├── NotificationManager.swift  # Notification handling
│   ├── NotificationData.swift     # Data models
│   └── Assets.xcassets            # Images, colors
├── Notibot.xcodeproj
└── README.md                      # This file
```

## Permissions & Capabilities

### Info.plist Entries

```xml
<key>NSBluetoothAlwaysUsageDescription</key>
<string>Notibot needs Bluetooth to send notifications to your device</string>

<key>NSBluetoothPeripheralUsageDescription</key>
<string>Notibot connects to your notification display device</string>

<key>NSUserNotificationsUsageDescription</key>
<string>Notibot needs notification access to display them on your device</string>
```

### Capabilities
- Background Modes: Bluetooth LE accessories

## Building & Running

1. **Select your iOS device** as build target
2. **Build project**: Product → Build (Cmd+B)
3. **Run app**: Product → Run (Cmd+R)

Or use xcodebuild:
```bash
xcodebuild -project Notibot.xcodeproj -scheme Notibot build
```

## Setup & Configuration

### First-Time Setup
1. Install app on iPhone
2. Open app
3. Grant Bluetooth permissions when prompted
4. Grant notification permissions when prompted
5. Tap "Connect to Notibot"
6. Select Notibot device from list

### Notification Access
Due to iOS limitations, notifications are captured when:
- App is in foreground
- App receives notification while running in background
- User explicitly shares notification with app

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
Format: UInt16 (2 bytes)
```

**Notification Data** (Write)
```
UUID: 1c95d5e3-d8f7-413a-bf3d-7a2e5d7be87e
Type: Write, Notify
Format: JSON string
```

### Data Format (JSON)
```json
{
  "app": "Messages",
  "title": "Jane Doe",
  "text": "See you tomorrow!",
  "timestamp": 1234567890
}
```

## Architecture

### Components

1. **NotibotApp**
   - Main app structure (SwiftUI App protocol)
   - App lifecycle management
   - Notification delegate setup

2. **ContentView**
   - Main UI with connection status
   - Current notifications list
   - Connect/disconnect button
   - Navigation to settings

3. **BLEManager** (ObservableObject)
   - CBCentralManager wrapper
   - Scans for Notibot peripheral
   - Manages connection state
   - Sends data to ESP32
   - Handles reconnection

4. **NotificationManager**
   - UNUserNotificationCenterDelegate
   - Captures foreground notifications
   - Formats notification data
   - Passes to BLEManager

5. **NotificationData** (Model)
   - Data structure for notifications
   - Codable for JSON serialization
   - Published for SwiftUI updates

## Code Example: BLEManager

```swift
import CoreBluetooth
import Combine

class BLEManager: NSObject, ObservableObject {
    @Published var isConnected = false
    @Published var notificationCount = 0

    private var centralManager: CBCentralManager!
    private var notibotPeripheral: CBPeripheral?

    override init() {
        super.init()
        centralManager = CBCentralManager(delegate: self, queue: nil)
    }

    func startScanning() {
        centralManager.scanForPeripherals(
            withServices: [CBUUID(string: "4fafc201-1fb5-459e-8fcc-c5c9c331914b")],
            options: nil
        )
    }

    func sendNotification(_ notification: NotificationData) {
        // Encode and send via BLE characteristic
    }
}
```

## Testing

### Manual Testing Checklist
- [ ] App requests and receives Bluetooth permission
- [ ] App requests and receives notification permission
- [ ] BLE scanning finds Notibot device
- [ ] Connection establishes successfully
- [ ] Notifications captured when app is active
- [ ] Data sent to ESP32 via BLE
- [ ] App reconnects after disconnect
- [ ] UI updates reflect connection state

### Debug Logging
Enable Bluetooth logging in Console app:
1. Open Console.app on Mac
2. Connect iPhone
3. Filter for "Notibot" or "CoreBluetooth"

## Troubleshooting

### BLE Not Finding Device
- Ensure Bluetooth is enabled on iPhone
- Check ESP32 is advertising
- Verify service UUID matches
- Try disabling/enabling Bluetooth
- Check BLE range (within 10 meters)

### Notifications Not Captured
- iOS limitations - cannot access all notifications
- Ensure app has notification permissions
- App must be running (foreground or background)
- Test with app in foreground first

### Connection Drops
- Check ESP32 is powered on
- Verify Bluetooth range
- Check for interference
- Review logs in Console.app

### Build Errors
- Clean build folder: Product → Clean Build Folder
- Delete DerivedData: `~/Library/Developer/Xcode/DerivedData`
- Check provisioning profile is valid
- Ensure deployment target matches device iOS version

## Development Phases

See [PLAN.md](../../docs/PLAN.md) Phase 5 for detailed development steps.

### Current Status: Planning

**Next Steps**:
1. Create new iOS project in Xcode
2. Set up project structure
3. Implement BLEManager
4. Implement NotificationManager
5. Create SwiftUI views
6. Test with Notibot device

## Dependencies

Using Swift Package Manager (no external dependencies required for basic functionality):

Optional:
- Add via Xcode: File → Add Packages...
- No required third-party packages for basic BLE functionality

## SwiftUI vs UIKit

This project uses **SwiftUI** for the interface because:
- Modern, declarative syntax
- Less boilerplate code
- Easier state management with `@Published` and `@ObservedObject`
- Better for beginners

If you prefer UIKit, the BLE logic remains the same - only UI code changes.

## Resources

- [CoreBluetooth Documentation](https://developer.apple.com/documentation/corebluetooth)
- [UserNotifications Framework](https://developer.apple.com/documentation/usernotifications)
- [SwiftUI Tutorials](https://developer.apple.com/tutorials/swiftui)
- [BLE on iOS Tutorial](https://www.raywenderlich.com/231-core-bluetooth-tutorial-for-ios-heart-rate-monitor)

## Known Limitations

1. **Cannot access all notifications** - iOS security restriction
2. **Requires app to be running** - Background notification access is limited
3. **No notification history** - Can only access current notifications
4. **Mac required** - Cannot develop iOS apps without macOS

These are platform limitations, not implementation issues.

## License

TBD
