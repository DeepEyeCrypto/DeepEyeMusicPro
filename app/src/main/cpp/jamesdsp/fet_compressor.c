#include "fet_compressor.h"
#include <math.h>
#include <string.h>

static float clampf_local(float value, float min_value, float max_value) {
    if (value < min_value) return min_value;
    if (value > max_value) return max_value;
    return value;
}

static float db_to_gain_local(float db) {
    return powf(10.0f, db / 20.0f);
}

void v4a_fet_init(v4a_fet_t* fet, int sample_rate) {
    if (!fet) return;
    memset(fet, 0, sizeof(v4a_fet_t));
    fet->sample_rate = sample_rate > 0 ? sample_rate : 48000;
    fet->attack_ms = 6.0f;
    fet->release_ms = 120.0f;
    fet->ratio = 2.2f;
    fet->threshold_db = -10.0f;
    fet->knee_db = 6.0f;
    fet->envelope = 1.0f;
}

void v4a_fet_set_params(v4a_fet_t* fet, float attack_ms, float release_ms, float ratio, float threshold_db, float knee_db) {
    if (!fet) return;
    fet->attack_ms = clampf_local(attack_ms, 0.2f, 100.0f);
    fet->release_ms = clampf_local(release_ms, 10.0f, 1000.0f);
    fet->ratio = clampf_local(ratio, 1.0f, 20.0f);
    fet->threshold_db = clampf_local(threshold_db, -48.0f, 0.0f);
    fet->knee_db = clampf_local(knee_db, 0.0f, 24.0f);
}

float v4a_fet_process(v4a_fet_t* fet, float input) {
    if (!fet) return input;
    float abs_x = fabsf(input) + 1e-9f;
    float level_db = 20.0f * log10f(abs_x);
    float over_db = level_db - fet->threshold_db;
    float compressed_over = over_db;
    if (fet->knee_db > 0.001f) {
        float half_knee = fet->knee_db * 0.5f;
        if (over_db <= -half_knee) {
            compressed_over = over_db;
        } else if (over_db >= half_knee) {
            compressed_over = over_db / fet->ratio;
        } else {
            float x = over_db + half_knee;
            compressed_over = over_db + (1.0f / fet->ratio - 1.0f) * x * x / (2.0f * fet->knee_db);
        }
    } else if (over_db > 0.0f) {
        compressed_over = over_db / fet->ratio;
    }
    float gain_db = over_db > -fet->knee_db * 0.5f ? compressed_over - over_db : 0.0f;
    float target = db_to_gain_local(gain_db);
    float attack = expf(-1.0f / (0.001f * fet->attack_ms * (float)fet->sample_rate));
    float release = expf(-1.0f / (0.001f * fet->release_ms * (float)fet->sample_rate));
    float coeff = target < fet->envelope ? attack : release;
    fet->envelope = coeff * fet->envelope + (1.0f - coeff) * target;
    if (fet->envelope < 0.0001f || fet->envelope != fet->envelope) fet->envelope = 1.0f;
    return input * fet->envelope;
}
