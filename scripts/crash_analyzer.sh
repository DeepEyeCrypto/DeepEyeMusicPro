#!/usr/bin/env bash
# ══════════════════════════════════════════════════════════════════
# DeepEyeMusicPro — Automated Crash Analyzer
# Usage: ./scripts/crash_analyzer.sh [package_name]
# ══════════════════════════════════════════════════════════════════

PACKAGE=${1:-"com.deepeye.musicpro"}
OUTPUT_DIR="crash_reports_$(date +%s)"
mkdir -p "$OUTPUT_DIR"

echo "🚨 Starting Emergency Data Collection for $PACKAGE..."

# 1. Capture Main Logcat (Java Exceptions)
echo "[1/5] Dumping Logcat (FATAL EXCEPTION / MediaCodec / AudioTrack)..."
adb logcat -d | grep -E "FATAL EXCEPTION|AndroidRuntime|signal |SIGSEGV|SIGABRT|ANR|DeepEye|ExoPlayerImpl|AudioTrack|MediaCodec|JNI DETECTED|StrictMode|Choreographer" > "$OUTPUT_DIR/crash_full.log"

# 2. Capture Dropbox (System-level ANRs and Crashes)
echo "[2/5] Extracting System Dropbox (ANRs)..."
adb shell dumpsys dropbox --print | grep -A 50 "system_app_crash\|system_app_anr" > "$OUTPUT_DIR/dropbox_crashes.log"

# 3. Capture Native Tombstones (signal 11, etc)
echo "[3/5] Extracting Native Tombstones..."
adb shell cat /data/tombstones/tombstone_* > "$OUTPUT_DIR/tombstones.log" 2>/dev/null || echo "No root access to tombstones or dir empty."

# 4. Capture Memory Info (Low Memory Kills)
echo "[4/5] Capturing Memory State..."
adb shell dumpsys meminfo "$PACKAGE" > "$OUTPUT_DIR/meminfo_crash.log"

# 5. Capture Activity State
echo "[5/5] Capturing Activity State..."
adb shell dumpsys activity "$PACKAGE" > "$OUTPUT_DIR/activity_state.log"

echo "✅ Data collection complete. Analyzing..."
echo "--------------------------------------------------------"

# Quick Triage
JAVA_CRASHES=$(grep -c "FATAL EXCEPTION" "$OUTPUT_DIR/crash_full.log" || true)
NATIVE_CRASHES=$(grep -c "signal " "$OUTPUT_DIR/crash_full.log" || true)
ANRS=$(grep -c "ANR in $PACKAGE" "$OUTPUT_DIR/crash_full.log" || true)

echo "📊 QUICK TRIAGE RESULT:"
echo "- Java Fatal Exceptions: $JAVA_CRASHES"
echo "- Native Signals (C++): $NATIVE_CRASHES"
echo "- Application Not Responding (ANR): $ANRS"
echo "--------------------------------------------------------"
echo "📂 All logs saved in: ./$OUTPUT_DIR/"
