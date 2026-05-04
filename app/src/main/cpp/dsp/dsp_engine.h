#pragma once
#include <vector>
#include <memory>
#include "biquad.h"
#include "limiter.h"
#include "sub_synth.h"

#include "fft_capture.h"

class DSPEngine {
public:
    DSPEngine(int sampleRate, int channels);
    ~DSPEngine();

    void process(const float* input, float* output, int frames);
    void reset();
    
    void setEnabled(bool enabled) { this->enabled = enabled; }
    void setPreamp(float dbGain);
    void setEqGain(int band, float dbGain);
    void setBassBoost(float amount);
    void setSubIntensity(float amount);
    void setCompressor(float threshold, float ratio, float attack, float release);
    void setLimiterCeiling(float dbCeiling);
    void setSpeakerSafe(bool enabled) { this->speakerSafe = enabled; }

    // Visualizer Access
    void getVisualizerData(float* spectrum, float* waveform, float* peak) {
        if (fftCapture) fftCapture->getVisualizerData(spectrum, waveform, peak);
    }

private:
    int sampleRate;
    int channels;
    bool enabled = true;
    bool speakerSafe = false;

    float preampGain = 1.0f;
    float targetPreamp = 1.0f;
    
    std::vector<std::unique_ptr<Biquad>> eqBands;
    std::vector<std::unique_ptr<Limiter>> limiters;
    std::vector<std::unique_ptr<SubSynth>> subSynths;
    std::unique_ptr<FFTCapture> fftCapture;
    
    // Gain staging: Auto-compensation
    void updateGainStaging();
    float autoGainComp = 1.0f;
};
