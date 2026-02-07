# Notibot Implementation Plan

## Project Phases

This plan breaks down the Notibot project into manageable phases. As a beginner, we'll take an incremental approach, testing each component before moving to the next.

---

## Phase 1: Setup & Environment Preparation

**Goal**: Get all tools and development environments ready

### 1.1 Order Parts
- [ ] Review SHOPPING_LIST.md
- [ ] Place Amazon order for core components
- [ ] Order optional components if desired
- [ ] Estimated arrival: 2-5 days

### 1.2 Setup Development Environment
- [ ] Install Arduino IDE (latest version)
- [ ] Install ESP32 board support in Arduino IDE
  - Add URL to board manager: `https://dl.espressif.com/dl/package_esp32_index.json`
  - Install "ESP32 by Espressif Systems"
- [ ] Install required libraries:
  - Adafruit GFX Library
  - Adafruit SSD1306
  - U8g2 (alternative OLED library)
  - BLE libraries (included with ESP32)
- [ ] Test USB connection with ESP32 (upload basic blink sketch)

### 1.3 Setup Mobile Development (can be done in parallel)
- [ ] **Android**: Install Android Studio
- [ ] **iOS**: Install Xcode (requires Mac)
- [ ] Clone Notibot repository on development machine
- [ ] Review BLE library documentation

**Duration**: 1-2 days (waiting for parts + setup time)

---

## Phase 2: Hardware Prototyping & Testing

**Goal**: Wire up components and test each individually

### 2.1 Test ESP32 Board
- [ ] Connect ESP32 to computer via USB
- [ ] Upload "Blink" example sketch
- [ ] Verify LED blinks (confirms board works)
- [ ] Test serial monitor communication

### 2.2 Test Small OLED Display
- [ ] Wire 0.96" OLED to ESP32:
  - VCC → 3.3V
  - GND → GND
  - SCL → GPIO22 (default I2C clock)
  - SDA → GPIO21 (default I2C data)
- [ ] Run I2C scanner sketch to find display address
- [ ] Upload simple "Hello World" test sketch
- [ ] Verify display shows text

### 2.3 Test Large OLED Display
- [ ] Wire 1.3" OLED to ESP32 (same I2C bus, different address)
- [ ] Test display independently
- [ ] Verify it has different I2C address than small display

### 2.4 Test Dual Display Setup
- [ ] Connect both displays simultaneously
- [ ] Write sketch that writes to both displays
- [ ] Verify no I2C address conflicts
- [ ] Test different content on each screen

**Duration**: 2-3 days (testing and troubleshooting)

**Deliverable**: Working dual-display hardware prototype on breadboard

---

## Phase 3: Firmware Development (ESP32)

**Goal**: Build firmware that manages displays and BLE communication

### 3.1 Display Management Module
- [ ] Create display driver abstraction
- [ ] Implement function to update small screen (count)
- [ ] Implement function to update large screen (text)
- [ ] Add text scrolling for long messages
- [ ] Add notification cycling (if multiple notifications)
- [ ] Test with hardcoded sample data

**Files to create**:
- `firmware/notibot/display_manager.h`
- `firmware/notibot/display_manager.cpp`

### 3.2 BLE Communication Module
- [ ] Create BLE service and characteristics
  - Service UUID: custom UUID for Notibot
  - Characteristic 1: Notification count (read)
  - Characteristic 2: Notification data (read/notify)
- [ ] Implement BLE connection callbacks
- [ ] Add auto-reconnection logic
- [ ] Test BLE advertising (phone should see "Notibot")
- [ ] Test BLE read/write with mobile BLE testing app

**Files to create**:
- `firmware/notibot/ble_manager.h`
- `firmware/notibot/ble_manager.cpp`

### 3.3 Data Parser Module
- [ ] Define notification data structure
- [ ] Implement JSON parser (or simple binary protocol)
- [ ] Parse incoming notification data
- [ ] Extract: app name, title, content, timestamp
- [ ] Store notifications in circular buffer (last 5-10)

