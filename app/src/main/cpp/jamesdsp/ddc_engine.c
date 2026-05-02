#include "ddc_engine.h"
#include <ctype.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static float clampf_local(float value, float min_value, float max_value) {
    if (value < min_value) return min_value;
    if (value > max_value) return max_value;
    return value;
}

static float db_to_gain_local(float db) {
    return powf(10.0f, db / 20.0f);
}

static float interpolate_gain(const v4a_ddc_t* ddc, float frequency) {
    if (!ddc || ddc->point_count <= 0) return 0.0f;
    if (frequency <= ddc->frequency[0]) return ddc->gain_db[0];
    for (int i = 1; i < ddc->point_count; ++i) {
        if (frequency <= ddc->frequency[i]) {
            float f0 = ddc->frequency[i - 1];
            float f1 = ddc->frequency[i];
            float t = (frequency - f0) / (f1 - f0 + 1e-6f);
            return ddc->gain_db[i - 1] + t * (ddc->gain_db[i] - ddc->gain_db[i - 1]);
        }
    }
    return ddc->gain_db[ddc->point_count - 1];
}

static void recalculate_bands(v4a_ddc_t* ddc) {
    const float low_freq = 90.0f;
    const float mid_freq = 1600.0f;
    const float high_freq = 8500.0f;
    ddc->band_gain[0] = db_to_gain_local(clampf_local(interpolate_gain(ddc, low_freq), -12.0f, 12.0f));
    ddc->band_gain[1] = db_to_gain_local(clampf_local(interpolate_gain(ddc, mid_freq), -12.0f, 12.0f));
    ddc->band_gain[2] = db_to_gain_local(clampf_local(interpolate_gain(ddc, high_freq), -12.0f, 12.0f));
}

void v4a_ddc_init(v4a_ddc_t* ddc, int sample_rate, int channels) {
    if (!ddc) return;
    memset(ddc, 0, sizeof(v4a_ddc_t));
    ddc->sample_rate = sample_rate > 0 ? sample_rate : 48000;
    ddc->channels = channels > 0 && channels <= V4A_DDC_MAX_CHANNELS ? channels : 2;
    ddc->point_count = 3;
    ddc->frequency[0] = 80.0f;
    ddc->gain_db[0] = 0.0f;
    ddc->frequency[1] = 1600.0f;
    ddc->gain_db[1] = 0.0f;
    ddc->frequency[2] = 9000.0f;
    ddc->gain_db[2] = 0.0f;
    recalculate_bands(ddc);
}

int v4a_ddc_load_file(v4a_ddc_t* ddc, const char* path) {
    if (!ddc || !path) return 0;
    FILE* file = fopen(path, "rb");
    if (!file) return 0;
    float frequency[V4A_DDC_MAX_POINTS];
    float gain[V4A_DDC_MAX_POINTS];
    int count = 0;
    char line[256];
    while (fgets(line, sizeof(line), file) && count < V4A_DDC_MAX_POINTS) {
        char* cursor = line;
        while (*cursor && isspace((unsigned char)*cursor)) ++cursor;
        if (*cursor == 0 || *cursor == '#' || *cursor == ';') continue;
        char* end = NULL;
        float f = strtof(cursor, &end);
        if (end == cursor) continue;
        while (*end && (*end == ',' || isspace((unsigned char)*end))) ++end;
        char* end_gain = NULL;
        float g = strtof(end, &end_gain);
        if (end_gain == end) continue;
        frequency[count] = clampf_local(f, 10.0f, 24000.0f);
        gain[count] = clampf_local(g, -18.0f, 18.0f);
        ++count;
    }
    fclose(file);
    if (count <= 0) return 0;
    for (int i = 0; i < count; ++i) {
        ddc->frequency[i] = frequency[i];
        ddc->gain_db[i] = gain[i];
    }
    ddc->point_count = count;
    recalculate_bands(ddc);
    return 1;
}

float v4a_ddc_process(v4a_ddc_t* ddc, int channel, float input) {
    if (!ddc || channel < 0 || channel >= ddc->channels || channel >= V4A_DDC_MAX_CHANNELS) return input;
    float low_alpha = 1.0f - expf(-2.0f * 3.14159265359f * 180.0f / (float)ddc->sample_rate);
    float high_alpha = 1.0f - expf(-2.0f * 3.14159265359f * 3800.0f / (float)ddc->sample_rate);
    ddc->low_state[channel] += low_alpha * (input - ddc->low_state[channel]);
    ddc->high_state[channel] += high_alpha * (input - ddc->high_state[channel]);
    float low = ddc->low_state[channel];
    float high = input - ddc->high_state[channel];
    float mid = input - low - high;
    return low * ddc->band_gain[0] + mid * ddc->band_gain[1] + high * ddc->band_gain[2];
}
