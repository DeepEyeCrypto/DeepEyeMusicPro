#!/bin/bash
# install_incremental.sh
# Preserves app data, just updates APK
adb install -r -d app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.deepeye.musicpro/.MainActivity
