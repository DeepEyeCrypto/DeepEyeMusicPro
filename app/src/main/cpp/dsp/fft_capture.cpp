#include "fft_capture.h"
#include <algorithm>
#include <cstring>

#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

FFTCapture::FFTCapture(int sampleRate) 
    : sampleRate(sampleRate), 
      inputBuffer(8192), 
      fftInput(FFT_SIZE), 
      fftOutput(FFT_SIZE),
      window(FFT_SIZE) {
    initializeWindow();
}

FFTCapture::~FFTCapture() {}

void FFTCapture::initializeWindow() {
    // Hann Window
    for (int i = 0; i < FFT_SIZE; ++i) {
        window[i] = 0.5f * (1.0f - cosf(2.0f * M_PI * i / (FFT_SIZE - 1)));
    }
}

void FFTCapture::pushSamples(const float* samples, int frames, int channels) {
    // Convert to mono and push to ring buffer
    for (int i = 0; i < frames; ++i) {
        float mono = 0;
        for (int ch = 0; ch < channels; ++ch) {
            mono += samples[i * channels + ch];
        }
        mono /= channels;
        inputBuffer.push(mono);
    }

    // If we have enough samples, trigger processing
    if (inputBuffer.available_read() >= FFT_SIZE) {
        processFFT();
    }
}

void FFTCapture::processFFT() {
    // Pop FFT_SIZE samples
    float rawSamples[FFT_SIZE];
    inputBuffer.pop_batch(rawSamples, FFT_SIZE);

    float maxAmp = 0.0f;
    for (int i = 0; i < FFT_SIZE; ++i) {
        // Apply window and store for peak/waveform
        float s = rawSamples[i];
        fftInput[i] = s * window[i];
        fftOutput[i] = {fftInput[i], 0.0f};
        
        float absS = fabsf(s);
        if (absS > maxAmp) maxAmp = absS;
        
        // Store subset for waveform display (every 4th sample to fit 256 points)
        if (i % 4 == 0 && i / 4 < FFT_SIZE / 4) {
            // We'll fill this in the JNI call later or here
        }
    }

    // Run FFT
    fft(fftOutput, false);

    // Update spectrum and peak in a thread-safe way
    std::lock_guard<std::mutex> lock(dataMutex);
    lastPeak = maxAmp;
    
    // Waveform: Downsample for UI
    for (int i = 0; i < FFT_SIZE / 2; ++i) {
        currentWaveform[i] = rawSamples[i * 2];
    }

    computeBands();
}

void FFTCapture::fft(std::vector<std::complex<float>>& a, bool invert) {
    int n = a.size();
    for (int i = 1, j = 0; i < n; i++) {
        int bit = n >> 1;
        for (; j & bit; bit >>= 1) j ^= bit;
        j ^= bit;
        if (i < j) std::swap(a[i], a[j]);
    }

    for (int len = 2; len <= n; len <<= 1) {
        float ang = 2 * M_PI / len * (invert ? -1 : 1);
        std::complex<float> wlen(cos(ang), sin(ang));
        for (int i = 0; i < n; i += len) {
            std::complex<float> w(1);
            for (int j = 0; j < len / 2; j++) {
                std::complex<float> u = a[i + j], v = a[i + j + len / 2] * w;
                a[i + j] = u + v;
                a[i + j + len / 2] = u - v;
                w *= wlen;
            }
        }
    }

    if (invert) {
        for (auto& x : a) x /= n;
    }
}

void FFTCapture::computeBands() {
    // Group FFT bins into BANDS log-spaced bands
    // FFT_SIZE / 2 bins represent 0 to sampleRate/2 Hz
    int halfSize = FFT_SIZE / 2;
    float maxFreq = sampleRate / 2.0f;
    
    for (int b = 0; b < BANDS; ++b) {
        float freqStart = 20.0f * powf(maxFreq / 20.0f, (float)b / BANDS);
        float freqEnd = 20.0f * powf(maxFreq / 20.0f, (float)(b + 1) / BANDS);
        
        int binStart = (int)(freqStart * FFT_SIZE / sampleRate);
        int binEnd = (int)(freqEnd * FFT_SIZE / sampleRate);
        if (binEnd <= binStart) binEnd = binStart + 1;
        if (binEnd > halfSize) binEnd = halfSize;

        float magnitude = 0;
        for (int i = binStart; i < binEnd; ++i) {
            magnitude += std::abs(fftOutput[i]);
        }
        magnitude /= (binEnd - binStart);
        
        // Convert to log scale (approx dB) and normalize
        float dB = 20.0f * log10f(magnitude + 1e-6f);
        float normalized = (dB + 80.0f) / 80.0f; // -80dB to 0dB range
        currentSpectrum[b] = std::max(0.0f, std::min(1.0f, normalized));
    }
}

void FFTCapture::getVisualizerData(float* spectrum, float* waveform, float* peak) {
    std::lock_guard<std::mutex> lock(dataMutex);
    if (spectrum) std::memcpy(spectrum, currentSpectrum, sizeof(float) * BANDS);
    if (waveform) std::memcpy(waveform, currentWaveform, sizeof(float) * (FFT_SIZE / 2));
    if (peak) *peak = lastPeak;
}
