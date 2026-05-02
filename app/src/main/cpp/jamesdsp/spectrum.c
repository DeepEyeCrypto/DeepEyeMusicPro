#include "spectrum.h"
#include <math.h>
#include <string.h>

void v4a_spectrum_init(v4a_spectrum_t* spectrum, int sample_rate) {
    if (!spectrum) return;
    memset(spectrum, 0, sizeof(v4a_spectrum_t));
    spectrum->sample_rate = sample_rate > 0 ? sample_rate : 48000;
}

void v4a_spectrum_push(v4a_spectrum_t* spectrum, float mono_sample) {
    if (!spectrum) return;
    spectrum->ring[spectrum->write_index] = mono_sample;
    spectrum->write_index = (spectrum->write_index + 1) % V4A_SPECTRUM_WINDOW;
    if (spectrum->filled < V4A_SPECTRUM_WINDOW) spectrum->filled++;
}

static void calculate_magnitudes(v4a_spectrum_t* spectrum) {
    if (!spectrum || spectrum->filled < 32) return;
    for (int bin = 0; bin < V4A_SPECTRUM_BINS; ++bin) {
        float norm = (float)bin / (float)(V4A_SPECTRUM_BINS - 1);
        float freq = 35.0f * powf(20000.0f / 35.0f, norm);
        float omega = 2.0f * 3.14159265359f * freq / (float)spectrum->sample_rate;
        float real = 0.0f;
        float imag = 0.0f;
        for (int n = 0; n < V4A_SPECTRUM_WINDOW; ++n) {
            int idx = spectrum->write_index - V4A_SPECTRUM_WINDOW + n;
            while (idx < 0) idx += V4A_SPECTRUM_WINDOW;
            float window = 0.5f - 0.5f * cosf(2.0f * 3.14159265359f * (float)n / (float)(V4A_SPECTRUM_WINDOW - 1));
            float sample = spectrum->ring[idx % V4A_SPECTRUM_WINDOW] * window;
            real += sample * cosf(omega * (float)n);
            imag -= sample * sinf(omega * (float)n);
        }
        float magnitude = sqrtf(real * real + imag * imag) / (float)V4A_SPECTRUM_WINDOW;
        float db = 20.0f * log10f(magnitude + 1e-6f);
        float normalized = (db + 72.0f) / 72.0f;
        if (normalized < 0.0f) normalized = 0.0f;
        if (normalized > 1.0f) normalized = 1.0f;
        spectrum->magnitudes[bin] = 0.65f * spectrum->magnitudes[bin] + 0.35f * normalized;
    }
}

void v4a_spectrum_get(v4a_spectrum_t* spectrum, float* out, int count) {
    if (!spectrum || !out || count <= 0) return;
    calculate_magnitudes(spectrum);
    int safe_count = count < V4A_SPECTRUM_BINS ? count : V4A_SPECTRUM_BINS;
    for (int i = 0; i < safe_count; ++i) out[i] = spectrum->magnitudes[i];
    for (int i = safe_count; i < count; ++i) out[i] = 0.0f;
}
