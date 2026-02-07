# Notibot Shopping List

**NO SOLDERING REQUIRED!** This project uses a breadboard - perfect for beginners.

## Essential Hardware Components (Breadboard Build)

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

### Recommended Tools

| Item | Quantity | Est. Price | Amazon Search Term | Notes |
|------|----------|------------|-------------------|-------|
| Multimeter | 1 | $15-25 | "digital multimeter" | Highly recommended for debugging electrical issues |

**Subtotal Tools**: ~$15-25

## Future Enhancements (Optional - Skip for Now!)

These items are for making your project permanent and polished. **Not needed for a fully working Notibot!**

### For Permanent Assembly (Requires Soldering)

| Item | Quantity | Est. Price | Amazon Search Term | Notes |
|------|----------|------------|-------------------|-------|
| Soldering Iron Kit | 1 | $20-30 | "soldering iron kit temperature control" | Only if you want permanent assembly |
| Solder Wire | 1 | $8-12 | "rosin core solder wire electronics" | 60/40 or 63/37 tin-lead |
| PCB Prototype Board | 1-2 | $10-15 | "PCB prototype board" | For permanent soldered assembly |
| Enclosure/Project Box | 1 | $10-15 | "electronics project box" | Dimensions ~10x8x5cm, or 3D print your own |

**Subtotal (Future)**: ~$50-75

### For 3D Printed Enclosure (Optional)

If you want to 3D print a custom case later:
- Access to a 3D printer OR
- Use online 3D printing service (we'll design the model later)

## Basic Tools You'll Need (Check what you have)

- [ ] Computer with USB port
- [ ] Arduino IDE (we'll install this together)
- [ ] USB cable for programming ESP32

**That's it!** No wire strippers, no screwdrivers, no soldering iron needed.

## Beginner-Friendly Starter Kits (Alternative)

If you prefer an all-in-one kit:

| Item | Est. Price | Amazon Search Term | Notes |
|------|------------|-------------------|-------|
| ESP32 Starter Kit | $40-60 | "ESP32 starter kit breadboard" | Usually includes ESP32, breadboard, wires, LEDs, resistors |
| Electronics Component Kit | $20-30 | "electronics component kit resistors capacitors" | Good to have extra parts |

## Shopping Strategy

### Budget Options:
- **Minimum to start**: Just get ESP32 board, 2 OLED displays, breadboard, and jumper wires (~$40-50)
- **Recommended**: Add multimeter for easier debugging (~$55-75 total)

### Recommended First Order (Everything You Need!):
1. ESP32 Development Board (x2)
2. 0.96" OLED Display (small screen)
3. 1.3" OLED Display (larger screen)
4. Breadboard
5. Jumper wires kit
6. Multimeter (highly recommended for troubleshooting)

**Total for Complete Working Project**: ~$55-85

**That's it!** You don't need anything else to build a fully functional Notibot.

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

## Estimated Total Cost (Breadboard Build - No Soldering!)

| Tier | Cost | What You Get |
|------|------|--------------|
| Minimum | $40-50 | Core components only (ESP32, displays, breadboard, wires) - **Fully functional!** |
| Recommended | $55-75 | Core + multimeter - **Complete working project!** |
| Future Upgrade | +$50-75 | Soldering equipment + PCB for permanent assembly (optional, much later) |

## Next Steps After Ordering

1. While waiting for parts, set up the development environment
2. Install Arduino IDE and ESP32 board support
3. Review the firmware development plan
4. Set up the mobile app development environment (Android Studio / Xcode)

---

**Note**: Prices are approximate and based on typical Amazon pricing. Actual costs may vary. Links and specific product recommendations can be added once you're ready to order.
