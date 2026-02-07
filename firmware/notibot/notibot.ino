/**
 * Notibot - Physical Notification Display
 *
 * Main firmware for ESP32
 * Receives notifications from phone via BLE and displays on dual OLED screens
 *
 * Hardware:
 * - ESP32 Development Board
 * - 0.96" OLED Display (128x64) - Small screen for count
 * - 1.3" OLED Display (128x64) - Large screen for content
 *
 * Author: Created with Claude Code
 * License: TBD
 */

#include <Wire.h>
// TODO: Include OLED library (Adafruit_SSD1306 or U8g2)
// TODO: Include BLE library

// Pin definitions
#define SDA_PIN 21
#define SCL_PIN 22

// I2C addresses for displays
#define SMALL_DISPLAY_ADDR 0x3C  // Typical address for 0.96" OLED
#define LARGE_DISPLAY_ADDR 0x3D  // Typical address for 1.3" OLED (may need adjustment)

// Configuration
#define DEVICE_NAME "Notibot"
#define MAX_NOTIFICATIONS 10

// Global state
int notificationCount = 0;
bool bleConnected = false;

void setup() {
  Serial.begin(115200);
  Serial.println("Notibot starting...");

  // Initialize I2C
  Wire.begin(SDA_PIN, SCL_PIN);

  // TODO: Initialize displays
  // initDisplays();

  // TODO: Initialize BLE
  // initBLE();

  Serial.println("Notibot ready!");
}

void loop() {
  // TODO: Handle BLE communication
  // TODO: Update displays
  // TODO: Handle notifications

  delay(100);
}

// Display functions (to be implemented)
void initDisplays() {
  // Initialize both OLED displays
}

void updateSmallDisplay(int count) {
  // Update notification count on small display
}

void updateLargeDisplay(String appName, String title, String content) {
  // Update notification content on large display
}

// BLE functions (to be implemented)
void initBLE() {
  // Initialize BLE service and characteristics
}

void onBLEConnected() {
  bleConnected = true;
  Serial.println("Phone connected!");
}

void onBLEDisconnected() {
  bleConnected = false;
  Serial.println("Phone disconnected!");
}

void handleNotificationData(uint8_t* data, size_t length) {
  // Parse and handle incoming notification data
}
