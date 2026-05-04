# DeepEye ADB Debug Quick Reference

## One-Liners

### Install & Launch
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk && adb shell am start -n com.deepeye.musicpro/.MainActivity
```

### Watch Crashes
```bash
adb logcat -c && adb logcat | grep -E "FATAL|signal |ANR |DeepEye"
```

### Memory Snapshot
```bash
adb shell dumpsys meminfo com.deepeye.musicpro | grep -E "TOTAL PSS|Native Heap|Dalvik Heap"
```

### CPU Check
```bash
adb shell top -p $(adb shell pidof com.deepeye.musicpro) -n 1
```

### Screenshot
```bash
adb shell screencap -p /sdcard/ss.png && adb pull /sdcard/ss.png
```

### Screen Record (30s)
```bash
adb shell screenrecord /sdcard/rec.mp4 & sleep 30 && kill %1 && adb pull /sdcard/rec.mp4
```

### Kill & Restart
```bash
adb shell am force-stop com.deepeye.musicpro && sleep 1 && adb shell am start -n com.deepeye.musicpro/.MainActivity
```

### Clear Data (nuclear)
```bash
adb shell pm clear com.deepeye.musicpro
```

## Log Tags Dictionary

| Tag | Meaning | Watch For |
|-----|---------|-----------|
| `DeepEyeDSP` | Native engine events | init success, process errors |
| `DeepEyeSafety` | Security events | buffer overflow attempts |
| `AudioSecurity` | Kotlin security | invalid parameter logs |
| `ExoPlayerImplInternal` | Playback engine | player errors, source errors |
| `AudioTrack` | Audio output | underruns, init failures |
| `MediaCodec` | Decoder | codec errors, release failures |
| `FATAL EXCEPTION` | Java crash | stack trace, component |
| `signal 11` | Native SIGSEGV | address, module, PC |
| `ANR` | Not responding | traces.txt, main thread |
| `Choreographer` | UI frame drops | "Skipped X frames" |

## File Locations

| Data | Path |
|------|------|
| Crash logs | `/data/anr/traces.txt` |
| Native tombstones | `/data/tombstones/tombstone_*` |
| App data | `/data/data/com.deepeye.musicpro/` |
| SDCard (accessible) | `/sdcard/` or `/storage/emulated/0/` |
| Logcat dump | `adb logcat -d` |

## NDK Debug

```bash
# Symbolicate native crash
$ANDROID_NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/llvm-addr2line \
  -e app/build/intermediates/cmake/debug/obj/arm64-v8a/libdeepeye_dsp.so \
  0x<PC_ADDRESS_FROM_LOG>
```
