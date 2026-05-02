#include "jdsp_controller.h"
#include <math.h>
#include <stdlib.h>
#include <string.h>

#define JDSP_MAX_CHANNELS 8
#define JDSP_EQ_BANDS 10
#define JDSP_IR_MAX 2048

struct jdsp_s {
    int sample_rate;
    int channels;
    int enabled;
    int eq_enabled;
    int bass_enabled;
    int width_enabled;
    int compressor_enabled;
    int reverb_enabled;
    int convolver_enabled;
    float eq_gain[JDSP_EQ_BANDS];
    float bass_amount;
    float stereo_width;
    float comp_threshold_db;
    float comp_ratio;
    float comp_attack_ms;
    float comp_release_ms;
    float limiter_ceiling_db;
    float low_state[JDSP_MAX_CHANNELS];
    float high_state[JDSP_MAX_CHANNELS];
    float envelope;
    float* reverb_buffer;
    int reverb_size;
    int reverb_index;
    float reverb_room;
    float reverb_damping;
    float reverb_width;
    float reverb_wet;
    float ir_left[JDSP_IR_MAX];
    float ir_right[JDSP_IR_MAX];
    float* conv_ring;
    int ir_length;
    int conv_index;
};

static float clampf(float value, float min_value, float max_value) {
    if (value < min_value) return min_value;
    if (value > max_value) return max_value;
    return value;
}

static float db_to_gain(float db) {
    return powf(10.0f, db / 20.0f);
}

jdsp_t* jdsp_init(int sample_rate, int channels) {
    if (sample_rate <= 0 || channels <= 0 || channels > JDSP_MAX_CHANNELS) return NULL;
    jdsp_t* dsp = (jdsp_t*)calloc(1, sizeof(jdsp_t));
    if (!dsp) return NULL;
    dsp->sample_rate = sample_rate;
    dsp->channels = channels;
    dsp->enabled = 1;
    dsp->eq_enabled = 1;
    dsp->bass_enabled = 1;
    dsp->width_enabled = channels >= 2;
    dsp->compressor_enabled = 1;
    dsp->reverb_enabled = 0;
    dsp->convolver_enabled = 0;
    dsp->bass_amount = 0.18f;
    dsp->stereo_width = 0.08f;
    dsp->comp_threshold_db = -6.0f;
    dsp->comp_ratio = 2.0f;
    dsp->comp_attack_ms = 8.0f;
    dsp->comp_release_ms = 120.0f;
    dsp->limiter_ceiling_db = -0.8f;
    dsp->reverb_room = 0.32f;
    dsp->reverb_damping = 0.42f;
    dsp->reverb_width = 0.45f;
    dsp->reverb_wet = 0.08f;
    dsp->reverb_size = sample_rate * channels;
    if (dsp->reverb_size < channels * 64) dsp->reverb_size = channels * 64;
    dsp->reverb_buffer = (float*)calloc((size_t)dsp->reverb_size, sizeof(float));
    dsp->conv_ring = (float*)calloc((size_t)JDSP_IR_MAX * (size_t)channels, sizeof(float));
    if (!dsp->reverb_buffer || !dsp->conv_ring) {
        jdsp_free(dsp);
        return NULL;
    }
    return dsp;
}

void jdsp_free(jdsp_t* dsp) {
    if (!dsp) return;
    free(dsp->reverb_buffer);
    free(dsp->conv_ring);
    free(dsp);
}

void jdsp_set_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->enabled = enabled ? 1 : 0; }
void jdsp_set_eq_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->eq_enabled = enabled ? 1 : 0; }
void jdsp_set_bass_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->bass_enabled = enabled ? 1 : 0; }
void jdsp_set_width_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->width_enabled = enabled ? 1 : 0; }
void jdsp_set_compressor_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->compressor_enabled = enabled ? 1 : 0; }
void jdsp_set_reverb_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->reverb_enabled = enabled ? 1 : 0; }
void jdsp_set_convolver_enabled(jdsp_t* dsp, int enabled) { if (dsp) dsp->convolver_enabled = enabled ? 1 : 0; }

void jdsp_set_eq_gain(jdsp_t* dsp, int band, float gain_db) {
    if (!dsp || band < 0 || band >= JDSP_EQ_BANDS) return;
    dsp->eq_gain[band] = clampf(gain_db, -12.0f, 12.0f);
}

void jdsp_set_eq_gains(jdsp_t* dsp, const float* gains, int count) {
    if (!dsp || !gains) return;
    int safe_count = count < JDSP_EQ_BANDS ? count : JDSP_EQ_BANDS;
    for (int i = 0; i < safe_count; ++i) jdsp_set_eq_gain(dsp, i, gains[i]);
}

