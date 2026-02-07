# Notibot Firmware

ESP32 firmware for the Notibot notification display device.

## Development Environment

### Required Software
- Arduino IDE 1.8.19 or later (or Arduino IDE 2.x)
- ESP32 board support package

### Installation Steps

1. **Install Arduino IDE**
   - Download from https://www.arduino.cc/en/software

2. **Add ESP32 Board Support**
   - Open Arduino IDE
   - Go to File → Preferences
   - Add this URL to "Additional Board Manager URLs":
     ```
     https://dl.espressif.com/dl/package_esp32_index.json
     ```
   - Go to Tools → Board → Boards Manager
   - Search for "ESP32"
   - Install "ESP32 by Espressif Systems"

3. **Install Required Libraries**
   - Go to Tools → Manage Libraries
   - Install the following libraries:
     - **Adafruit GFX Library** (by Adafruit)
     - **Adafruit SSD1306** (by Adafruit)
     - **U8g2** (by oliver) - Alternative OLED library
   - BLE libraries are included with ESP32 board support

## Hardware Setup

### Wiring Diagram

```
ESP32          Small OLED (0.96")      Large OLED (1.3")
-----          ------------------      -----------------
3.3V     →     VCC                     VCC
GND      →     GND                     GND
GPIO21   →     SDA             and     SDA  (shared I2C bus)
GPIO22   →     SCL             and     SCL  (shared I2C bus)
```

### I2C Addresses
- Small display: 0x3C (typical)
- Large display: 0x3D (may vary - use I2C scanner to verify)

If both displays have the same address, you may need to:
1. Use a different display model with configurable address
2. Use an I2C multiplexer (TCA9548A)
3. Use software I2C on different pins for second display

## Compiling & Uploading

1. Open `notibot/notibot.ino` in Arduino IDE
2. Select board: Tools → Board → ESP32 Arduino → ESP32 Dev Module
3. Select port: Tools → Port → (your ESP32 COM port)
4. Click Upload button or press Ctrl+U

## Testing

### Test I2C Devices
Use this sketch to find I2C addresses of your displays:

```cpp
#include <Wire.h>

void setup() {
  Wire.begin();
  Serial.begin(115200);
  Serial.println("I2C Scanner");
}

void loop() {
  byte count = 0;
  Serial.println("Scanning...");
  for(byte i = 8; i < 120; i++) {
    Wire.beginTransmission(i);
    if(Wire.endTransmission() == 0) {
      Serial.print("Found address: 0x");
      Serial.println(i, HEX);
      count++;
    }
  }
  Serial.print("Found ");
  Serial.print(count);
  Serial.println(" device(s)");
  delay(5000);
}
```

### Serial Monitor
- Open Serial Monitor (Tools → Serial Monitor)
- Set baud rate to 115200
- You should see debug output from the firmware

## Project Structure

```
firmware/
├── notibot/
│   ├── notibot.ino          # Main sketch file
│   ├── config.h             # Configuration constants (to be created)
│   ├── display_manager.h/.cpp   # Display control (to be created)
│   ├── ble_manager.h/.cpp       # BLE communication (to be created)
│   └── notification_parser.h/.cpp  # Data parsing (to be created)
└── README.md                # This file
```

## Development Phases

See [PLAN.md](../docs/PLAN.md) for detailed implementation phases.

### Current Status: Phase 1 - Setup

**Next Steps**:
1. Order parts from SHOPPING_LIST.md
2. Install development environment
3. Test ESP32 board with blink sketch
4. Proceed to Phase 2 - Hardware testing

## Troubleshooting

### Common Issues

**Upload fails / Serial port not detected**
- Install CP210x or CH340 USB drivers (depending on your ESP32 board)
- Try different USB cable
- Press and hold BOOT button while uploading

**Display not working**
- Run I2C scanner to verify addresses
- Check wiring connections
- Verify voltage (use 3.3V, not 5V)
- Try swapping SDA/SCL if display still doesn't work

**Out of memory errors**
- Reduce MAX_NOTIFICATIONS constant
- Optimize string usage
- Use F() macro for string constants to store in flash

**BLE not working**
- Ensure BLE is enabled in Arduino IDE board settings
- Check that you're using ESP32 (not ESP8266)
- Verify phone Bluetooth is enabled

## Resources

- [ESP32 Arduino Core Documentation](https://docs.espressif.com/projects/arduino-esp32/)
- [Adafruit SSD1306 Library Guide](https://learn.adafruit.com/monochrome-oled-breakouts)
- [ESP32 BLE Tutorial](https://randomnerdtutorials.com/esp32-bluetooth-low-energy-ble-arduino-ide/)

## License

TBD
