/**
 * Notibot - Physical Notification Display
 *
 * Receives notifications from phone via BLE and displays on dual OLED screens
 * - Small screen (0.96" SSD1306): Notification count
 * - Large screen (1.3" SH1106): Notification content
 *
 * Hardware:
 * - ESP32 DevKit V1
 * - 0.96" OLED (SSD1306) on I2C bus 0: SDA=21, SCL=22
 * - 1.3" OLED (SH1106) on I2C bus 1: SDA=16, SCL=17
 */

#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include <Adafruit_SH110X.h>
#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>

// Display settings
#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64
#define OLED_ADDR 0x3C

// BLE UUIDs
#define SERVICE_UUID        "12345678-1234-1234-1234-123456789abc"
#define CHAR_NOTIF_UUID     "12345678-1234-1234-1234-123456789abd"

// Forward declarations
void updateSmallDisplay(int count);
void updateLargeDisplay(String line1, String line2, String line3);
void parseNotification(String data);

// I2C buses
TwoWire I2C_small = TwoWire(0);
TwoWire I2C_large = TwoWire(1);

// Displays
Adafruit_SSD1306 smallDisplay(SCREEN_WIDTH, SCREEN_HEIGHT, &I2C_small, -1);
Adafruit_SH1106G largeDisplay(SCREEN_WIDTH, SCREEN_HEIGHT, &I2C_large, -1);

// BLE
BLEServer *pServer = NULL;
BLECharacteristic *pCharacteristic = NULL;
bool deviceConnected = false;
bool oldDeviceConnected = false;

// Notification state
int notificationCount = 0;
String currentApp = "";
String currentTitle = "";
String currentText = "";
unsigned long lastUpdateTime = 0;

// BLE Server Callbacks
class ServerCallbacks : public BLEServerCallbacks {
  void onConnect(BLEServer* pServer) {
    deviceConnected = true;
    Serial.println("Phone connected!");
    updateLargeDisplay("Connected!", "Waiting for", "notifications...");
  }

  void onDisconnect(BLEServer* pServer) {
    deviceConnected = false;
    Serial.println("Phone disconnected!");
    updateLargeDisplay("Disconnected", "Open Notibot app", "on your phone");
  }
};

// BLE Characteristic Callbacks
class CharacteristicCallbacks : public BLECharacteristicCallbacks {
  void onWrite(BLECharacteristic *pCharacteristic) {
    String value = pCharacteristic->getValue().c_str();
    if (value.length() > 0) {
      Serial.print("Received: ");
      Serial.println(value);
      parseNotification(value);
    }
  }
};

void setup() {
  Serial.begin(115200);
  delay(100);
  Serial.println("\n\nNotibot starting...");

  // Initialize I2C buses
  I2C_small.begin(21, 22);
  I2C_large.begin(16, 17);
  delay(100);

  // Initialize displays
  initDisplays();

  // Initialize BLE
  initBLE();

  Serial.println("Notibot ready!");
}

void loop() {
  // Handle BLE reconnection
  if (!deviceConnected && oldDeviceConnected) {
    delay(500);
    pServer->startAdvertising();
    Serial.println("Advertising...");
    oldDeviceConnected = deviceConnected;
  }

  if (deviceConnected && !oldDeviceConnected) {
    oldDeviceConnected = deviceConnected;
  }

  delay(100);
}

void initDisplays() {
  Serial.println("Initializing displays...");

  // Small display
  if (!smallDisplay.begin(SSD1306_SWITCHCAPVCC, OLED_ADDR)) {
    Serial.println("Small display FAILED!");
  } else {
    Serial.println("Small display OK");
  }
  delay(100);

  // Large display
  if (!largeDisplay.begin(OLED_ADDR, true)) {
    Serial.println("Large display FAILED!");
  } else {
    Serial.println("Large display OK");
  }
  delay(100);

  // Initial display content
  updateSmallDisplay(0);
  updateLargeDisplay("Notibot", "Starting...", "");
  delay(1000);
  updateLargeDisplay("Ready!", "Pair phone via", "Bluetooth");
}

void initBLE() {
  Serial.println("Initializing BLE...");

  BLEDevice::init("Notibot");

  pServer = BLEDevice::createServer();
  pServer->setCallbacks(new ServerCallbacks());

  BLEService *pService = pServer->createService(SERVICE_UUID);

  pCharacteristic = pService->createCharacteristic(
    CHAR_NOTIF_UUID,
    BLECharacteristic::PROPERTY_READ |
    BLECharacteristic::PROPERTY_WRITE |
    BLECharacteristic::PROPERTY_NOTIFY
  );

  pCharacteristic->addDescriptor(new BLE2902());
  pCharacteristic->setCallbacks(new CharacteristicCallbacks());

  pService->start();

  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(SERVICE_UUID);
  pAdvertising->setScanResponse(true);
  pAdvertising->setMinPreferred(0x06);
  pAdvertising->setMinPreferred(0x12);
  BLEDevice::startAdvertising();

  Serial.println("BLE advertising started");
}

void updateSmallDisplay(int count) {
  notificationCount = count;

  smallDisplay.clearDisplay();
  smallDisplay.setTextColor(SSD1306_WHITE);

  // Draw count (big number in center)
  if (count > 99) {
    smallDisplay.setTextSize(2);
    smallDisplay.setCursor(20, 20);
    smallDisplay.println("99+");
  } else {
    smallDisplay.setTextSize(4);
    if (count < 10) {
      smallDisplay.setCursor(52, 15);
    } else {
      smallDisplay.setCursor(38, 15);
    }
    smallDisplay.println(count);
  }

  smallDisplay.display();
}

void updateLargeDisplay(String line1, String line2, String line3) {
  largeDisplay.clearDisplay();
  largeDisplay.setTextColor(SH110X_WHITE);
  largeDisplay.setTextSize(1);

  // Line 1 - App name or title (larger)
  largeDisplay.setTextSize(2);
  largeDisplay.setCursor(0, 0);
  largeDisplay.println(line1.substring(0, 10));  // Truncate if too long

  // Line 2
  largeDisplay.setTextSize(1);
  largeDisplay.setCursor(0, 25);
  largeDisplay.println(line2.substring(0, 21));

  // Line 3
  largeDisplay.setCursor(0, 40);
  largeDisplay.println(line3.substring(0, 21));

  largeDisplay.display();
}

void parseNotification(String data) {
  // Expected format: "COUNT|APP|TITLE|TEXT"
  // Example: "5|Gmail|New Email|Hello world..."

  int sep1 = data.indexOf('|');
  int sep2 = data.indexOf('|', sep1 + 1);
  int sep3 = data.indexOf('|', sep2 + 1);

  if (sep1 > 0 && sep2 > 0 && sep3 > 0) {
    int count = data.substring(0, sep1).toInt();
    String app = data.substring(sep1 + 1, sep2);
    String title = data.substring(sep2 + 1, sep3);
    String text = data.substring(sep3 + 1);

    Serial.printf("Count: %d, App: %s, Title: %s\n", count, app.c_str(), title.c_str());

    updateSmallDisplay(count);
    updateLargeDisplay(app, title, text);
  } else if (sep1 > 0) {
    // Just count update: "0|cleared"
    int count = data.substring(0, sep1).toInt();
    updateSmallDisplay(count);
    if (count == 0) {
      updateLargeDisplay("All clear!", "No new", "notifications");
    }
  }
}