void jdsp_set_bass_boost(jdsp_t* dsp, float amount) { if (dsp) dsp->bass_amount = clampf(amount, 0.0f, 1.0f); }
void jdsp_set_stereo_width(jdsp_t* dsp, float amount) { if (dsp) dsp->stereo_width = clampf(amount, -0.6f, 0.8f); }

void jdsp_set_compressor(jdsp_t* dsp, float threshold_db, float ratio, float attack_ms, float release_ms) {
    if (!dsp) return;
    dsp->comp_threshold_db = clampf(threshold_db, -36.0f, 0.0f);
    dsp->comp_ratio = clampf(ratio, 1.0f, 12.0f);
    dsp->comp_attack_ms = clampf(attack_ms, 1.0f, 120.0f);
    dsp->comp_release_ms = clampf(release_ms, 20.0f, 1200.0f);
}

void jdsp_set_reverb(jdsp_t* dsp, float room_size, float damping, float width, float wet_level) {
    if (!dsp) return;
    dsp->reverb_room = clampf(room_size, 0.0f, 1.0f);
    dsp->reverb_damping = clampf(damping, 0.0f, 1.0f);
    dsp->reverb_width = clampf(width, 0.0f, 1.0f);
    dsp->reverb_wet = clampf(wet_level, 0.0f, 0.35f);
}

void jdsp_set_limiter_ceiling(jdsp_t* dsp, float ceiling_db) { if (dsp) dsp->limiter_ceiling_db = clampf(ceiling_db, -12.0f, 0.0f); }

void jdsp_set_convolver_ir(jdsp_t* dsp, const float* left, const float* right, int length) {
    if (!dsp || !left || length <= 0) return;
    dsp->ir_length = length < JDSP_IR_MAX ? length : JDSP_IR_MAX;
    for (int i = 0; i < dsp->ir_length; ++i) {
        dsp->ir_left[i] = left[i];
        dsp->ir_right[i] = right ? right[i] : left[i];
    }
    memset(dsp->conv_ring, 0, (size_t)JDSP_IR_MAX * (size_t)dsp->channels * sizeof(float));
    dsp->conv_index = 0;
}

static float process_channel_tone(jdsp_t* dsp, int channel, float x) {
    float low_alpha = 0.035f;
    float high_alpha = 0.22f;
    dsp->low_state[channel] += low_alpha * (x - dsp->low_state[channel]);
    dsp->high_state[channel] += high_alpha * (x - dsp->high_state[channel]);
    float low = dsp->low_state[channel];
    float high = x - dsp->high_state[channel];
    float mid = x - low - high;
    float low_gain = db_to_gain((dsp->eq_gain[0] + dsp->eq_gain[1] + dsp->eq_gain[2]) / 3.0f);
    float mid_gain = db_to_gain((dsp->eq_gain[3] + dsp->eq_gain[4] + dsp->eq_gain[5] + dsp->eq_gain[6]) / 4.0f);
    float high_gain = db_to_gain((dsp->eq_gain[7] + dsp->eq_gain[8] + dsp->eq_gain[9]) / 3.0f);
    float y = low * low_gain + mid * mid_gain + high * high_gain;
    if (dsp->bass_enabled) y += low * dsp->bass_amount;
    return y;
}

static void process_stereo_width(jdsp_t* dsp, float* left, float* right) {
    if (!dsp->width_enabled || dsp->channels < 2) return;
    float mid = (*left + *right) * 0.5f;
    float side = (*left - *right) * (0.5f + dsp->stereo_width);
    *left = mid + side;
    *right = mid - side;
}

static float process_compressor(jdsp_t* dsp, float x) {
    if (!dsp->compressor_enabled) return x;
    float abs_x = fabsf(x) + 1e-9f;
    float level_db = 20.0f * log10f(abs_x);
    float threshold = dsp->comp_threshold_db;
    float gain_db = 0.0f;
    if (level_db > threshold) {
        float compressed = threshold + (level_db - threshold) / dsp->comp_ratio;
        gain_db = compressed - level_db;
    }
    float target = db_to_gain(gain_db);
    float attack = expf(-1.0f / (0.001f * dsp->comp_attack_ms * (float)dsp->sample_rate));
    float release = expf(-1.0f / (0.001f * dsp->comp_release_ms * (float)dsp->sample_rate));
    float coeff = target < dsp->envelope ? attack : release;
    dsp->envelope = coeff * dsp->envelope + (1.0f - coeff) * target;
    if (dsp->envelope <= 0.0001f) dsp->envelope = 1.0f;
    return x * dsp->envelope;
}

