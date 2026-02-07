# Notibot Requirements

## Product Vision

Notibot is a physical desktop notification display that allows users to monitor their smartphone notifications without constantly checking their phone. It provides an at-a-glance view of notification count and content through dual screens.

## User Stories

### Primary Use Cases
1. **As a user**, I want to see how many unread notifications I have at a glance
2. **As a user**, I want to read notification content without picking up my phone
3. **As a user**, I want the device to automatically connect to my phone via Bluetooth
4. **As a user**, I want notifications to update in real-time when new ones arrive
5. **As a user**, I want to use this device with both my Android and iOS phones

### Secondary Use Cases
6. **As a user**, I want the device to be compact enough for my desk
7. **As a user**, I want minimal setup and configuration
8. **As a user**, I want the device to consume low power

## Functional Requirements

### Hardware Display

#### Small Screen (Notification Count)
- **Size**: 0.96" OLED (128x64 pixels)
- **Display Content**:
  - Large number showing total notification count
  - Clear, readable font
  - Updates in real-time when count changes
- **Behavior**:
  - Show "0" when no notifications
  - Show count up to 99 (display "99+" if more)
  - Dim or sleep after 5 minutes of inactivity (optional)

#### Large Screen (Notification Content)
- **Size**: 1.3" OLED (128x64 pixels) or larger
- **Display Content**:
  - App name/icon identifier
  - Notification title
  - Notification text content (truncated if too long)
  - Timestamp (optional)
- **Behavior**:
  - Cycle through notifications if multiple exist
  - Each notification displays for 5-10 seconds
  - Scroll text if content is longer than screen width
  - Show "No notifications" when empty

### Bluetooth Connectivity

#### Pairing & Connection
- **Protocol**: Bluetooth Low Energy (BLE) 4.0+
- **Range**: Minimum 10 meters (typical BLE range)
- **Auto-connect**: Remember paired device and reconnect automatically
- **Pairing mode**: Enter pairing mode on first boot or via button press
- **Status indicator**: LED or on-screen indicator showing connection status

#### Data Transfer
- **Direction**: Phone → Notibot (one-way communication)
- **Data Format**: JSON or custom binary protocol
- **Update Frequency**: Real-time push when notifications change
- **Battery Efficiency**: Use BLE notifications/indications for low power

### Mobile Application

#### Android App
- **Minimum Version**: Android 6.0 (API 23) or higher
- **Permissions Required**:
  - Notification access
  - Bluetooth
  - Background service
- **Functionality**:
  - Notification listener service to capture all notifications
  - BLE GATT server/client to communicate with ESP32
  - Settings screen for filtering which apps to sync
  - Persistent background service

#### iOS App
- **Minimum Version**: iOS 12.0 or higher
- **Permissions Required**:
  - Bluetooth
  - Background modes
- **Functionality**:
  - Notification monitoring (via user notification center API)
  - CoreBluetooth for BLE communication
  - Settings for app filtering
  - Background notification delivery
- **Limitations**: iOS has stricter notification access - may require workarounds

### Firmware (ESP32)

#### Core Functionality
- BLE peripheral/central mode
- Receive notification data from phone
- Parse and format data for display
- Drive both OLED screens via I2C
- Handle reconnection logic
- Low power sleep modes when idle

#### Display Management
- Multiplexing between two I2C displays
- Text rendering and scrolling
- Screen brightness control
- Rotation/animation effects (optional enhancement)

## Non-Functional Requirements

### Performance
- **Boot time**: < 5 seconds
- **Connection time**: < 3 seconds to paired device
- **Notification latency**: < 1 second from phone to display
- **Screen refresh rate**: Smooth scrolling (no flicker)

### Power
- **Power source**: USB 5V (micro USB or USB-C)
- **Power consumption**: < 500mA average
- **Sleep mode**: Reduce to < 50mA when inactive
- **Always-on**: Device remains powered continuously

### Reliability
- **Uptime**: Run continuously for days/weeks without restart
- **Connection stability**: Auto-reconnect if Bluetooth drops
- **Error handling**: Graceful degradation if connection lost
- **Crash recovery**: Auto-restart if firmware crashes

### Usability
- **Setup time**: < 5 minutes for initial pairing and setup
- **User documentation**: Clear setup guide for beginners
- **Visual feedback**: Clear indicators for connection status
- **Minimal interaction**: Works automatically after initial setup

### Size & Form Factor
- **Dimensions**: Compact desktop size (~10cm x 8cm x 5cm)
- **Weight**: Lightweight (< 200g)
- **Aesthetics**: Clean, modern design
- **Portability**: Can be moved between desk locations easily

## Technical Constraints

### Hardware Limitations
- ESP32 memory: 520KB SRAM, 4MB Flash
- OLED resolution: 128x64 pixels (limited text display)
- I2C bus: Both displays on same bus (different addresses)
- BLE throughput: ~1 Mbps max (sufficient for text data)

### Platform Limitations
- **Android**: Full notification access available via NotificationListenerService
- **iOS**: Limited notification access - may need user to manually enable notifications in Control Center or use workarounds
- **BLE**: Maximum MTU size typically 23-512 bytes
- **Cross-platform**: Need separate apps for Android and iOS

## Future Enhancements (Out of Scope for v1.0)

- Touch screen interface for interaction
- WiFi connectivity for direct internet access
- Weather display, calendar integration
- Customizable themes and colors
- Multiple device pairing
- Notification priority/filtering on device
- Gesture controls (wave to cycle notifications)
- E-ink display for better battery life (if battery-powered version)
- Web dashboard for configuration
- Sound/vibration alerts for high-priority notifications

## Success Criteria

The Notibot v1.0 will be considered successful if:

1. It reliably displays notification count on small screen
2. It shows notification content on large screen
3. It connects to both Android and iOS phones via BLE
4. Notifications update within 1 second of arriving on phone
5. Device runs continuously without requiring restarts
6. Setup process is simple enough for beginners to follow
7. Total build cost remains under $100

## Assumptions & Dependencies

### Assumptions
- User has access to smartphone (Android or iOS)
- User has smartphone with BLE 4.0+ support
- User willing to install companion mobile app
- User has basic computer skills to program ESP32
- User has or can acquire necessary parts

### Dependencies
- Arduino IDE or PlatformIO for firmware development
- Android Studio for Android app (or React Native for cross-platform)
- Xcode for iOS app (requires Mac)
- BLE libraries: Arduino BLE, Android BLE API, CoreBluetooth
- OLED display libraries: Adafruit SSD1306, U8g2

## Open Questions

1. Should we support notification actions (dismiss, reply)?
2. Do we need persistent storage for notifications?
3. Should device work standalone without phone connected?
4. What happens to notifications when device is offline?
5. Should we implement notification history scrolling?
6. Do we need a physical button for interaction?

These questions can be answered during development based on feasibility and user feedback.
