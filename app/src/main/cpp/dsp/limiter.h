#pragma once
#include <math.h>
#include <algorithm>
#include <vector>

/**
 * Professional Lookahead Limiter / Soft Clipper.
 * Prevents digital clipping while maintaining punch and loudness.
 */
class Limiter {
public:
    Limiter(int sampleRate, float lookaheadMs = 2.0f) {
        lookaheadSamples = (int)(sampleRate * lookaheadMs / 1000.0f);
        if (lookaheadSamples < 1) lookaheadSamples = 1;
        buffer.resize(lookaheadSamples, 0.0f);
        writeIndex = 0;
        currentGain = 1.0f;
        releaseCoeff = expf(-1.0f / (0.015f * sampleRate)); // 15ms release
    }

    void setCeiling(float dbCeiling) {
        ceiling = powf(10.0f, dbCeiling / 20.0f);
    }

    void reset() {
        std::fill(buffer.begin(), buffer.end(), 0.0f);
        writeIndex = 0;
        currentGain = 1.0f;
    }

    inline float process(float input) {
        buffer[writeIndex] = input;
        
        // Simple peak detection in lookahead window
        float maxPeak = 0.0f;
        for (float s : buffer) {
            maxPeak = std::max(maxPeak, fabsf(s));
        }

        float targetGain = 1.0f;
        if (maxPeak > ceiling) {
            targetGain = ceiling / maxPeak;
        }

        // Smooth gain reduction (Fast attack, slower release)
        if (targetGain < currentGain) {
            currentGain = targetGain; // Instant attack for limiter
        } else {
            currentGain = releaseCoeff * currentGain + (1.0f - releaseCoeff) * targetGain;
        }

        // Delayed output
        int readIndex = (writeIndex + 1) % lookaheadSamples;
        float delayedInput = buffer[readIndex];
        writeIndex = (writeIndex + 1) % lookaheadSamples;

        float limited = delayedInput * currentGain;

        // Final safety soft-clipper (tanh) to prevent any overshoot
        if (fabsf(limited) > ceiling * 0.95f) {
            limited = ceiling * tanhf(limited / ceiling);
        }

        return limited;
    }

private:
    std::vector<float> buffer;
    int lookaheadSamples;
    int writeIndex;
    float currentGain;
    float ceiling = 0.95f;
    float releaseCoeff;
};