static float process_reverb(jdsp_t* dsp, int channel, float x) {
    if (!dsp->reverb_enabled || !dsp->reverb_buffer) return x;
    int delay_frames = (int)((0.045f + dsp->reverb_room * 0.22f) * (float)dsp->sample_rate);
    int delay_samples = delay_frames * dsp->channels;
    if (delay_samples <= 0 || delay_samples >= dsp->reverb_size) delay_samples = dsp->channels * 64;
    int read = dsp->reverb_index - delay_samples + channel;
    while (read < 0) read += dsp->reverb_size;
    float delayed = dsp->reverb_buffer[read % dsp->reverb_size];
    int write = (dsp->reverb_index + channel) % dsp->reverb_size;
    dsp->reverb_buffer[write] = x + delayed * (0.25f + 0.55f * (1.0f - dsp->reverb_damping));
    return x * (1.0f - dsp->reverb_wet) + delayed * dsp->reverb_wet;
}

static float process_convolver(jdsp_t* dsp, int channel, float x) {
    if (!dsp->convolver_enabled || dsp->ir_length <= 0 || !dsp->conv_ring) return x;
    int base = dsp->conv_index * dsp->channels + channel;
    dsp->conv_ring[base] = x;
    float y = 0.0f;
    for (int i = 0; i < dsp->ir_length; ++i) {
        int idx = dsp->conv_index - i;
        while (idx < 0) idx += JDSP_IR_MAX;
        float ir = channel == 1 ? dsp->ir_right[i] : dsp->ir_left[i];
        y += dsp->conv_ring[idx * dsp->channels + channel] * ir;
    }
    return y;
}

void jdsp_process(jdsp_t* dsp, const float* input, float* output, int frames) {
    if (!dsp || !input || !output || frames <= 0) return;
    if (!dsp->enabled) {
        memcpy(output, input, (size_t)frames * (size_t)dsp->channels * sizeof(float));
        return;
    }
    float ceiling = db_to_gain(dsp->limiter_ceiling_db);
    for (int frame = 0; frame < frames; ++frame) {
        float frame_values[JDSP_MAX_CHANNELS];
        for (int ch = 0; ch < dsp->channels; ++ch) {
            float x = input[frame * dsp->channels + ch];
            if (dsp->eq_enabled || dsp->bass_enabled) x = process_channel_tone(dsp, ch, x);
            x = process_convolver(dsp, ch, x);
            x = process_reverb(dsp, ch, x);
            frame_values[ch] = x;
        }
        if (dsp->channels >= 2) process_stereo_width(dsp, &frame_values[0], &frame_values[1]);
        for (int ch = 0; ch < dsp->channels; ++ch) {
            float y = process_compressor(dsp, frame_values[ch]);
            y = clampf(y, -ceiling, ceiling);
            output[frame * dsp->channels + ch] = y;
        }
        dsp->reverb_index += dsp->channels;
        if (dsp->reverb_index >= dsp->reverb_size) dsp->reverb_index = 0;
        dsp->conv_index = (dsp->conv_index + 1) % JDSP_IR_MAX;
    }
}

#include "convolver.h"
#include "ddc_engine.h"
#include "fet_compressor.h"
#include "spectrum.h"

#define V4A_EFFECT_EQ 0
#define V4A_EFFECT_CONVOLVER 1
#define V4A_EFFECT_DDC 2
#define V4A_EFFECT_FET 3
#define V4A_EFFECT_TUBE 4
#define V4A_EFFECT_REVERB 5

typedef struct v4a_runtime_s {
    int initialized;
    int enabled;
    int effects[6];
    int sample_rate;
    int channels;
    float eq_gain[JDSP_EQ_BANDS];
    float eq_low_state[JDSP_MAX_CHANNELS];
    float eq_high_state[JDSP_MAX_CHANNELS];
    float tube_warmth;
    float reverb_buffer[48000 * 2];
    int reverb_size;
    int reverb_index;
    v4a_convolver_t convolver;
    v4a_ddc_t ddc;
    v4a_fet_t fet;
    v4a_spectrum_t spectrum;
} v4a_runtime_t;

static v4a_runtime_t g_v4a;

