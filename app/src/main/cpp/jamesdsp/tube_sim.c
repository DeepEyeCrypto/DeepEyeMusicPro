#include "tube_sim.h"
#include <math.h>
#include <string.h>

static inline float soft_clip(float x) {
    if (x > 1.0f) return 1.0f;
    if (x < -1.0f) return -1.0f;
    float x2 = x * x;
    return x * (1.0f + x2 * (-0.333333f + x2 * 0.133333f));
}

static inline float asym_saturate(float s, float drive, float bias) {
    float b = s * drive + bias;
    float sat = soft_clip(b);
    return sat - soft_clip(bias);
}

void tube_sim_init(tube_sim_t* tube) {
    memset(tube, 0, sizeof(tube_sim_t));
    tube->warmth = 0.0f;
    tube->enabled = false;
    tube->lp_coeff = 0.15f;
    tube->hp_coeff = 0.995f;
}

void tube_sim_set_warmth(tube_sim_t* tube, float warmth) {
    tube->warmth = warmth;
    tube->enabled = (warmth > 0.005f);
    tube->drive = 1.0f + warmth * 4.0f;    // 1x to 5x
    tube->bias = warmth * 0.15f;            // 0 to 0.15 (asymmetry)
    tube->mix = warmth * 0.6f;              // 0% to 60% wet
}

float tube_sim_process_sample(tube_sim_t* tube, int ch, float sample) {
    if (ch >= 2) ch = 0;
    
    // Gentle lowpass before saturation
    float* lp = &tube->lp_state[ch];
    *lp += tube->lp_coeff * (sample - *lp);
    
    // Asymmetric saturation = even + odd harmonics (analog warmth)
    float saturated = asym_saturate(*lp, tube->drive, tube->bias);
    
    // DC blocker
    float* hp = &tube->hp_state[ch];
    *hp = tube->hp_coeff * (*hp + saturated - *hp);
    
    // Dry/Wet mix
    return sample * (1.0f - tube->mix) + *hp * tube->mix;
}

void tube_sim_process(tube_sim_t* tube, float* left, float* right, int frames) {
    if (!tube->enabled || tube->warmth <= 0.0f) return;
    
    for (int i = 0; i < frames; i++) {
        left[i] = tube_sim_process_sample(tube, 0, left[i]);
        right[i] = tube_sim_process_sample(tube, 1, right[i]);
    }
}
