#!/bin/bash
# install_fresh.sh
PACKAGE="com.deepeye.musicpro"
APK="app/build/outputs/apk/debug/app-debug.apk"

echo "🔴 Uninstalling existing..."
adb uninstall $PACKAGE 2>/dev/null

echo "📦 Installing fresh..."
adb install -r -d "$APK"

echo "🔐 Granting permissions..."
adb shell pm grant $PACKAGE android.permission.POST_NOTIFICATIONS
adb shell pm grant $PACKAGE android.permission.READ_MEDIA_AUDIO
adb shell pm grant $PACKAGE android.permission.BLUETOOTH_CONNECT
adb shell pm grant $PACKAGE android.permission.BLUETOOTH_SCAN

echo "🚀 Launching..."
adb shell am start -n $PACKAGE/.MainActivity

echo "✅ Install complete. PID: $(adb shell pidof $PACKAGE)"
