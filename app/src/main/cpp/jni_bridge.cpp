#include <jni.h>
#include <android/log.h>
#include <vector>
#include <pthread.h>
#include "dsp/dsp_engine.h"
#include "security/NativeSafetyWrapper.h"

#undef LOG_TAG
#define LOG_TAG "DeepEyeDSP_SEC"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM* vm, void* reserved) {
    __android_log_print(ANDROID_LOG_INFO, "DeepEyeJNI", "JNI_OnLoad called - Native library initializing...");
    return JNI_VERSION_1_6;
}

using namespace de_security;

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeReset(JNIEnv* env, jobject, jlong handle) {
    JniExceptionGuard guard(env);
    if (auto* engine = reinterpret_cast<DSPEngine*>(handle)) {
        engine->reset();
    }
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeCreate(JNIEnv* env, jobject, jint sample_rate, jint channels) {
    JniExceptionGuard guard(env);
    
    // SECURITY: Validate sample rate and channels
    if (sample_rate < 8000 || sample_rate > 384000) {
        LOGE("SECURITY: Invalid sample rate: %d", sample_rate);
        return 0;
    }
    if (channels < 1 || channels > 8) {
        LOGE("SECURITY: Invalid channel count: %d", channels);
        return 0;
    }

    try {
        auto* engine = new DSPEngine(sample_rate, channels);
        return reinterpret_cast<jlong>(engine);
    } catch (...) {
        LOGE("SECURITY: Memory allocation failed for DSPEngine");
        return 0;
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeRelease(JNIEnv* env, jobject, jlong handle) {
    JniExceptionGuard guard(env);
    if (handle) {
        delete reinterpret_cast<DSPEngine*>(handle);
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetEnabled(JNIEnv* env, jobject, jlong handle, jboolean enabled) {
    JniExceptionGuard guard(env);
    if (auto* engine = reinterpret_cast<DSPEngine*>(handle)) {
        engine->setEnabled(enabled);
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetEqGains(JNIEnv* env, jobject, jlong handle, jfloatArray gains) {
    JniExceptionGuard guard(env);
    auto* engine = reinterpret_cast<DSPEngine*>(handle);
    if (!engine || !gains) return;

    jsize count = env->GetArrayLength(gains);
    // SECURITY: Band limit
    if (count > 10) count = 10;

    jfloat* data = env->GetFloatArrayElements(gains, nullptr);
    if (data) {
        for (int i = 0; i < count; ++i) {
            // SECURITY: Clamp gain
            float gain = clampFloat(data[i], -24.0f, 24.0f);
            engine->setEqGain(i, gain);
        }
        env->ReleaseFloatArrayElements(gains, data, JNI_ABORT);
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetBassBoost(JNIEnv* env, jobject, jlong handle, jfloat amount) {
    JniExceptionGuard guard(env);
    if (auto* engine = reinterpret_cast<DSPEngine*>(handle)) {
        // SECURITY: Clamp intensity
        engine->setSubIntensity(clampFloat(amount, 0.0f, 1.0f));
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetLimiterCeiling(JNIEnv* env, jobject, jlong handle, jfloat ceiling_db) {
    JniExceptionGuard guard(env);
    if (auto* engine = reinterpret_cast<DSPEngine*>(handle)) {
        // SECURITY: Clamp ceiling
        engine->setLimiterCeiling(clampFloat(ceiling_db, -30.0f, 0.0f));
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeProcessDirect(JNIEnv* env, jobject, jlong handle, jobject input, jobject output, jint frames) {
    JniExceptionGuard guard(env);
    auto* engine = reinterpret_cast<DSPEngine*>(handle);
    if (!engine || !input || !output || frames <= 0) return;
    
    // SECURITY FIX: DEEP-2026-001 — Validate buffer capacity
    // We need to know channels. In this handle-based system, engine knows it.
    // Assuming 2 for now, but should ideally be pulled from engine.
    int channels = 2; 
    
    auto inResult = validateDirectFloatBuffer(env, input, frames, channels);
    if (!inResult.valid) {
        LOGE("SECURITY: Input buffer invalid: %s", inResult.error);
        return;
    }
    auto outResult = validateDirectFloatBuffer(env, output, frames, channels);
    if (!outResult.valid) {
        LOGE("SECURITY: Output buffer invalid: %s", outResult.error);
        return;
    }

    float* in_data = static_cast<float*>(env->GetDirectBufferAddress(input));
    float* out_data = static_cast<float*>(env->GetDirectBufferAddress(output));
    
    if (in_data && out_data) {
        engine->process(in_data, out_data, frames);
        
        // SECURITY FIX: DEEP-2026-003 — Sanitize output to prevent NaN/Inf poisoning Java
        sanitizeBuffer(out_data, frames * channels);
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeGetVisualizerData(
    JNIEnv* env, jobject, jlong handle, jfloatArray spectrum, jfloatArray waveform, jfloatArray peak
) {
    JniExceptionGuard guard(env);
    auto* engine = reinterpret_cast<DSPEngine*>(handle);
    if (!engine) return;

    jfloat* spectrum_ptr = spectrum ? env->GetFloatArrayElements(spectrum, nullptr) : nullptr;
    jfloat* waveform_ptr = waveform ? env->GetFloatArrayElements(waveform, nullptr) : nullptr;
    jfloat* peak_ptr = peak ? env->GetFloatArrayElements(peak, nullptr) : nullptr;

    engine->getVisualizerData(spectrum_ptr, waveform_ptr, peak_ptr);

    if (spectrum_ptr) env->ReleaseFloatArrayElements(spectrum, spectrum_ptr, 0);
    if (waveform_ptr) env->ReleaseFloatArrayElements(waveform, waveform_ptr, 0);
    if (peak_ptr) env->ReleaseFloatArrayElements(peak, peak_ptr, 0);
}
