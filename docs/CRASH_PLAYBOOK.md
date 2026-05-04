# 🚨 DeepEyeMusicPro Crash Forensic Playbook

This document details common runtime crashes in the DeepEyeMusicPro pipeline, root cause analysis, and standard operating procedures (SOP) for emergency resolution.

## 1. Native DSP Initialization (`UnsatisfiedLinkError`)

**Symptom**: App crashes immediately on startup or when the player screen opens.  
**Logcat**: `FATAL EXCEPTION: java.lang.UnsatisfiedLinkError: dlopen failed: library "libdeepeye_dsp.so" not found`  
**Root Cause**: The NDK build failed to package the correct ABI, or the app was installed on an unsupported architecture.  
**SOP**:
1. Check `app/build.gradle` `ndk { abiFilters 'armeabi-v7a', 'arm64-v8a', 'x86', 'x86_64' }`.
2. Wrap `System.loadLibrary` in a `try-catch` inside `NativeDSP.kt` companion object.
3. Fallback: Set a flag so the `AudioProcessor` operates in strict passthrough mode if native fails.

## 2. Media3 AudioProcessor Buffer Underflow/Overflow

**Symptom**: Crash during playback or when seeking. Sometimes accompanied by silent audio.  
**Logcat**: `java.lang.ArrayIndexOutOfBoundsException` or `MediaCodecVideoRenderer error`  
**Root Cause**: `queueInput()` frame math is incorrect, resulting in `remaining()` bytes mismatching channel count, OR `getOutput()` returns a buffer with `capacity < limit`.  
**SOP**:
1. Ensure `remaining = inputBuffer.remaining()`.
2. Frames calculation: `frames = remaining / (channelCount * bytesPerSample)`.
3. Clear/Flip DirectByteBuffers correctly. `outputBuffer.clear(); outputBuffer.put(inputBuffer); outputBuffer.flip();`

## 3. Native DSP Buffer Segmentation Fault (`SIGSEGV`)

**Symptom**: Unpredictable crash mid-song, usually when DSP sliders (EQ, Bass) are heavily manipulated.  
**Logcat**: `signal 11 (SIGSEGV), code 1 (SEGV_MAPERR)`  
**Root Cause**: Buffer pointers passed via JNI (`processDirect`) read or write past allocated boundaries, often triggered by thread concurrency if `preset` is changed mid-process.  
**SOP**:
1. Use `adb logcat | ndk-stack -sym app/build/intermediates/cxx/Debug/`.
2. Check `jni_bridge.cpp`. Ensure `env->GetDirectBufferCapacity(input_buffer)` matches the passed `frames` size.
3. Implement a lock-free ring buffer or atomic pointer swap for presets in C++ instead of mutating state mid-block.

## 4. Compose UI State Recomposition (`NullPointerException`)

**Symptom**: Crash when scrolling the library, opening the bottom sheet, or when visualizer activates.  
**Logcat**: `java.lang.NullPointerException` inside `LazyColumn` or `drawScope`.  
**Root Cause**: Flow state emits `null` while Compose is re-rendering. Canvas trying to draw a `FloatArray` that was concurrently reassigned.  
**SOP**:
1. Use `.collectAsStateWithLifecycle()` instead of `.collectAsState()`.
2. Provide default safe arrays for Visualizer (e.g., `FloatArray(64) { 0f }`).

## 5. MediaSession Service ANR (Background Execution)

**Symptom**: App freezes for 5 seconds when skipping tracks while the screen is off, leading to ANR.  
**Logcat**: `ANR in com.deepeye.musicpro ... Input dispatching timed out / main thread blocked`.  
**Root Cause**: `MediaSessionService.onStartCommand()` is doing synchronous database queries or heavy DSP initialization.  
**SOP**:
1. Delegate logic to `CoroutineScope(Dispatchers.IO)` inside the service.
2. Ensure `startForeground()` is called within 5 seconds of the service waking up.

## 6. StrictMode Thread Policy Violation

**Symptom**: App crashes in Debug build only (Release build works fine).  
**Logcat**: `android.os.StrictMode$StrictModeDiskReadViolation`  
**Root Cause**: `SharedPreferences` / `DataStore` reading on the UI thread during cold start.  
**SOP**:
1. Prefetch `DataStore` in `Application.onCreate` using `applicationScope.launch`.
2. Do not block the UI thread waiting for the read. Use `runBlocking` ONLY if absolutely required for immediate state (bad practice).
