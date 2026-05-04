#ifndef DEEPEYE_TUBE_SIM_H
#define DEEPEYE_TUBE_SIM_H

#include <stdbool.h>

typedef struct {
    float warmth;
    bool enabled;
    float drive, bias, mix;
    float lp_coeff, lp_state[2];
    float hp_coeff, hp_state[2];
} tube_sim_t;

void tube_sim_init(tube_sim_t* tube);
void tube_sim_set_warmth(tube_sim_t* tube, float warmth);
float tube_sim_process_sample(tube_sim_t* tube, int ch, float sample);
void tube_sim_process(tube_sim_t* tube, float* left, float* right, int frames);

#endif
