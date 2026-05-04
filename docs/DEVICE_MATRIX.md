# DeepEyeMusicPro Compatibility Matrix

| Device Category | Model (Example) | OS Version | Status | Notes |
|---|---|---|---|---|
| **Reference** | Pixel 8 | Android 15 | ✅ PASS | Baseline performance. |
| **Vendor (Samsung)** | Galaxy S23 | One UI 6.1 | ✅ PASS | Handle AudioTrack latency variations. |
| **Aggressive (Xiaomi)** | Redmi Note 13 | MIUI 14 | ⚠️ WARN | Battery saver kills background DSP. |
| **Low-End** | Moto G Play | Android 11 | ⚠️ WARN | CPU load high (15% @ 48kHz). |
| **Legacy** | Pixel 4a | Android 10 | ✅ PASS | Native .so ABI (armeabi-v7a) tested. |

## 🏁 Verification Checklist

- [ ] Native .so loads (all ABIs)
- [ ] AudioTrack initialization success
- [ ] No ANR in first 5 minutes
- [ ] Background playback survives 30 minutes
- [ ] Bluetooth stable (A2DP)
- [ ] Cast discovery works
- [ ] No thermal throttling in 15-min playback
