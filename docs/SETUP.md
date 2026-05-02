# Setup

1. Install Android Studio and SDK 35.
2. Install CMake 3.22.1+ and an Android NDK through the SDK Manager.
3. Copy `local.properties.template` to `local.properties`.
4. Build the debug APK with Android Studio or Gradle.
5. Grant notification permission on Android 13+ to see playback/download notifications.

## Optional signing

Use Gradle properties for signing. Never commit keystores or private passwords.

## Optional extraction configuration

The app uses public web metadata and the included NewPipe Extractor dependency without private API keys. If a builder adds provider credentials or alternative extractors, store them in local-only configuration or collect them at runtime.
