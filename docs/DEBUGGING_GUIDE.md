# DeepEyeMusicPro Debugging & Stability Manual

## 🛠 Setup Environment
1. Ensure `ANDROID_HOME` is set.
2. Connect device via USB or WiFi ADB.
3. Run `./tools/deploy.sh` to install and grant permissions.

## 📊 Monitoring Logs
Use the following filter to watch the entire audio pipeline:
```bash
adb logcat | grep -E "DeepEye|AudioFlinger|ExoPlayer|ANR|FATAL|DEBUG"
```

## 🧪 Stability Testing
Run the 30-minute stress test:
```bash
./tools/deep_test.sh
```

## 💥 Handling Crashes

### Java Crash
1. Get the stack trace: `adb logcat -d | grep -A 20 "FATAL EXCEPTION"`
2. If it's in `DSPAudioProcessor.kt`, verify buffer `flip()`/`clear()` logic.

### Native Crash (SIGSEGV)
1. Capture the PC address from logcat.
2. Run triage: `./tools/crash_triage.sh current_log.txt`
3. If the crash is in `Biquad::process`, check for denormal handling or NaN input.

### ANR (Application Not Responding)
1. Check `ANRDetector.kt` logs in logcat (`TAG: ANRDetector`).
2. Pull traces: `adb shell bugreport > bugreport.zip`
3. Analyze `FS/data/anr/traces.txt`. Look for `NativeDSP` methods on the `main` thread.

## 🚀 Performance Profiling

### Memory
- Check for native leaks: `adb shell dumpsys meminfo com.deepeye.musicpro`
- Look at `Native Heap`. If it grows indefinitely during playback, check `DSPEngine` destructor.

### CPU
- Monitor real-time load: `adb shell top -p $(pidof com.deepeye.musicpro)`
- If DSP load is > 20%, verify `-ffast-math` is enabled in `CMakeLists.txt`.

## 🔋 Battery & Thermal
- Force Doze mode: `adb shell dumpsys deviceidle force-idle`
- Verify playback continues via Foreground Service.
- Check thermal throttling: `adb shell dumpsys thermalservice`
