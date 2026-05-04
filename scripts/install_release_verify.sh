#!/bin/bash
# install_release_verify.sh
APK="app/build/outputs/apk/release/app-release.apk"

# Verify signature before install
apksigner verify --verbose "$APK"

# Install as fresh user would
adb install "$APK"

# Verify no debug flag
adb shell dumpsys package com.deepeye.musicpro | grep -i debuggable
