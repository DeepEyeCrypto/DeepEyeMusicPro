#ifndef DEEPEYE_V4A_DDC_ENGINE_H
#define DEEPEYE_V4A_DDC_ENGINE_H

#ifdef __cplusplus
extern "C" {
#endif

#define V4A_DDC_MAX_POINTS 64
#define V4A_DDC_MAX_CHANNELS 8

typedef struct v4a_ddc_s {
    int sample_rate;
    int channels;
    int point_count;
    float frequency[V4A_DDC_MAX_POINTS];
    float gain_db[V4A_DDC_MAX_POINTS];
    float band_gain[3];
    float low_state[V4A_DDC_MAX_CHANNELS];
    float high_state[V4A_DDC_MAX_CHANNELS];
} v4a_ddc_t;

void v4a_ddc_init(v4a_ddc_t* ddc, int sample_rate, int channels);
int v4a_ddc_load_file(v4a_ddc_t* ddc, const char* path);
float v4a_ddc_process(v4a_ddc_t* ddc, int channel, float input);

#ifdef __cplusplus
}
#endif

#endif
