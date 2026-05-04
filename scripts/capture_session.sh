#!/bin/bash
# capture_session.sh
SESSION_ID=$(date +%Y%m%d_%H%M%S)
mkdir -p "debug_sessions/$SESSION_ID"
adb logcat -c
adb logcat -v threadtime > "debug_sessions/$SESSION_ID/full_logcat.log" &
LOG_PID=$!

echo "Recording session $SESSION_ID..."
echo "Press Enter to stop and save..."
read

kill $LOG_PID
adb shell dumpsys meminfo com.deepeye.musicpro > "debug_sessions/$SESSION_ID/meminfo.log"
adb shell dumpsys activity com.deepeye.musicpro > "debug_sessions/$SESSION_ID/activity.log"
echo "Saved to debug_sessions/$SESSION_ID/"
