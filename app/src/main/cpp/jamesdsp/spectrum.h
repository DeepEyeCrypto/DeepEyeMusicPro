#ifndef DEEPEYE_V4A_SPECTRUM_H
#define DEEPEYE_V4A_SPECTRUM_H

#ifdef __cplusplus
extern "C" {
#endif

#define V4A_SPECTRUM_BINS 32
#define V4A_SPECTRUM_WINDOW 1024

typedef struct v4a_spectrum_s {
    int sample_rate;
    int write_index;
    int filled;
    float ring[V4A_SPECTRUM_WINDOW];
    float magnitudes[V4A_SPECTRUM_BINS];
} v4a_spectrum_t;

void v4a_spectrum_init(v4a_spectrum_t* spectrum, int sample_rate);
void v4a_spectrum_push(v4a_spectrum_t* spectrum, float mono_sample);
void v4a_spectrum_get(v4a_spectrum_t* spectrum, float* out, int count);

#ifdef __cplusplus
}
#endif

#endif