**Files to create**:
- `firmware/notibot/notification_parser.h`
- `firmware/notibot/notification_parser.cpp`

### 3.4 Main Firmware Integration
- [ ] Integrate all modules into main sketch
- [ ] Implement state machine:
  - Boot → Pairing mode → Connected → Displaying
- [ ] Add connection status indicator
- [ ] Add error handling
- [ ] Add serial debug logging
- [ ] Optimize memory usage
- [ ] Test complete firmware with simulated data

**Files to create**:
- `firmware/notibot/notibot.ino` (main sketch)
- `firmware/notibot/config.h` (configuration constants)

**Duration**: 5-7 days

**Deliverable**: Complete working firmware that displays test notifications

---

## Phase 4: Android App Development

**Goal**: Build Android app that sends notifications to Notibot

### 4.1 Project Setup
- [ ] Create new Android project in Android Studio
- [ ] Set minimum SDK to API 23 (Android 6.0)
- [ ] Add BLE permissions to manifest
- [ ] Add notification listener permission
- [ ] Set up project structure

### 4.2 Notification Listener Service
- [ ] Create NotificationListenerService
- [ ] Request notification access permission from user
- [ ] Capture all notifications
- [ ] Extract notification data (app, title, text)
- [ ] Filter system notifications
- [ ] Store notifications in local list

**Files to create**:
- `mobile-apps/android/app/src/main/java/com/notibot/NotificationListener.kt`
- `mobile-apps/android/app/src/main/java/com/notibot/NotificationData.kt`

### 4.3 BLE Communication
- [ ] Implement BLE scanning for Notibot device
- [ ] Connect to Notibot when found
- [ ] Discover services and characteristics
- [ ] Send notification count via BLE
- [ ] Send notification data via BLE
- [ ] Handle disconnection and reconnection
- [ ] Maintain persistent connection in foreground service

**Files to create**:
- `mobile-apps/android/app/src/main/java/com/notibot/BleManager.kt`
- `mobile-apps/android/app/src/main/java/com/notibot/BleService.kt`

### 4.4 User Interface
- [ ] Create main activity with:
  - Connection status
  - List of current notifications
  - Settings button
- [ ] Create settings screen:
  - Enable/disable per-app notifications
  - Connection controls (pair, unpair)
  - About/help section
- [ ] Add notification for persistent background service

**Files to create**:
- `mobile-apps/android/app/src/main/java/com/notibot/MainActivity.kt`
- `mobile-apps/android/app/src/main/java/com/notibot/SettingsActivity.kt`
- Layout XML files

### 4.5 Testing & Debugging
- [ ] Test notification capture
- [ ] Test BLE connection to ESP32
- [ ] Test data transmission
- [ ] Test with various notification types
- [ ] Test reconnection after disconnect
- [ ] Test background service persistence

**Duration**: 7-10 days

**Deliverable**: Working Android app that syncs notifications to Notibot

---

## Phase 5: iOS App Development

**Goal**: Build iOS app with similar functionality to Android app

### 5.1 Project Setup
- [ ] Create new iOS project in Xcode
- [ ] Set deployment target to iOS 12.0+
- [ ] Add Bluetooth permission to Info.plist
- [ ] Set up project structure
- [ ] Add CoreBluetooth framework

### 5.2 Notification Monitoring
- [ ] Implement UNUserNotificationCenter delegate
- [ ] Request notification permissions
- [ ] Capture notifications when app is in foreground
- [ ] Note: iOS limitations - cannot access all notifications like Android
- [ ] Workaround: User must manually forward or app shows current visible notifications
- [ ] Store notifications in array

**Files to create**:
- `mobile-apps/ios/Notibot/NotificationManager.swift`
- `mobile-apps/ios/Notibot/NotificationData.swift`

