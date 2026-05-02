#ifndef DEEPEYE_V4A_FET_COMPRESSOR_H
#define DEEPEYE_V4A_FET_COMPRESSOR_H

#ifdef __cplusplus
extern "C" {
#endif

typedef struct v4a_fet_s {
    int sample_rate;
    float attack_ms;
    float release_ms;
    float ratio;
    float threshold_db;
    float knee_db;
    float envelope;
} v4a_fet_t;

void v4a_fet_init(v4a_fet_t* fet, int sample_rate);
void v4a_fet_set_params(v4a_fet_t* fet, float attack_ms, float release_ms, float ratio, float threshold_db, float knee_db);
float v4a_fet_process(v4a_fet_t* fet, float input);

#ifdef __cplusplus
}
#endif

#endif
