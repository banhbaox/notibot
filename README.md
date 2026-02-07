# Notibot

**🔌 NO SOLDERING REQUIRED | 🍞 Breadboard Build | 👨‍🎓 Beginner Friendly**

A physical desktop notification display toy with dual screens that shows your phone notifications in real-time via Bluetooth.

## Overview

Notibot is a compact desktop device that displays:
- **Small screen**: Current notification count
- **Large screen**: Scrolling notification text content

The device connects to both Android and iOS phones via Bluetooth Low Energy (BLE).

## Project Status

**Phase**: Planning & Parts Ordering
**Hardware Platform**: ESP32
**Supported Platforms**: Android & iOS

## Features

- **No soldering required** - Everything on breadboard
- Real-time notification sync from smartphone
- Dual-screen display (count + content)
- Bluetooth Low Energy connectivity
- Support for both Android and iOS
- Low power consumption
- Compact desktop form factor
- Beginner-friendly project (~$55-75 total cost)

## Project Structure

```
notibot/
├── firmware/          # ESP32 firmware code
├── mobile-apps/       # Android & iOS companion apps
│   ├── android/      # Android app (Kotlin)
│   └── ios/          # iOS app (Swift)
├── hardware/         # Schematics and wiring diagrams
├── 3d-models/        # 3D printable enclosure files
└── docs/             # Documentation
```

## Getting Started

### Prerequisites

See [SHOPPING_LIST.md](SHOPPING_LIST.md) for all required parts and tools.

### Setup Instructions

**Coming soon** - Detailed setup instructions will be added as we develop the project.

## Documentation

- **[Quick Start Guide](QUICKSTART.md)** - Start here! Get up and running fast
- [Shopping List](SHOPPING_LIST.md) - All parts needed (~$55-75, no soldering!)
- [Implementation Plan](docs/PLAN.md) - 6-week development roadmap
- [Requirements](docs/REQUIREMENTS.md) - Detailed feature requirements
- [Hardware Guide](hardware/README.md) - Wiring diagrams (coming soon)

## Tech Stack

- **Assembly**: Breadboard (no soldering!)
- **Microcontroller**: ESP32 (BLE + WiFi)
- **Displays**:
  - Small OLED (e.g., 0.96" 128x64 SSD1306)
  - Larger OLED (e.g., 1.3" or 1.5" 128x64 SH1106)
- **Connection**: Jumper wires (plug and play)
- **Android App**: Kotlin with BLE library
- **iOS App**: Swift with CoreBluetooth
- **Firmware**: Arduino framework (C++)

## License

TBD

## Author

Created with Claude Code
