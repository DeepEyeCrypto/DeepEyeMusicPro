#!/bin/bash
# DeepEyeMusicPro — Automated Stability & Stress Test
# Duration: 30 minutes (customizable)

PACKAGE="com.deepeye.musicpro.debug"
DURATION_SEC=1800
LOG_FILE="stability_test_$(date +%Y%m%d_%H%M%S).log"

echo "🧪 Starting DeepEye Stability Test Engine..."
echo "📊 Logging to: $LOG_FILE"

# 1. Start Capture
adb logcat -c
adb logcat -v time | grep -E "DeepEye|AudioFlinger|ExoPlayer|ANR|FATAL|DEBUG" > "$LOG_FILE" &
LOGCAT_PID=$!

# 2. Stress Loop: Playback Interaction
echo "🌀 Starting stress loop (Play/Pause/Seek/Toggle)..."
END_TIME=$(( $(date +%s) + DURATION_SEC ))

while [ $(date +%s) -lt $END_TIME ]; do
    # Toggle Play/Pause
    adb shell input keyevent 85
    sleep 2
    
    # Random Seek
    adb shell am broadcast -a "$PACKAGE.action.SEEK_STRESS" --ei pos $((RANDOM % 100000))
    sleep 1
    
    # Toggle DSP
    adb shell am broadcast -a "$PACKAGE.action.TOGGLE_DSP"
    sleep 3
    
    # Check if process alive
    if ! adb shell pidof $PACKAGE > /dev/null; then
        echo "💥 CRASH DETECTED! Process died."
        break
    fi
    
    # Memory Sample
    echo "--- Memory Snapshot ---" >> "$LOG_FILE"
    adb shell dumpsys meminfo $PACKAGE | grep "TOTAL" >> "$LOG_FILE"
done

# 3. Cleanup
kill $LOGCAT_PID
echo "🏁 Test complete. Analyzing results..."

# 4. Simple Triage
CRASHES=$(grep -c "FATAL EXCEPTION" "$LOG_FILE")
ANRS=$(grep -c "ANR" "$LOG_FILE")
NATIVE_CRASHES=$(grep -c "signal 11 (SIGSEGV)" "$LOG_FILE")

echo "-----------------------------------"
echo "Java Crashes: $CRASHES"
echo "ANRs: $ANRS"
echo "Native Crashes: $NATIVE_CRASHES"
echo "-----------------------------------"

if [ $CRASHES -eq 0 ] && [ $ANRS -eq 0 ] && [ $NATIVE_CRASHES -eq 0 ]; then
    echo "✅ PASS: Stability criteria met."
else
    echo "❌ FAIL: Stability issues found. Check $LOG_FILE"
fi
