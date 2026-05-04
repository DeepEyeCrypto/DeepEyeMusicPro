# DeepEyeMusicPro — Release Guide

## Prerequisites

- [ ] Android Studio with SDK 35 (API 35)
- [ ] Android NDK (for native C++ DSP build)
- [ ] JDK 17
- [ ] `apksigner` and `zipalign` (bundled with Android SDK Build Tools)
- [ ] Release keystore (generated once, kept offline)
- [ ] Google Play Console developer account
- [ ] Fastlane (optional, for automated deployment)

---

## 1. Generate Release Keystore (One-Time)

```bash
keytool -genkey -v \
    -keystore deepeye-release.keystore \
    -alias deepeye \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000 \
    -storepass YOUR_STORE_PASSWORD \
    -keypass YOUR_KEY_PASSWORD
```

> ⚠️ **CRITICAL**: Back up this keystore file offline. If lost, you cannot update the app on Play Store.

## 2. Configure Signing

Create `signing.properties` in the project root (**DO NOT** commit this file):

```properties
STORE_FILE=/absolute/path/to/deepeye-release.keystore
STORE_PASSWORD=your_store_password
KEY_ALIAS=deepeye
KEY_PASSWORD=your_key_password
```

Or pass via Gradle properties:
```bash
./gradlew assembleRelease \
    -PDEEPEYE_RELEASE_STORE_FILE=/path/to/keystore \
    -PDEEPEYE_RELEASE_STORE_PASSWORD=password \
    -PDEEPEYE_RELEASE_KEY_ALIAS=deepeye \
    -PDEEPEYE_RELEASE_KEY_PASSWORD=password
```

## 3. Build Release

### Option A: Automated (recommended)
```bash
chmod +x scripts/sign_release.sh
./scripts/sign_release.sh
```

### Option B: Manual
```bash
# AAB (for Play Store)
./gradlew bundleRelease

# APK (for direct distribution)
./gradlew assembleRelease
```

## 4. Verify Release

```bash
chmod +x scripts/verify_release.sh
./scripts/verify_release.sh
```

This checks:
- APK/AAB size limits
- Native library presence across ABIs
- APK signature and alignment
- Debug flag absence
- ProGuard mapping generation
- Target SDK compliance

## 5. Test on Device

```bash
# Install release APK
adb install app/build/outputs/apk/release/app-release.apk

# Cold start benchmark
adb shell am start -W -n com.deepeye.musicpro/.ui.MainActivity

# Memory baseline
adb shell dumpsys meminfo com.deepeye.musicpro | grep "TOTAL PSS"

# CPU during playback (with DSP)
adb shell top -p $(adb shell pidof com.deepeye.musicpro) -n 5
```

### Target Benchmarks
| Metric | Target | Critical |
|--------|--------|----------|
| Cold start | < 500ms | < 1000ms |
| Memory (idle) | < 80MB PSS | < 120MB PSS |
| CPU (playback + DSP) | < 25% | < 40% |
| APK size | < 30MB | < 50MB |

## 6. Deploy to Play Store

### Option A: Fastlane (recommended)
```bash
# Internal testing (first)
fastlane android internal

# Beta testing
fastlane android beta

# Production (5% staged rollout)
fastlane android production

# Promote to 100%
fastlane android promote_full
```

### Option B: Manual Upload
1. Open [Google Play Console](https://play.google.com/console)
2. Select DeepEyeMusicPro
3. Go to Release → Testing → Internal testing
4. Upload `app-release.aab`
5. Upload `mapping.txt` (under App bundle explorer → Download tab)
6. Upload native debug symbols ZIP
7. Fill release notes
8. Submit for review

## 7. Rollout Strategy

| Phase | Track | Rollout | Duration | Gate |
|-------|-------|---------|----------|------|
| 1 | Internal | 100% | 2 days | No crashes in 24h |
| 2 | Closed Beta | 100% | 3 days | < 0.5% crash rate |
| 3 | Production | 5% | 2 days | Monitor ANR/crash metrics |
| 4 | Production | 20% | 2 days | Stable metrics |
| 5 | Production | 100% | — | All clear |

## 8. Rollback

If critical issues are found post-release:

1. **Halt rollout**: Play Console → Release → Halt
2. **Hotfix**: Bump `versionCode`, fix issue, rebuild
3. **Expedited review**: Upload fixed AAB, request expedited review
4. **Resume**: New staged rollout from 5%

## 9. Post-Release Checklist

- [ ] ProGuard mapping uploaded to Play Console
- [ ] Native debug symbols uploaded
- [ ] Crash monitoring verified (Crashlytics / Play Console)
- [ ] ANR rate < 0.47% (Play Console threshold)
- [ ] Crash rate < 1.09% (Play Console threshold)
- [ ] Performance metrics within targets
- [ ] User reviews monitored for first 48 hours
- [ ] Previous release AAB/APK archived locally

## 10. GitHub Secrets Setup (for CI/CD)

| Secret Name | Value |
|-------------|-------|
| `SIGNING_KEY_BASE64` | Base64-encoded keystore file |
| `SIGNING_KEY_PASSWORD` | Keystore password |
| `SIGNING_KEY_ALIAS` | Key alias (e.g., `deepeye`) |
| `SIGNING_KEY_ALIAS_PASSWORD` | Key password |
| `PLAY_STORE_JSON_KEY_BASE64` | Base64-encoded Play Console service account JSON |

To base64-encode your keystore:
```bash
base64 -i deepeye-release.keystore | tr -d '\n' > keystore_base64.txt
```

---

**Remember:** Always test the release build on a real device before uploading to Play Store. The release build (R8/ProGuard) can behave differently from debug.
