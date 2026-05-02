#include <jni.h>
#include <android/log.h>
#include <vector>
#include <mutex>
#include "jamesdsp/jdsp_controller.h"

#define LOG_TAG "DeepEyeDSP"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static std::mutex g_mutex;

extern "C" JNIEXPORT jlong JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeCreate(JNIEnv*, jobject, jint sample_rate, jint channels) {
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_t* dsp = jdsp_init(sample_rate, channels);
    return reinterpret_cast<jlong>(dsp);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeRelease(JNIEnv*, jobject, jlong handle) {
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_free(reinterpret_cast<jdsp_t*>(handle));
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetEqEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_eq_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetBassEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_bass_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetWidthEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_width_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetCompressorEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_compressor_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetReverbEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_reverb_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetConvolverEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_convolver_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetEqGain(JNIEnv*, jobject, jlong handle, jint band, jfloat gain_db) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_eq_gain(dsp, band, gain_db);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetEqGains(JNIEnv* env, jobject, jlong handle, jfloatArray gains) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !gains) return;
    jsize count = env->GetArrayLength(gains);
    jfloat* data = env->GetFloatArrayElements(gains, nullptr);
    jdsp_set_eq_gains(dsp, data, count);
    env->ReleaseFloatArrayElements(gains, data, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetBassBoost(JNIEnv*, jobject, jlong handle, jfloat amount) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_bass_boost(dsp, amount);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetStereoWidth(JNIEnv*, jobject, jlong handle, jfloat amount) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_stereo_width(dsp, amount);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetCompressor(JNIEnv*, jobject, jlong handle, jfloat threshold, jfloat ratio, jfloat attack, jfloat release) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_compressor(dsp, threshold, ratio, attack, release);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetReverb(JNIEnv*, jobject, jlong handle, jfloat room, jfloat damping, jfloat width, jfloat wet) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_reverb(dsp, room, damping, width, wet);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetLimiterCeiling(JNIEnv*, jobject, jlong handle, jfloat ceiling_db) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_limiter_ceiling(dsp, ceiling_db);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeSetConvolverIr(JNIEnv* env, jobject, jlong handle, jfloatArray left, jfloatArray right) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !left) return;
    jsize left_count = env->GetArrayLength(left);
    jfloat* left_data = env->GetFloatArrayElements(left, nullptr);
    jfloat* right_data = right ? env->GetFloatArrayElements(right, nullptr) : nullptr;
    jdsp_set_convolver_ir(dsp, left_data, right_data, left_count);
    env->ReleaseFloatArrayElements(left, left_data, JNI_ABORT);
    if (right && right_data) env->ReleaseFloatArrayElements(right, right_data, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeProcessFloatArray(JNIEnv* env, jobject, jlong handle, jfloatArray input, jfloatArray output, jint frames) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !input || !output || frames <= 0) return;
    jfloat* in_data = env->GetFloatArrayElements(input, nullptr);
    jfloat* out_data = env->GetFloatArrayElements(output, nullptr);
    jdsp_process(dsp, in_data, out_data, frames);
    env->ReleaseFloatArrayElements(input, in_data, JNI_ABORT);
    env->ReleaseFloatArrayElements(output, out_data, 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_NativeDSP_nativeProcessDirect(JNIEnv* env, jobject, jlong handle, jobject input, jobject output, jint frames) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !input || !output || frames <= 0) return;
    auto* in_data = static_cast<float*>(env->GetDirectBufferAddress(input));
    auto* out_data = static_cast<float*>(env->GetDirectBufferAddress(output));
    if (!in_data || !out_data) {
        LOGE("Direct buffer address unavailable");
        return;
    }
    jdsp_process(dsp, in_data, out_data, frames);
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeCreate(JNIEnv*, jobject, jint sample_rate, jint channels) {
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_t* dsp = jdsp_init(sample_rate, channels);
    if (dsp) {
        jdsp_v4a_set_enabled(dsp, 1);
    }
    return reinterpret_cast<jlong>(dsp);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeRelease(JNIEnv*, jobject, jlong handle) {
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_free(reinterpret_cast<jdsp_t*>(handle));
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeSetMasterEnabled(JNIEnv*, jobject, jlong handle, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_v4a_set_enabled(dsp, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeSetEffectEnabled(JNIEnv*, jobject, jlong handle, jint effect_ordinal, jboolean enabled) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_v4a_set_effect_enabled(dsp, effect_ordinal, enabled ? 1 : 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeSetEqGains(JNIEnv* env, jobject, jlong handle, jfloatArray gains) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !gains) return;
    jsize count = env->GetArrayLength(gains);
    jfloat* data = env->GetFloatArrayElements(gains, nullptr);
    jdsp_v4a_set_eq_gains(dsp, data, count);
    env->ReleaseFloatArrayElements(gains, data, JNI_ABORT);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeSetTubeWarmth(JNIEnv*, jobject, jlong handle, jfloat warmth) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_v4a_set_tube_warmth(dsp, warmth);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeSetReverb(JNIEnv*, jobject, jlong handle, jfloat room, jfloat damping, jfloat width, jfloat wet) {
    if (auto* dsp = reinterpret_cast<jdsp_t*>(handle)) jdsp_set_reverb(dsp, room, damping, width, wet);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeProcessFloatArray(JNIEnv* env, jobject, jlong handle, jfloatArray input, jfloatArray output, jint frames) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !input || !output || frames <= 0) return;
    jfloat* in_data = env->GetFloatArrayElements(input, nullptr);
    jfloat* out_data = env->GetFloatArrayElements(output, nullptr);
    jdsp_v4a_process(dsp, in_data, out_data, frames);
    env->ReleaseFloatArrayElements(input, in_data, JNI_ABORT);
    env->ReleaseFloatArrayElements(output, out_data, 0);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_nativeProcessDirect(JNIEnv* env, jobject, jlong handle, jobject input, jobject output, jint frames) {
    auto* dsp = reinterpret_cast<jdsp_t*>(handle);
    if (!dsp || !input || !output || frames <= 0) return;
    auto* in_data = static_cast<float*>(env->GetDirectBufferAddress(input));
    auto* out_data = static_cast<float*>(env->GetDirectBufferAddress(output));
    if (!in_data || !out_data) {
        LOGE("V4A direct buffer address unavailable");
        return;
    }
    jdsp_v4a_process(dsp, in_data, out_data, frames);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_loadConvolverIR(JNIEnv* env, jobject, jstring path) {
    if (!path) return;
    const char* c_path = env->GetStringUTFChars(path, nullptr);
    if (!c_path) return;
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_v4a_load_convolver_ir(c_path);
    env->ReleaseStringUTFChars(path, c_path);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_loadDDCProfile(JNIEnv* env, jobject, jstring path) {
    if (!path) return;
    const char* c_path = env->GetStringUTFChars(path, nullptr);
    if (!c_path) return;
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_v4a_load_ddc_profile(c_path);
    env->ReleaseStringUTFChars(path, c_path);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_setFETParams(JNIEnv*, jobject, jfloat attack, jfloat release, jfloat ratio, jfloat threshold, jfloat knee) {
    std::lock_guard<std::mutex> lock(g_mutex);
    jdsp_v4a_set_fet_params(attack, release, ratio, threshold, knee);
}

extern "C" JNIEXPORT void JNICALL
Java_com_deepeye_musicpro_dsp_v4a_NativeV4A_getSpectrumMagnitudes(JNIEnv* env, jobject, jfloatArray out) {
    if (!out) return;
    jsize count = env->GetArrayLength(out);
    jfloat* data = env->GetFloatArrayElements(out, nullptr);
    jdsp_v4a_get_spectrum(data, count);
    env->ReleaseFloatArrayElements(out, data, 0);
}
