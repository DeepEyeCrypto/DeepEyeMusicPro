#!/bin/bash
# debug_loop.sh — The ultimate iterative debugger

PACKAGE="com.deepeye.musicpro"
APK="app/build/outputs/apk/debug/app-debug.apk"

while true; do
    echo "========================================="
    echo "  DeepEye ADB Debug Loop"
    echo "========================================="
    echo "1) Build & Fresh Install"
    echo "2) Build & Incremental Install"
    echo "3) Watch Critical Logs"
    echo "4) Watch Audio Pipeline"
    echo "5) Record Screen (30s)"
    echo "6) Memory Check"
    echo "7) CPU Profile (30s)"
    echo "8) Run Stability Test"
    echo "9) Capture Full Session"
    echo "10) Kill & Restart App"
    echo "11) Clear App Data"
    echo "q) Quit"
    echo "========================================="
    read -p "Choice: " CHOICE

    case $CHOICE in
        1)
            ./gradlew assembleDebug
            adb uninstall $PACKAGE 2>/dev/null
            adb install -r "$APK"
            adb shell pm grant $PACKAGE android.permission.READ_MEDIA_AUDIO
            adb shell pm grant $PACKAGE android.permission.POST_NOTIFICATIONS
            adb shell am start -n $PACKAGE/.MainActivity
            ;;
        2)
            ./gradlew assembleDebug
            adb install -r "$APK"
            adb shell am start -n $PACKAGE/.MainActivity
            ;;
        3)
            adb logcat -c
            adb logcat | grep -E "FATAL|signal |ANR |DeepEye|ExoPlayer|AudioTrack"
            ;;
        4)
            adb logcat -c
            adb logcat | grep -E "AudioFlinger|MediaCodec|DeepEyeDSP|NativeDSP"
            ;;
        5)
            ./scripts/record_bug.sh
            ;;
        6)
            adb shell dumpsys meminfo $PACKAGE | grep -E "TOTAL PSS|Native Heap"
            ;;
        7)
            adb shell simpleperf record -p $(pidof $PACKAGE) --duration 30
            adb shell simpleperf report
            ;;
        8)
            ./tools/deep_test.sh
            ;;
        9)
            ./scripts/capture_session.sh
            ;;
        10)
            adb shell am force-stop $PACKAGE
            adb shell am start -n $PACKAGE/.MainActivity
            ;;
        11)
            adb shell pm clear $PACKAGE
            ;;
        q)
            break
            ;;
    esac
done
