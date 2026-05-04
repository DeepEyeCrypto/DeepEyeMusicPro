#pragma once
#include <oboe/Oboe.h>
#include "dsp/dsp_engine.h"
#include <memory>

class OboeAudioEngine : public oboe::AudioStreamDataCallback {
public:
    OboeAudioEngine(int sampleRate, int channels);
    ~OboeAudioEngine();

    bool start();
    void stop();

    oboe::DataCallbackResult onAudioReady(
        oboe::AudioStream *audioStream,
        void *audioData,
        int32_t numFrames) override;

    DSPEngine* getDSPEngine() { return dspEngine.get(); }

private:
    std::shared_ptr<oboe::AudioStream> stream;
    std::unique_ptr<DSPEngine> dspEngine;
    int sampleRate;
    int channels;
};