static void v4a_runtime_init(int sample_rate, int channels) {
    if (channels <= 0 || channels > JDSP_MAX_CHANNELS) channels = 2;
    if (sample_rate <= 0) sample_rate = 48000;
    memset(&g_v4a, 0, sizeof(g_v4a));
    g_v4a.initialized = 1;
    g_v4a.enabled = 1;
    g_v4a.sample_rate = sample_rate;
    g_v4a.channels = channels;
    g_v4a.effects[V4A_EFFECT_EQ] = 1;
    g_v4a.effects[V4A_EFFECT_FET] = 1;
    g_v4a.effects[V4A_EFFECT_TUBE] = 1;
    g_v4a.tube_warmth = 0.25f;
    g_v4a.reverb_size = sample_rate * 2;
    if (g_v4a.reverb_size > (int)(sizeof(g_v4a.reverb_buffer) / sizeof(g_v4a.reverb_buffer[0]))) {
        g_v4a.reverb_size = (int)(sizeof(g_v4a.reverb_buffer) / sizeof(g_v4a.reverb_buffer[0]));
    }
    if (g_v4a.reverb_size < channels * 128) g_v4a.reverb_size = channels * 128;
    v4a_convolver_init(&g_v4a.convolver, channels);
    v4a_ddc_init(&g_v4a.ddc, sample_rate, channels);
    v4a_fet_init(&g_v4a.fet, sample_rate);
    v4a_spectrum_init(&g_v4a.spectrum, sample_rate);
}

static void v4a_ensure_runtime(jdsp_t* dsp) {
    int sample_rate = dsp ? dsp->sample_rate : 48000;
    int channels = dsp ? dsp->channels : 2;
    if (!g_v4a.initialized || g_v4a.sample_rate != sample_rate || g_v4a.channels != channels) {
        v4a_runtime_init(sample_rate, channels);
    }
}

static float v4a_tube(float x, float warmth) {
    if (warmth <= 0.001f) return x;
    float drive = 1.0f + warmth * 5.5f;
    float y = tanhf(x * drive) / tanhf(drive);
    float even = y * y * (x >= 0.0f ? 1.0f : -1.0f) * 0.12f * warmth;
    return y * (1.0f - 0.18f * warmth) + even;
}

static float v4a_process_eq(int channel, float x) {
    float low_alpha = 1.0f - expf(-2.0f * 3.14159265359f * 170.0f / (float)g_v4a.sample_rate);
    float high_alpha = 1.0f - expf(-2.0f * 3.14159265359f * 4200.0f / (float)g_v4a.sample_rate);
    g_v4a.eq_low_state[channel] += low_alpha * (x - g_v4a.eq_low_state[channel]);
    g_v4a.eq_high_state[channel] += high_alpha * (x - g_v4a.eq_high_state[channel]);
    float low = g_v4a.eq_low_state[channel];
    float high = x - g_v4a.eq_high_state[channel];
    float mid = x - low - high;
    float low_gain = db_to_gain((g_v4a.eq_gain[0] + g_v4a.eq_gain[1] + g_v4a.eq_gain[2]) / 3.0f);
    float mid_gain = db_to_gain((g_v4a.eq_gain[3] + g_v4a.eq_gain[4] + g_v4a.eq_gain[5] + g_v4a.eq_gain[6]) / 4.0f);
    float high_gain = db_to_gain((g_v4a.eq_gain[7] + g_v4a.eq_gain[8] + g_v4a.eq_gain[9]) / 3.0f);
    return low * low_gain + mid * mid_gain + high * high_gain;
}

static float v4a_process_reverb(int channel, float x) {
    if (!g_v4a.effects[V4A_EFFECT_REVERB]) return x;
    int delay_frames = (int)(0.085f * (float)g_v4a.sample_rate);
    int delay_samples = delay_frames * g_v4a.channels;
    if (delay_samples <= 0 || delay_samples >= g_v4a.reverb_size) delay_samples = g_v4a.channels * 128;
    int read = g_v4a.reverb_index - delay_samples + channel;
    while (read < 0) read += g_v4a.reverb_size;
    float delayed = g_v4a.reverb_buffer[read % g_v4a.reverb_size];
    int write = (g_v4a.reverb_index + channel) % g_v4a.reverb_size;
    g_v4a.reverb_buffer[write] = x + delayed * 0.42f;
    return x * 0.86f + delayed * 0.14f;
}

void jdsp_v4a_set_enabled(jdsp_t* dsp, int enabled) {
    v4a_ensure_runtime(dsp);
    g_v4a.enabled = enabled ? 1 : 0;
}

