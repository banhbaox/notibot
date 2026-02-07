# Notibot Shopping List

## Essential Hardware Components

### Microcontroller & Core Electronics

| Item | Quantity | Est. Price | Amazon Search Term | Notes |
|------|----------|------------|-------------------|-------|
| ESP32 Development Board | 1-2 | $10-15 | "ESP32 development board" | Get 2 in case you damage one. Look for ESP32-WROOM or ESP32-DevKitC |
| 0.96" OLED Display (128x64) | 1 | $8-12 | "0.96 inch OLED SSD1306 I2C" | For notification count (small screen) |
| 1.3" OLED Display (128x64) | 1 | $12-18 | "1.3 inch OLED SH1106 I2C" | For notification text (larger screen) |
| Micro USB Cable | 1 | $6-8 | "micro USB cable data sync" | For programming ESP32 (some boards use USB-C, check first) |
| 5V Power Supply | 1 | $8-12 | "5V 2A USB power adapter" | To power the device |
| Breadboard (400 points) | 1 | $6-10 | "solderless breadboard 400" | For prototyping |
| Jumper Wires Kit | 1 | $8-12 | "jumper wires kit male to male" | Get a kit with M-M, M-F, F-F wires |

**Subtotal Core Components**: ~$70-100

### Optional but Recommended

| Item | Quantity | Est. Price | Amazon Search Term | Notes |
|------|----------|------------|-------------------|-------|
| PCB Prototype Board | 1-2 | $10-15 | "PCB prototype board" | For permanent assembly later |
| Soldering Iron Kit | 1 | $20-30 | "soldering iron kit temperature control" | Only if you don't have one |
| Solder Wire | 1 | $8-12 | "rosin core solder wire electronics" | 60/40 or 63/37 tin-lead |
| Enclosure/Project Box | 1 | $10-15 | "electronics project box" | Dimensions ~10x8x5cm, or 3D print your own |
| Multimeter | 1 | $15-25 | "digital multimeter" | Essential for debugging |

**Subtotal Optional**: ~$60-100

### For 3D Printed Enclosure (Optional)

If you want to 3D print a custom case:
- Access to a 3D printer OR
- Use online 3D printing service (we'll design the model later)

## Tools You'll Need (Check what you have)

- [ ] Computer with USB port
- [ ] Arduino IDE or PlatformIO installed
- [ ] Wire strippers/cutters
- [ ] Small Phillips screwdriver
- [ ] USB cable for programming ESP32

## Beginner-Friendly Starter Kits (Alternative)

If you prefer an all-in-one kit:

| Item | Est. Price | Amazon Search Term | Notes |
|------|------------|-------------------|-------|
| ESP32 Starter Kit | $40-60 | "ESP32 starter kit breadboard" | Usually includes ESP32, breadboard, wires, LEDs, resistors |
| Electronics Component Kit | $20-30 | "electronics component kit resistors capacitors" | Good to have extra parts |

## Shopping Strategy

### Budget Options:
- **Minimum to start**: Just get ESP32 board, 2 OLED displays, breadboard, and jumper wires (~$40-50)
- **Add tools as needed**: If you get stuck, you can order additional items

### Recommended First Order:
1. ESP32 Development Board (x2)
2. 0.96" OLED Display (small screen)
3. 1.3" OLED Display (larger screen)
4. Breadboard
5. Jumper wires kit
6. Multimeter (very helpful for troubleshooting)

**First Order Total**: ~$70-85

### Second Order (after testing):
- Soldering equipment
- PCB board for permanent assembly
- Enclosure
- Any additional components you discover you need

## Important Notes

### ESP32 Variants
- **ESP32-WROOM** or **ESP32-DevKitC** are good choices
- Make sure it has **Bluetooth** (most do)
- Some boards use **USB-C** instead of Micro USB - check before ordering cables

### OLED Display Specs
- **Interface**: I2C (easier to use than SPI for beginners)
- **Driver chips**: SSD1306 or SH1106 (both widely supported)
- **Voltage**: 3.3V or 5V compatible
- Make sure to get different sizes for the two screens

### Buying Tips
- Look for "Prime" eligible items for faster shipping
- Check reviews for quality
- Many components are cheaper when bought in packs (2-5 pieces)
- Consider buying from electronics specialty stores like Adafruit or SparkFun for higher quality (though usually more expensive)

## Estimated Total Cost

| Tier | Cost | What You Get |
|------|------|--------------|
| Minimum | $40-50 | Core components only, no tools |
| Recommended | $80-120 | Core + basic tools + extras |
| Complete | $150-200 | Everything including soldering kit, enclosure, backup parts |

## Next Steps After Ordering

1. While waiting for parts, set up the development environment
2. Install Arduino IDE and ESP32 board support
3. Review the firmware development plan
4. Set up the mobile app development environment (Android Studio / Xcode)

---

**Note**: Prices are approximate and based on typical Amazon pricing. Actual costs may vary. Links and specific product recommendations can be added once you're ready to order.
