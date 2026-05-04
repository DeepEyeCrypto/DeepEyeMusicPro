#ifndef DEEPEYE_FFT_CAPTURE_H
#define DEEPEYE_FFT_CAPTURE_H

#include <vector>
#include <complex>
#include <cmath>
#include <mutex>
#include "../util/RingBuffer.h"

class FFTCapture {
public:
    static constexpr int FFT_SIZE = 1024;
    static constexpr int BANDS = 64;

    FFTCapture(int sampleRate);
    ~FFTCapture();

    // Called from Audio Thread
    void pushSamples(const float* samples, int frames, int channels);

    // Called from JNI/UI Thread (polling)
    void getVisualizerData(float* spectrum, float* waveform, float* peak);

private:
    int sampleRate;
    RingBuffer<float> inputBuffer;
    
    std::vector<float> fftInput;
    std::vector<std::complex<float>> fftOutput;
    std::vector<float> window;
    
    float lastPeak = 0.0f;
    float currentSpectrum[BANDS] = {0};
    float currentWaveform[FFT_SIZE / 2] = {0};
    std::mutex dataMutex;

    void processFFT();
    void computeBands();
    void initializeWindow();
    
    // Radix-2 FFT helper
    void fft(std::vector<std::complex<float>>& a, bool invert);
};

#endif // DEEPEYE_FFT_CAPTURE_H