void jdsp_v4a_set_effect_enabled(jdsp_t* dsp, int effect_ordinal, int enabled) {
    v4a_ensure_runtime(dsp);
    if (effect_ordinal < 0 || effect_ordinal >= 6) return;
    g_v4a.effects[effect_ordinal] = enabled ? 1 : 0;
}

void jdsp_v4a_set_eq_gains(jdsp_t* dsp, const float* gains, int count) {
    v4a_ensure_runtime(dsp);
    if (!gains) return;
    int safe_count = count < JDSP_EQ_BANDS ? count : JDSP_EQ_BANDS;
    for (int i = 0; i < safe_count; ++i) g_v4a.eq_gain[i] = clampf(gains[i], -12.0f, 12.0f);
}

void jdsp_v4a_set_tube_warmth(jdsp_t* dsp, float warmth) {
    v4a_ensure_runtime(dsp);
    g_v4a.tube_warmth = clampf(warmth, 0.0f, 1.0f);
}

int jdsp_v4a_load_convolver_ir(const char* path) {
    if (!g_v4a.initialized) v4a_runtime_init(48000, 2);
    return v4a_convolver_load_file(&g_v4a.convolver, path);
}

int jdsp_v4a_load_ddc_profile(const char* path) {
    if (!g_v4a.initialized) v4a_runtime_init(48000, 2);
    return v4a_ddc_load_file(&g_v4a.ddc, path);
}

void jdsp_v4a_set_fet_params(float attack_ms, float release_ms, float ratio, float threshold_db, float knee_db) {
    if (!g_v4a.initialized) v4a_runtime_init(48000, 2);
    v4a_fet_set_params(&g_v4a.fet, attack_ms, release_ms, ratio, threshold_db, knee_db);
}

void jdsp_v4a_get_spectrum(float* out, int count) {
    if (!g_v4a.initialized) v4a_runtime_init(48000, 2);
    v4a_spectrum_get(&g_v4a.spectrum, out, count);
}

void jdsp_v4a_process(jdsp_t* dsp, const float* input, float* output, int frames) {
    if (!dsp || !input || !output || frames <= 0) return;
    v4a_ensure_runtime(dsp);
    if (!g_v4a.enabled) {
        memcpy(output, input, (size_t)frames * (size_t)dsp->channels * sizeof(float));
        for (int frame = 0; frame < frames; ++frame) {
            float mono = 0.0f;
            for (int ch = 0; ch < dsp->channels; ++ch) mono += input[frame * dsp->channels + ch];
            v4a_spectrum_push(&g_v4a.spectrum, mono / (float)dsp->channels);
        }
        return;
    }
    float ceiling = db_to_gain(-0.8f);
    for (int frame = 0; frame < frames; ++frame) {
        float frame_values[JDSP_MAX_CHANNELS];
        float mono = 0.0f;
        for (int ch = 0; ch < dsp->channels; ++ch) {
            float x = input[frame * dsp->channels + ch];
            if (g_v4a.effects[V4A_EFFECT_EQ]) x = v4a_process_eq(ch, x);
            if (g_v4a.effects[V4A_EFFECT_DDC]) x = v4a_ddc_process(&g_v4a.ddc, ch, x);
            if (g_v4a.effects[V4A_EFFECT_CONVOLVER]) x = v4a_convolver_process(&g_v4a.convolver, ch, x);
            if (g_v4a.effects[V4A_EFFECT_REVERB]) x = v4a_process_reverb(ch, x);
            if (g_v4a.effects[V4A_EFFECT_TUBE]) x = v4a_tube(x, g_v4a.tube_warmth);
            frame_values[ch] = x;
        }
        if (dsp->channels >= 2 && g_v4a.effects[V4A_EFFECT_CONVOLVER]) {
            float cross = 0.06f;
            float left = frame_values[0];
            float right = frame_values[1];
            frame_values[0] = left * (1.0f - cross) + right * cross;
            frame_values[1] = right * (1.0f - cross) + left * cross;
        }
        for (int ch = 0; ch < dsp->channels; ++ch) {
            float y = frame_values[ch];
            if (g_v4a.effects[V4A_EFFECT_FET]) y = v4a_fet_process(&g_v4a.fet, y);
            y = clampf(y, -ceiling, ceiling);
            output[frame * dsp->channels + ch] = y;
            mono += y;
        }
        v4a_spectrum_push(&g_v4a.spectrum, mono / (float)dsp->channels);
        v4a_convolver_advance(&g_v4a.convolver);
        g_v4a.reverb_index += g_v4a.channels;
        if (g_v4a.reverb_index >= g_v4a.reverb_size) g_v4a.reverb_index = 0;
    }
}
