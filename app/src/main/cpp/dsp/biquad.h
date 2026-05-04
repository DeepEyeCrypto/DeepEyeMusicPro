#pragma once
#include <math.h>
#include "security/NativeSafetyWrapper.h"

using namespace de_security;

/**
 * Hardened Biquad Filter (Direct Form II Transposed)
 * Includes denormal protection and parameter validation.
 */
class Biquad {
public:
    enum Type { LOWPASS, HIGHPASS, BANDPASS, PEAK, NOTCH, LOWSHELF, HIGHSHELF };

    void configure(Type type, float fs, float f0, float Q, float dbGain = 0.0f) {
        // Sanitize parameters
        fs = std::max(8000.0f, fs);
        f0 = std::max(10.0f, std::min(fs / 2.1f, f0)); // Nyquist safety
        Q = std::max(0.1f, std::min(20.0f, Q));
        dbGain = std::max(-24.0f, std::min(24.0f, dbGain));

        float A = powf(10.0f, dbGain / 40.0f);
        float omega = 2.0f * M_PI * f0 / fs;
        float sn = sinf(omega);
        float cs = cosf(omega);
        float alpha = sn / (2.0f * Q);

        float b0, b1, b2, a0, a1, a2;

        switch (type) {
            case LOWPASS:
                b0 = (1.0f - cs) / 2.0f;
                b1 = 1.0f - cs;
                b2 = (1.0f - cs) / 2.0f;
                a0 = 1.0f + alpha;
                a1 = -2.0f * cs;
                a2 = 1.0f - alpha;
                break;
            case PEAK:
                b0 = 1.0f + alpha * A;
                b1 = -2.0f * cs;
                b2 = 1.0f - alpha * A;
                a0 = 1.0f + alpha / A;
                a1 = -2.0f * cs;
                a2 = 1.0f - alpha / A;
                break;
            default: return; // Type not implemented
        }

        // Normalize coefficients
        this->b0 = b0 / a0;
        this->b1 = b1 / a0;
        this->b2 = b2 / a0;
        this->a1 = a1 / a0;
        this->a2 = a2 / a0;
    }

    void reset() {
        z1 = 0.0f;
        z2 = 0.0f;
    }

    inline float process(float x) {
        float y = b0 * x + z1;
        z1 = flushDenormal(b1 * x - a1 * y + z2);
        z2 = flushDenormal(b2 * x - a2 * y);
        return y;
    }

private:
    float b0, b1, b2, a1, a2;
    float z1 = 0.0f, z2 = 0.0f;
};
