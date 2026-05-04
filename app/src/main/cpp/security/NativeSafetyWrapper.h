// DeepEyeMusicPro — Native Safety Wrapper
// Thread-safe, real-time-safe, bounds-checked utilities

#ifndef NATIVE_SAFETY_WRAPPER_H
#define NATIVE_SAFETY_WRAPPER_H

#include <jni.h>
#include <android/log.h>
#include <cmath>
#include <cstdint>
#include <cstring>

#define LOG_TAG "DeepEyeSafety"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Security: Strip all logs in release builds
#ifdef NDEBUG
#undef LOGI
#undef LOGW
#undef LOGE
#define LOGI(...)
#define LOGW(...)
#define LOGE(...)
#endif

namespace de_security {

// ── Denormal / Subnormal Protection ──
// ARM Cortex-A: Denormals cause 100x+ CPU penalty. Flush to zero.
static inline float flushDenormal(float x) {
    // Any value whose absolute exponent is below ~1e-18 is denormal for float
    const float DENORMAL_THRESHOLD = 1.0e-18f;
    float absX = fabsf(x);
    if (absX < DENORMAL_THRESHOLD && absX != 0.0f) {
        return 0.0f;
    }
    return x;
}

// Batch flush for buffers
static inline void flushDenormalBuffer(float* buffer, int frames) {
    if (!buffer) return;
    for (int i = 0; i < frames; i++) {
        buffer[i] = flushDenormal(buffer[i]);
    }
}

// ── NaN / Inf Detection & Sanitization ──
static inline bool isInvalidFloat(float x) {
    return std::isnan(x) || std::isinf(x);
}

static inline float sanitizeFloat(float x) {
    if (std::isnan(x)) return 0.0f;
    if (std::isinf(x)) return (x > 0.0f) ? 1.0f : -1.0f;
    return x;
}

static inline void sanitizeBuffer(float* buffer, int frames) {
    if (!buffer) return;
    for (int i = 0; i < frames; i++) {
        buffer[i] = sanitizeFloat(buffer[i]);
    }
}

// ── Buffer Bounds Validation ──
struct BufferValidationResult {
    bool valid = false;
    jlong capacity = 0;
    jlong required = 0;
    const char* error = nullptr;
};

static inline BufferValidationResult validateDirectFloatBuffer(
    JNIEnv* env,
    jobject buffer,
    jint frames,
    jint channels
) {
    BufferValidationResult result;
    if (buffer == nullptr) {
        result.error = "Buffer is null";
        return result;
    }
    if (frames <= 0 || channels <= 0 || channels > 8) { // Updated to support up to 8 channels
        result.error = "Invalid frame/channel count";
        return result;
    }

    jlong capacity = env->GetDirectBufferCapacity(buffer);
    if (capacity <= 0) {
        result.error = "Buffer is not direct or has zero capacity";
        return result;
    }

    jlong required = static_cast<jlong>(frames) * channels * sizeof(float);
    if (capacity < required) {
        result.capacity = capacity;
        result.required = required;
        result.error = "Buffer capacity insufficient";
        LOGE("SECURITY: Buffer overflow attempt detected. Capacity=%ld, Required=%ld", 
             static_cast<long>(capacity), static_cast<long>(required));
        return result;
    }

    void* address = env->GetDirectBufferAddress(buffer);
    if (address == nullptr) {
        result.error = "GetDirectBufferAddress returned null";
        return result;
    }

    result.capacity = capacity;
    result.valid = true;
    return result;
}

// ── Safe Math Utilities ──
static inline float safeLog10(float x) {
    if (x <= 0.0f) return -100.0f; // Return very low dB instead of -inf
    return log10f(x);
}

static inline float safeDivide(float num, float den, float fallback = 0.0f) {
    if (den == 0.0f || std::isnan(den) || std::isinf(den)) return fallback;
    return num / den;
}

static inline int clampInt(int value, int min, int max) {
    if (value < min) return min;
    if (value > max) return max;
    return value;
}

static inline float clampFloat(float value, float min, float max) {
    if (value < min) return min;
    if (value > max) return max;
    return value;
}

// ── JNI Exception Guard ──
class JniExceptionGuard {
    JNIEnv* env_;
public:
    explicit JniExceptionGuard(JNIEnv* env) : env_(env) {}
    ~JniExceptionGuard() {
        if (env_->ExceptionCheck()) {
            env_->ExceptionDescribe();
            env_->ExceptionClear();
            LOGE("JNI Exception detected and cleared");
        }
    }
};

} // namespace de_security

#endif // NATIVE_SAFETY_WRAPPER_H
