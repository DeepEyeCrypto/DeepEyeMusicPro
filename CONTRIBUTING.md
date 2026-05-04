# Contributing to DeepEyeMusicPro

Thank you for your interest! This project is built by hackers, for hackers.

## 🚀 Quick Start

1. **Fork** the repo
2. **Clone** your fork: `git clone https://github.com/YOU/DeepEyeMusicPro.git`
3. **Branch**: `git checkout -b feature/your-feature`
4. **Code**, **test**, **commit**
5. **Push** and open a **Pull Request**

## 🏗️ Development Setup

### Requirements
- Android Studio Hedgehog+
- JDK 17
- NDK r25c+
- CMake 3.22.1+

### Build
```bash
./gradlew assembleDebug
```

### Test Before PR
```bash
./gradlew test                          # Unit tests
./gradlew connectedAndroidTest          # Instrumentation
./tools/deep_test.sh DURATION_SEC=120   # Stability stress test
```

## 📝 Code Style

- **Kotlin**: Official Kotlin style guide (ktlint)
- **C++**: LLVM style, 4-space indent
- **Commit messages**: Conventional commits (`feat:`, `fix:`, `security:`, `perf:`)

## 🐛 Reporting Bugs

Use the [Bug Report](.github/ISSUE_TEMPLATE/bug_report.md) template. Include:
- Device model and Android version
- Steps to reproduce
- Logcat output (`./scripts/crash_analyzer.sh`)
- Expected vs actual behavior

## 💡 Feature Requests

Use the [Feature Request](.github/ISSUE_TEMPLATE/feature_request.md) template.

## 🔒 Security

Do **not** open public issues for security bugs. Use [Security Advisories](https://github.com/deepeye/DeepEyeMusicPro/security/advisories/new).

## 🎯 Priority Areas

- F-Droid metadata and reproducible builds
- Additional visualizer algorithms
- Bluetooth codec support (LDAC, aptX, LC3)
- Android Auto integration
- Translations (we need i18n!)
- Wear OS companion

## 🙏 Thank You!

Every PR, bug report, and feature request makes DeepEyeMusicPro better.
