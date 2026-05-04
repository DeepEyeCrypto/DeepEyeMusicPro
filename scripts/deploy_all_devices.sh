#!/bin/bash
# deploy_all_devices.sh
# Installs to ALL connected devices
APK="app/build/outputs/apk/debug/app-debug.apk"

adb devices | grep -v "List" | grep "device$" | while read -r serial _; do
    echo "📱 Deploying to $serial..."
    adb -s "$serial" install -r "$APK"
    adb -s "$serial" shell am start -n com.deepeye.musicpro/.MainActivity
done
