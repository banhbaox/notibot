# Notibot Quick Start Guide

Welcome to Notibot! This guide will help you get started building your physical notification display.

**NO SOLDERING REQUIRED!** This is a beginner-friendly breadboard project.

## What You'll Build

A desktop device with two screens:
- **Small screen**: Shows notification count
- **Large screen**: Shows notification content

Connects to your phone via Bluetooth!

**Everything connects on a breadboard** - no soldering, no complicated assembly!

## Step 1: Order Parts (5 minutes)

Open [SHOPPING_LIST.md](SHOPPING_LIST.md) and order:

**Everything you need** (~$55-75):
- ESP32 Development Board (x2 - backup recommended)
- 0.96" OLED Display (small screen)
- 1.3" OLED Display (large screen)
- Breadboard
- Jumper wire kit
- Micro USB cable
- Multimeter (highly recommended for debugging)

**That's it!** No soldering iron, no tools, no complicated equipment.

## Step 2: Setup Software (While Waiting for Parts)

### Install Arduino IDE
1. Download from https://www.arduino.cc/en/software
2. Install on your computer

### Add ESP32 Support
1. Open Arduino IDE
2. Go to **File → Preferences**
3. Add this URL to "Additional Board Manager URLs":
   ```
   https://dl.espressif.com/dl/package_esp32_index.json
   ```
4. Go to **Tools → Board → Boards Manager**
5. Search "ESP32"
6. Install "ESP32 by Espressif Systems"

### Install Libraries
1. Go to **Tools → Manage Libraries**
2. Install these libraries:
   - Adafruit GFX Library
   - Adafruit SSD1306
   - U8g2

## Step 3: Test ESP32 (When Parts Arrive)

1. **Connect ESP32 to computer** via USB
2. **Select board**: Tools → Board → ESP32 Dev Module
3. **Select port**: Tools → Port → (your ESP32 COM port)
4. **Upload test sketch**:
   - File → Examples → 01.Basics → Blink
   - Click Upload button
5. **Verify**: Onboard LED should blink

If this works, your ESP32 is ready!

## Step 4: Wire Up Displays

Follow the wiring diagram in [firmware/README.md](firmware/README.md):

```
ESP32 Pin    →    Both OLED Displays
---------          -------------------
3.3V        →      VCC (on both)
GND         →      GND (on both)
GPIO21      →      SDA (on both)
GPIO22      →      SCL (on both)
```

## Step 5: Test Displays

1. **Find I2C addresses**:
   - Use I2C scanner sketch from firmware/README.md
   - Note the addresses (typically 0x3C and 0x3D)

2. **Test each display**:
   - File → Examples → Adafruit SSD1306 → ssd1306_128x64_i2c
   - Modify address if needed
   - Upload and verify display works

## Step 6: Follow Development Plan

Now you're ready to start building! Follow [docs/PLAN.md](docs/PLAN.md):

- **Phase 2**: Complete hardware testing (you're almost done!)
- **Phase 3**: Build firmware
- **Phase 4**: Build Android app
- **Phase 5**: Build iOS app
- **Phase 6-8**: Testing, assembly, documentation

## Syncing Between Computers

### Initial Setup (on first computer)
```bash
cd notibot
git add .
git commit -m "Initial Notibot project setup"

# Create GitHub repository, then:
git remote add origin https://github.com/YOUR_USERNAME/notibot.git
git push -u origin main
```

### On Other Computer (laptop/workstation)
```bash
git clone https://github.com/YOUR_USERNAME/notibot.git
cd notibot
```

### Keeping in Sync
```bash
# Before starting work:
git pull

# After making changes:
git add .
git commit -m "Description of what you did"
git push
```

## Project Structure Reminder

```
notibot/
├── README.md              ← Project overview
├── SHOPPING_LIST.md       ← What to buy
├── QUICKSTART.md          ← You are here!
├── docs/
│   ├── REQUIREMENTS.md    ← What we're building
│   └── PLAN.md           ← Step-by-step plan
├── firmware/             ← ESP32 code
├── mobile-apps/
│   ├── android/          ← Android app
│   └── ios/              ← iOS app
├── hardware/             ← Wiring diagrams
└── 3d-models/            ← Enclosure design
```

## Need Help?

### Common Issues

**ESP32 not detected:**
- Install CH340 or CP210x USB driver
- Try different USB cable
- Press BOOT button while uploading

**Display not working:**
- Double-check wiring (especially SDA/SCL)
- Verify voltage (use 3.3V)
- Run I2C scanner to confirm addresses

**Out of memory:**
- This is normal as project grows
- We'll optimize later in Phase 3

### Resources

- **ESP32 Tutorials**: https://randomnerdtutorials.com/projects-esp32/
- **Arduino Forums**: https://forum.arduino.cc/
- **OLED Guide**: https://learn.adafruit.com/monochrome-oled-breakouts

## What's Next?

Once parts arrive:
1. Complete Phase 2 (hardware testing)
2. Start Phase 3 (firmware development)
3. Keep everything synced via git

## Estimated Timeline

- **Phase 1-2**: 1 week (setup + testing)
- **Phase 3-5**: 3-4 weeks (coding)
- **Phase 6-8**: 2-3 weeks (testing + assembly)

**Total**: 6-8 weeks working part-time

Remember: This is a learning project! Take your time, test each step, and don't hesitate to debug issues.

## Track Your Progress

Use the checklist in [docs/PLAN.md](docs/PLAN.md) to track what you've completed. Update it as you go!

---

**Ready?** Order those parts and let's build Notibot!
