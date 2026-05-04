#!/bin/bash
# DeepEyeMusicPro — One-Command Deployment Script
# Author: Senior QA Engineer

PACKAGE="com.deepeye.musicpro.debug"
NAMESPACE="com.deepeye.musicpro"
APK="app/build/outputs/apk/debug/app-debug.apk"
MAIN_ACTIVITY="$NAMESPACE.ui.MainActivity"

echo "🚀 Starting Deployment for $PACKAGE"

# 1. Device Check
DEVICE_ID=$(adb devices | grep -v "List" | grep "device" | head -n 1 | awk '{print $1}')
if [ -z "$DEVICE_ID" ]; then
    echo "❌ Error: No device connected via ADB."
    exit 1
fi
echo "📱 Target Device: $DEVICE_ID"

# 2. Cleanup
echo "🧹 Uninstalling previous version..."
adb uninstall $PACKAGE > /dev/null 2>&1

# 3. Installation
echo "📦 Installing APK: $APK"
if [ ! -f "$APK" ]; then
    echo "❌ Error: APK not found! Run ./gradlew assembleDebug first."
    exit 1
fi

adb install -r -d "$APK"
if [ $? -ne 0 ]; then
    echo "❌ Installation failed."
    exit 1
fi

# 4. Permissions
echo "🔐 Granting critical permissions..."
adb shell pm grant $PACKAGE android.permission.POST_NOTIFICATIONS
adb shell pm grant $PACKAGE android.permission.READ_MEDIA_AUDIO
adb shell pm grant $PACKAGE android.permission.BLUETOOTH_CONNECT

# 5. Launch
echo "🎬 Launching $PACKAGE/$MAIN_ACTIVITY..."
adb shell am start -n "$PACKAGE/$MAIN_ACTIVITY"

# 6. Verification
PID=$(adb shell pidof $PACKAGE)
if [ -z "$PID" ]; then
    echo "❌ Error: Process failed to start."
    exit 1
else
    echo "✅ Success! Process running with PID: $PID"
fi

echo "📊 Monitoring logs for native DSP initialization..."
adb logcat -d | grep "DeepEye"
