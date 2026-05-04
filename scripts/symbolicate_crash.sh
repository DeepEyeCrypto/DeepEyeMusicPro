#!/bin/bash
# symbolicate_crash.sh
# Usage: ./symbolicate_crash.sh <tombstone_or_log_file>

LOGFILE=${1:-"tombstone.log"}
SO_PATH="app/build/intermediates/cmake/debug/obj/arm64-v8a/libdeepeye_dsp.so"
ADDR2LINE="$ANDROID_NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/llvm-addr2line"

echo "Symbolicating native crashes from $LOGFILE..."
grep -oE 'pc [0-9a-f]+ .*libdeepeye_dsp.so' "$LOGFILE" | while read -r line; do
    PC=$(echo "$line" | grep -oE '[0-9a-f]+' | head -1)
    echo "PC: $PC → $($ADDR2LINE -e "$SO_PATH" "$PC")"
done
