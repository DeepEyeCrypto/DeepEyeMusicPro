# 🎧 DeepEyeMusicPro

[![Android CI](https://github.com/deepeye/DeepEyeMusicPro/actions/workflows/android.yml/badge.svg)](https://github.com/deepeye/DeepEyeMusicPro/actions/workflows/android.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org)
[![Downloads](https://img.shields.io/github/v/release/deepeye/DeepEyeMusicPro)](https://github.com/deepeye/DeepEyeMusicPro/releases)

> **A native DSP-powered Android music player with real-time visualization, deep bass enhancement, and production-grade audio processing.**

Built with **C++ DSP engine**, **Media3 ExoPlayer**, **Jetpack Compose**, and **lock-free audio pipelines** — designed for audiophiles and hackers alike.

---

## 🚀 Features

| Feature | Description |
|---------|-------------|
| 🔊 **Native DSP Engine** | Custom C++ audio processor with tube warmth, EQ, compressor, limiter, sub-bass synthesis |
| 🎨 **Real-time Visualizer** | Frequency bars, waveform oscilloscope, and circular spectrum — all in Compose Canvas |
| 🛡️ **Privacy-First** | Zero network leakage. URL scrubbing. No analytics. No tracking. |
| 🎛️ **Pro DSP Chain** | Preamp → Tube Saturation → 10-Band EQ → Compressor → Limiter → Sub Synth |
| 📡 **Bluetooth / Cast** | Auto route detection with safety limiting per output device |
| ⚡ **Low Latency** | Lock-free ring buffers. Zero-allocation audio path. < 50ms DSP latency |
| 🔒 **Security Hardened** | ASAN-verified native code. Buffer overflow protection. Denormal FTZ. |

---

## 📸 Screenshots

| Now Playing | Visualizer | EQ / DSP |
|-------------|------------|----------|
| ![Now Playing](docs/screenshots/nowplaying.png) | ![Visualizer](docs/screenshots/visualizer.png) | ![EQ](docs/screenshots/eq.png) |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────┐
│              Compose UI Layer                │
│  (NowPlaying | Library | Visualizer | EQ)     │
├─────────────────────────────────────────────┤
│           Media3 ExoPlayer                     │
│    ┌──────────────┐  ┌──────────────┐       │
│    │ AudioProcessor│→ │  Native DSP   │       │
│    │  (Java/Kotlin)│  │  (C++/JNI)    │       │
│    └──────────────┘  └──────────────┘       │
├─────────────────────────────────────────────┤
│         AudioTrack → Bluetooth / Cast        │
└─────────────────────────────────────────────┘
```

### Native Audio Pipeline

- **Input**: Float PCM from Media3 decoder
- **Process**: Deinterleave → DSP Engine → Interleave
- **DSP Stages**: Preamp → Tube → EQ (10-band) → Compressor → Limiter → Sub Synth
- **Output**: Float PCM to AudioTrack
- **Safety**: Buffer bounds validation, NaN/Inf sanitization, denormal flush-to-zero

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| UI | Jetpack Compose, Material 3, Canvas |
| Audio Engine | C++17, JNI, pthread, lock-free ring buffers |
| Playback | Media3 ExoPlayer, AudioProcessor API |
| DSP | Custom IIR Biquad, Compressor, Limiter, Sub Synthesizer |
| Visualizer | Native FFT (Kiss FFT), Compose Canvas |
| Build | CMake, NDK r25+, Gradle 8.2 |
| CI/CD | GitHub Actions, Fastlane |

---

## 📦 Download

Get the latest APK from [GitHub Releases](https://github.com/deepeye/DeepEyeMusicPro/releases).

```bash
# Or build from source
./gradlew assembleRelease
# Signed APK: app/build/outputs/apk/release/app-release.apk
```

### F-Droid

[//]: # (F-Droid badge will appear here once merged)
Coming soon — [open an issue](https://github.com/deepeye/DeepEyeMusicPro/issues) to help!

---

## 🧑💻 Building from Source

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34
- NDK r25c or newer
- CMake 3.22.1+

### Steps

```bash
# 1. Clone
git clone https://github.com/deepeye/DeepEyeMusicPro.git
cd DeepEyeMusicPro

# 2. Build native + APK
./gradlew assembleDebug

# 3. Install to device
adb install app/build/outputs/apk/debug/app-debug.apk

# 4. Run tests
./gradlew test
./gradlew connectedAndroidTest
```

### Release Build

```bash
# Create signing config from template
cp signing.properties.template app/signing.properties
# Edit with your keystore details

./scripts/sign_release.sh
./scripts/verify_release.sh
```

---

## 🧪 Testing

| Test | Command |
|------|---------|
| Unit Tests | `./gradlew test` |
| Integration Tests | `./gradlew connectedAndroidTest` |
| Stability Stress | `./tools/deep_test.sh` |
| Native Fuzzing | `cd app/src/main/cpp && cmake -DCMAKE_BUILD_TYPE=Fuzz && make fuzz_dsp && ./fuzz_dsp` |
| Crash Analysis | `./scripts/crash_analyzer.sh` |

---

## 🔒 Security

DeepEyeMusicPro has undergone a full security audit with native code hardening.

- [Security Audit Report](docs/SECURITY_AUDIT_REPORT.md)
- [Crash Playbook](docs/CRASH_PLAYBOOK.md)
- Native ASAN/UBSAN verified
- Buffer overflow protection on all JNI boundaries
- Denormal flush-to-zero (no CPU DoS)
- Privacy-zero leakage architecture

**Responsible Disclosure**: If you find a vulnerability, please open a [security advisory](https://github.com/deepeye/DeepEyeMusicPro/security/advisories/new) instead of a public issue.

---

## 🤝 Contributing

We welcome contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

Quick start:
```bash
# Fork, clone, branch
git checkout -b feature/my-awesome-feature

# Code, test, commit
./gradlew test
./gradlew connectedAndroidTest

# Push and open PR
git push origin feature/my-awesome-feature
```

### Areas We Need Help

- [ ] F-Droid packaging metadata
- [ ] Additional visualizer modes (particle, fluid)
- [ ] Bluetooth LDAC/LC3 codec support
- [ ] Android Auto / Automotive OS integration
- [ ] Translations (i18n)
- [ ] Wear OS companion app

---

## 📜 License

```
MIT License

Copyright (c) 2026 DeepEye

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND...
```

See [LICENSE](LICENSE) for full text.

---

## 🙏 Acknowledgements

- [Media3 ExoPlayer](https://github.com/androidx/media) — Android media playback
- [Jetpack Compose](https://developer.android.com/jetpack/compose) — Modern Android UI
- [Kiss FFT](https://github.com/mborgerding/kissfft) — Fast Fourier Transform
- [Material Design 3](https://m3.material.io/) — Design system

---

## 📬 Contact

- **Issues**: [GitHub Issues](https://github.com/deepeye/DeepEyeMusicPro/issues)
- **Security**: [Security Advisories](https://github.com/deepeye/DeepEyeMusicPro/security)
- **Discussions**: [GitHub Discussions](https://github.com/deepeye/DeepEyeMusicPro/discussions)

---

<p align="center">
  <b>Made with 🔥 in Delhi, India</b><br>
  <i>Hack the audio.</i>
</p>
