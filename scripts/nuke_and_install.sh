#!/bin/bash
# nuke_and_install.sh
adb shell pm clear com.deepeye.musicpro  # Wipes ALL app data
adb uninstall com.deepeye.musicpro
adb install app/build/outputs/apk/debug/app-debug.apk
