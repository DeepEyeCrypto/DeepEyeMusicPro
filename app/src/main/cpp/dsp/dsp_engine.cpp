#include "dsp_engine.h"
#include "security/NativeSafetyWrapper.h"
#include <algorithm>

using namespace de_security;

DSPEngine::DSPEngine(int sampleRate, int channels) : sampleRate(sampleRate), channels(channels) {
    // Sanitize channel count (Max 8 supported)
    if (this->channels > 8) this->channels = 8;
    if (this->channels < 1) this->channels = 1;

    float frequencies[] = {31.0f, 62.0f, 125.0f, 250.0f, 500.0f, 1000.0f, 2000.0f, 4000.0f, 8000.0f, 16000.0f};
    for (int i = 0; i < 10; ++i) {
        for (int ch = 0; ch < this->channels; ++ch) {
            auto bq = std::make_unique<Biquad>();
            bq->configure(Biquad::PEAK, (float)sampleRate, frequencies[i], 1.414f, 0.0f);
            eqBands.push_back(std::move(bq));
        }
    }

    for (int ch = 0; ch < this->channels; ++ch) {
        limiters.push_back(std::make_unique<Limiter>(sampleRate));
        subSynths.push_back(std::make_unique<SubSynth>(sampleRate));
    }

    fftCapture = std::make_unique<FFTCapture>(sampleRate);
}

DSPEngine::~DSPEngine() {}

void DSPEngine::setPreamp(float dbGain) {
    targetPreamp = powf(10.0f, std::max(-30.0f, std::min(30.0f, dbGain)) / 20.0f);
}

void DSPEngine::setEqGain(int band, float dbGain) {
    if (band < 0 || band >= 10) return;
    float frequencies[] = {31.0f, 62.0f, 125.0f, 250.0f, 500.0f, 1000.0f, 2000.0f, 4000.0f, 8000.0f, 16000.0f};
    for (int ch = 0; ch < channels; ++ch) {
        eqBands[band * channels + ch]->configure(Biquad::PEAK, (float)sampleRate, frequencies[band], 1.414f, dbGain);
    }
}

void DSPEngine::process(const float* input, float* output, int frames) {
    if (!enabled || frames <= 0) {
        if (input != output) {
             for (int i = 0; i < frames * channels; ++i) output[i] = input[i];
        }
        if (fftCapture) fftCapture->pushSamples(output, frames, channels);
        return;
    }

    float smoothing = 0.001f;

    for (int f = 0; f < frames; ++f) {
        preampGain = preampGain * (1.0f - smoothing) + targetPreamp * smoothing;

        for (int ch = 0; ch < channels; ++ch) {
            float sample = input[f * channels + ch];
            
            // SECURITY: Sanitize input sample
            sample = clampFloat(sample, -1.0f, 1.0f) * preampGain;

            // SubSynth
            sample = subSynths[ch]->process(sample);

            // EQ
            for (int b = 0; b < 10; ++b) {
                sample = eqBands[b * channels + ch]->process(sample);
            }

            // Tube Saturation (Hardened)
            sample = flushDenormal(tanhf(sample * 1.05f));

            // Limiter
            sample = limiters[ch]->process(sample);

            // SECURITY: Final check for NaN/Inf before writing to buffer
            output[f * channels + ch] = clampFloat(sample, -1.0f, 1.0f);
        }
    }

    if (fftCapture) fftCapture->pushSamples(output, frames, channels);
}

void DSPEngine::setSubIntensity(float amount) {
    for (auto& s : subSynths) s->setIntensity(std::max(0.0f, std::min(1.0f, amount)));
}

void DSPEngine::setLimiterCeiling(float dbCeiling) {
    for (auto& l : limiters) l->setCeiling(dbCeiling);
}

void DSPEngine::reset() {
    for (auto& b : eqBands) b->reset();
    for (auto& l : limiters) l->reset();
    for (auto& s : subSynths) s->reset();
    preampGain = targetPreamp;
}
