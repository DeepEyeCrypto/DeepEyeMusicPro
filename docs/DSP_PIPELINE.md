# DSP Pipeline

The native DSP chain is:

PCM Float Input → 10-band EQ → Bass Boost → Stereo Width → FIR/Convolver → Compressor → Reverb → Limiter → PCM Float Output

Kotlin entry points:

- `dsp/DSPPreset.kt`: built-in and custom preset serialization model
- `dsp/NativeDSP.kt`: guarded JNI bridge with passthrough fallback
- `dsp/DSPAudioProcessor.kt`: Media3 `AudioProcessor` adapter
- `dsp/DSPManager.kt`: current preset state and processor attachment

If the native library fails to load, the app reports DSP unavailable and keeps playback in passthrough mode.
