#include "oboe_engine.h"
#include <android/log.h>

OboeAudioEngine::OboeAudioEngine(int sampleRate, int channels) 
    : sampleRate(sampleRate), channels(channels) {
    dspEngine = std::make_unique<DSPEngine>(sampleRate, channels);
}

OboeAudioEngine::~OboeAudioEngine() {
    stop();
}

bool OboeAudioEngine::start() {
    oboe::AudioStreamBuilder builder;
    builder.setFormat(oboe::AudioFormat::Float)
        ->setChannelCount(channels)
        ->setSampleRate(sampleRate)
        ->setPerformanceMode(oboe::PerformanceMode::LowLatency)
        ->setSharingMode(oboe::SharingMode::Exclusive)
        ->setDataCallback(this);

    oboe::Result result = builder.openStream(stream);
    if (result != oboe::Result::OK) {
        __android_log_print(ANDROID_LOG_ERROR, "OboeEngine", "Error opening stream: %s", oboe::convertToText(result));
        return false;
    }

    result = stream->requestStart();
    if (result != oboe::Result::OK) {
        __android_log_print(ANDROID_LOG_ERROR, "OboeEngine", "Error starting stream: %s", oboe::convertToText(result));
        return false;
    }

    return true;
}

void OboeAudioEngine::stop() {
    if (stream) {
        stream->stop();
        stream->close();
        stream.reset();
    }
}

oboe::DataCallbackResult OboeAudioEngine::onAudioReady(
    oboe::AudioStream *audioStream,
    void *audioData,
    int32_t numFrames) {
    
    // In a real implementation, we would pull data from a buffer/source
    // For this demonstration, we assume audioData is being filled or used as output
    float *floatData = static_cast<float *>(audioData);
    
    // Process in place if needed or clear/fill
    // Here we'd typically have a circular buffer from the player
    // For now, we'll just show the DSP integration point
    dspEngine->process(floatData, floatData, numFrames);

    return oboe::DataCallbackResult::Continue;
}