### 5.3 BLE Communication
- [ ] Create CBCentralManager
- [ ] Scan for Notibot peripheral
- [ ] Connect and discover services
- [ ] Send notification data via BLE characteristics
- [ ] Handle connection state changes
- [ ] Implement reconnection logic

**Files to create**:
- `mobile-apps/ios/Notibot/BLEManager.swift`

### 5.4 User Interface
- [ ] Create main view with SwiftUI or UIKit:
  - Connection status
  - Current notifications list
  - Settings button
- [ ] Create settings view:
  - Connection controls
  - App information
- [ ] Add proper state management

**Files to create**:
- `mobile-apps/ios/Notibot/ContentView.swift`
- `mobile-apps/ios/Notibot/SettingsView.swift`

### 5.5 Testing
- [ ] Test on physical iPhone (iOS Simulator doesn't support BLE)
- [ ] Test notification delivery
- [ ] Test BLE connection
- [ ] Test background behavior

**Duration**: 7-10 days (similar to Android, iOS development may be easier or harder depending on experience)

**Deliverable**: Working iOS app that syncs notifications to Notibot

---

## Phase 6: Integration & Testing

**Goal**: Test complete system end-to-end

### 6.1 Android Integration Testing
- [ ] Test full flow: Notification arrives → App captures → Sends to ESP32 → Display shows
- [ ] Test with multiple notifications
- [ ] Test connection stability over hours
- [ ] Test notification filtering
- [ ] Test edge cases (long text, special characters, emojis)

### 6.2 iOS Integration Testing
- [ ] Same tests as Android
- [ ] Document iOS limitations
- [ ] Test battery impact

### 6.3 Performance Testing
- [ ] Measure notification latency
- [ ] Test BLE range
- [ ] Measure power consumption
- [ ] Test memory usage on ESP32
- [ ] Stress test with many rapid notifications

### 6.4 Bug Fixes & Optimization
- [ ] Fix any bugs discovered during testing
- [ ] Optimize display refresh rate
- [ ] Optimize BLE transmission frequency
- [ ] Reduce power consumption
- [ ] Improve error handling

**Duration**: 3-5 days

**Deliverable**: Fully working prototype ready for permanent assembly

---

## Phase 7: Permanent Assembly & Enclosure

**Goal**: Build final version with permanent assembly

### 7.1 Circuit Design
- [ ] Create proper wiring diagram
- [ ] Design PCB layout (optional - can use protoboard)
- [ ] Order PCB if going custom route
- [ ] Otherwise, plan protoboard layout

### 7.2 Soldering & Assembly
- [ ] Solder components to protoboard/PCB
- [ ] Test all connections
- [ ] Ensure displays are securely mounted
- [ ] Add power connector

### 7.3 Enclosure Design
- [ ] Measure final dimensions of assembled electronics
- [ ] Design 3D model for enclosure with cutouts for:
  - Both displays (visible through front)
  - USB power port
  - LED indicator (if added)
- [ ] Export STL file for 3D printing

**Files to create**:
- `3d-models/notibot-case.stl`
- `hardware/wiring-diagram.png`
- `hardware/assembly-guide.md`

### 7.4 3D Printing & Final Assembly
- [ ] 3D print enclosure (or order from service)
- [ ] Assemble electronics into case
- [ ] Test everything still works
- [ ] Make any necessary adjustments
- [ ] Final cleanup and cable management

**Duration**: 5-7 days (includes 3D printing time)

**Deliverable**: Complete, assembled Notibot device

---

## Phase 8: Documentation & Polish

**Goal**: Create user documentation and finalize project

### 8.1 User Documentation
- [ ] Write beginner-friendly setup guide
- [ ] Create quick start guide
- [ ] Document troubleshooting steps
- [ ] Add photos/videos of assembly
- [ ] Create FAQ section

### 8.2 Developer Documentation
- [ ] Comment code thoroughly
- [ ] Create API documentation for BLE protocol
- [ ] Document firmware architecture
- [ ] Add contribution guidelines
- [ ] Create issue templates for GitHub

### 8.3 Repository Finalization
- [ ] Clean up code
- [ ] Add LICENSE file
- [ ] Update README with final photos
- [ ] Tag v1.0 release
- [ ] Create release notes

**Duration**: 2-3 days

**Deliverable**: Complete, documented, open-source project

---

## Timeline Summary

| Phase | Duration | Dependencies |
|-------|----------|--------------|
| Phase 1: Setup | 1-2 days | None |
| Phase 2: Hardware Testing | 2-3 days | Phase 1 complete, parts arrived |
| Phase 3: Firmware Development | 5-7 days | Phase 2 complete |
| Phase 4: Android App | 7-10 days | Phase 3 complete (can overlap) |
| Phase 5: iOS App | 7-10 days | Phase 3 complete (can overlap with Phase 4) |
| Phase 6: Integration Testing | 3-5 days | Phases 3, 4, 5 complete |
| Phase 7: Assembly & Enclosure | 5-7 days | Phase 6 complete |
| Phase 8: Documentation | 2-3 days | Phase 7 complete |

**Total Estimated Time**: 6-8 weeks working part-time (evenings/weekends)

**Total Estimated Time**: 3-4 weeks working full-time

---

## Risk Management

### Technical Risks

| Risk | Mitigation |
|------|------------|
| BLE connection unstable | Implement robust reconnection logic, use connection parameters tuning |
| I2C address conflict | Use I2C scanner, verify addresses, use multiplexer if needed |
| ESP32 memory overflow | Optimize data structures, use streaming instead of buffering |
| iOS notification access limitations | Document limitations clearly, implement workarounds, set user expectations |
| Display text too small | Test early, choose appropriate font sizes, consider larger displays |

### Non-Technical Risks

| Risk | Mitigation |
|------|------------|
| Parts don't arrive on time | Order early, have backup suppliers |
| Component incompatibility | Research thoroughly before ordering, read reviews |
| Lack of specific expertise | Follow tutorials, use community forums, ask for help |
| Scope creep | Stick to v1.0 requirements, defer enhancements to future versions |
| Budget overrun | Order minimum viable parts first, buy additional items as needed |

---

## Success Metrics

- [ ] Device successfully connects to phone via BLE
- [ ] Notifications display within 1 second of arriving on phone
- [ ] Device runs for 24+ hours without crashing
- [ ] Both Android and iOS apps functional
- [ ] Total cost under $100
- [ ] Setup takes less than 10 minutes
- [ ] Code is documented and open-sourced

---

## Next Steps

**Immediate next steps**:
1. Review and order parts from SHOPPING_LIST.md
2. Install Arduino IDE and ESP32 support
3. While waiting for parts, read BLE and OLED tutorials
4. Begin Phase 1 setup tasks

**Weekly Goals Template**:
- Week 1: Complete Phase 1 & 2 (setup + hardware testing)
- Week 2-3: Complete Phase 3 (firmware)
- Week 3-4: Complete Phase 4 (Android app)
- Week 4-5: Complete Phase 5 (iOS app)
- Week 5-6: Complete Phase 6 (testing)
- Week 6-7: Complete Phase 7 (assembly)
- Week 7-8: Complete Phase 8 (documentation)

---

## Notes for Beginners

- Don't be discouraged if things don't work immediately - debugging is part of the learning process
- Test each component individually before combining them
- Use serial monitor extensively for debugging
- Google error messages - someone has likely encountered the same issue
- Join Arduino and ESP32 communities for help
- Take breaks when stuck - fresh perspective helps
- Document your progress - it helps track what works and what doesn't
- Have fun! This is a learning project, mistakes are opportunities

---

**Last Updated**: [Date will be auto-updated]
**Status**: Planning phase complete, ready to begin Phase 1
