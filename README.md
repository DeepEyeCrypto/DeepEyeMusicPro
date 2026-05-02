# DeepEyeMusicPro

DeepEyeMusicPro is an Android-only premium dark music player focused on YouTube search/link extraction, resilient ad-blocked WebView fallback, offline downloads, Media3 playback, and a native in-app DSP chain.

## Highlights

- Package: `com.deepeye.musicpro`
- Kotlin, Android SDK, Media3, Room, OkHttp, Coil, Material 3
- MediaLibraryService playback architecture with media session controls
- Native JNI DSP pipeline with EQ, bass, stereo width, compressor, convolver, reverb, and limiter stages
- Search and stream extraction layer with NewPipe-first capability checks and safe fallbacks
- Foreground download service with Room metadata and FileProvider sharing
- Premium dark design tokens, adaptive icon, and builder-ready docs

## Build

1. Install Android Studio with SDK 35 and CMake 3.22.1 or newer.
2. Copy `local.properties.template` to `local.properties` and set `sdk.dir` if Android Studio does not create it automatically.
3. Open the project root in Android Studio.
4. Run `./gradlew assembleDebug` if a wrapper is added by Android Studio, or `gradle assembleDebug` from the project root.

Release signing is intentionally optional for open-source builders. Add Gradle properties named in `local.properties.template` to sign release builds with a private keystore.

## Runtime configuration

DeepEyeMusicPro does not ship private API keys. External/user-owned configuration is entered at runtime or kept in local-only builder files. If extraction fails because a provider changes, playback continues through offline/local items and the optional WebView fallback.

## V4A dual-mode audio architecture

DeepEyeMusicPro now exposes a Home quick-action tile for V4A Audio FX and uses a dual-mode engine:

- **System V4A mode** detects pre-installed/root V4A drivers through `AudioEffect.queryEffects()` using modern and legacy V4A UUIDs, then attaches the driver to the active Media3 audio session when available.
- **Bundled V4A mode** is the non-root default. It injects `V4ABundledProcessor` into the Media3 `DefaultAudioSink` chain and processes float PCM through `libdeepeye_dsp.so`.
- Bundled processing includes IRS convolver, DDC headphone correction, FET soft-knee dynamics, tube harmonic saturation, optional room/reverb depth, and a 32-bin spectrum analyzer used by the bottom sheet visualizer.
- V4A assets are bundled under `app/src/main/assets/v4a/` and copied to cache at startup so IRS/DDC/preset files can be loaded by native DSP code.
- The V4A bottom sheet provides master mode status, effect toggles, IRS/DDC chip selectors, FET sliders, tube warmth, EQ display, animated spectrum bars, built-in presets, and custom preset saving through Room.
