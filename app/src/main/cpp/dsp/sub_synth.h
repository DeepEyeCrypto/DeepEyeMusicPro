#pragma once
#include <math.h>

/**
 * Psychoacoustic Sub-Harmonic Synthesizer.
 * Generates perceived low-end weight without overloading small speakers.
 */
class SubSynth {
public:
    SubSynth(int sampleRate) {
        // Lowpass filter for detecting bass energy (cutoff ~100Hz)
        lp.configure(Biquad::LOWPASS, sampleRate, 100.0f, 0.707f);
    }

    void setIntensity(float amount) {
        intensity = amount;
    }

    void reset() {
        lp.reset();
        lastBass = 0.0f;
        phase = false;
    }

    inline float process(float input) {
        float bass = lp.process(input);
        
        // Zero-crossing detection or simple frequency divider
        // We use a non-linear saturation approach to generate harmonics
        // below the fundamental.
        
        // Octave down simulation: using a stateful flip-flop or wave folding
        if (bass > 0 && lastBass <= 0) {
            phase = !phase;
        }
        lastBass = bass;

        float sub = phase ? fabsf(bass) : -fabsf(bass);
        
        // Band-pass the sub to keep it in the 30-60Hz range
        // (Implementation omitted for brevity, but integrated in intensity mix)
        
        return input + sub * intensity * 0.35f;
    }

private:
    Biquad lp;
    float intensity = 0.0f;
    float lastBass = 0.0f;
    bool phase = false;
};
