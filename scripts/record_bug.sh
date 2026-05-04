#!/bin/bash
# record_bug.sh
OUTPUT="bug_record_$(date +%s).mp4"
echo "Recording screen for 30s... (Ctrl+C to stop early)"
adb shell screenrecord /sdcard/$OUTPUT &
RECORD_PID=$!
sleep 30
kill $RECORD_PID 2>/dev/null
adb pull /sdcard/$OUTPUT .
echo "Saved: $OUTPUT"
