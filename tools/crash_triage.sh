#!/bin/bash
# DeepEyeMusicPro — Crash & Log Triage Tool
# Symbolicates native crashes and parses Java stack traces.

LOG_FILE=$1
LIB_PATH="app/build/intermediates/cmake/debug/obj/arm64-v8a/libdeepeye_dsp.so"

if [ -z "$LOG_FILE" ]; then
    echo "Usage: ./crash_triage.sh <log_file>"
    exit 1
fi

echo "🔍 Analyzing $LOG_FILE..."

# 1. Java Crashes
echo "--- Java Exceptions ---"
grep -A 20 "FATAL EXCEPTION" "$LOG_FILE"

# 2. Native Crashes (Segmentation Faults)
echo "--- Native Crashes ---"
grep "signal 11 (SIGSEGV)" "$LOG_FILE"

# Extract PC address for symbolication
PC_ADDR=$(grep "pc " "$LOG_FILE" | head -n 1 | awk '{print $4}')

if [ ! -z "$PC_ADDR" ]; then
    echo "🎯 Found Native Crash at PC: $PC_ADDR"
    echo "🛠 Symbolicating using addr2line..."
    
    # Requires Android NDK addr2line in PATH or provided
    NDK_ADDR2LINE=$(find $ANDROID_HOME/ndk -name "llvm-symbolizer" | head -n 1)
    
    if [ -f "$LIB_PATH" ] && [ ! -z "$NDK_ADDR2LINE" ]; then
        $NDK_ADDR2LINE --obj="$LIB_PATH" "$PC_ADDR"
    else
        echo "⚠️ Missing libdeepeye_dsp.so or llvm-symbolizer. Symbolication skipped."
    fi
fi

# 3. ANR Identification
echo "--- ANR Summary ---"
grep "ANR in" "$LOG_FILE"
grep "Reason:" "$LOG_FILE"
