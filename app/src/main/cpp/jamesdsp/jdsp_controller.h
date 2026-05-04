#ifndef DEEPEYE_JDSP_CONTROLLER_H
#define DEEPEYE_JDSP_CONTROLLER_H

#ifdef __cplusplus
extern "C" {
#endif

typedef struct jdsp_s jdsp_t;

#define V4A_EFFECT_EQ 0
#define V4A_EFFECT_CONVOLVER 1
#define V4A_EFFECT_DDC 2
#define V4A_EFFECT_FET 3
#define V4A_EFFECT_TUBE 4
#define V4A_EFFECT_REVERB 5

jdsp_t* jdsp_init(int sample_rate, int channels);
void jdsp_free(jdsp_t* dsp);
void jdsp_set_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_eq_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_bass_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_width_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_compressor_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_reverb_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_convolver_enabled(jdsp_t* dsp, int enabled);
void jdsp_set_eq_gain(jdsp_t* dsp, int band, float gain_db);
void jdsp_set_eq_gains(jdsp_t* dsp, const float* gains, int count);
void jdsp_set_bass_boost(jdsp_t* dsp, float amount);
void jdsp_set_stereo_width(jdsp_t* dsp, float amount);
void jdsp_set_compressor(jdsp_t* dsp, float threshold_db, float ratio, float attack_ms, float release_ms);
void jdsp_set_reverb(jdsp_t* dsp, float room_size, float damping, float width, float wet_level);
void jdsp_set_limiter_ceiling(jdsp_t* dsp, float ceiling_db);
void jdsp_set_convolver_ir(jdsp_t* dsp, const float* left, const float* right, int length);
void jdsp_process(jdsp_t* dsp, const float* input, float* output, int frames);

void jdsp_v4a_set_enabled(jdsp_t* dsp, int enabled);
void jdsp_v4a_set_effect_enabled(jdsp_t* dsp, int effect_ordinal, int enabled);
void jdsp_v4a_set_eq_gains(jdsp_t* dsp, const float* gains, int count);
void jdsp_v4a_set_tube_warmth(jdsp_t* dsp, float warmth);
float jdsp_v4a_get_tube_warmth(jdsp_t* dsp);
int jdsp_v4a_load_convolver_ir(const char* path);
int jdsp_v4a_load_ddc_profile(const char* path);
void jdsp_v4a_set_fet_params(float attack_ms, float release_ms, float ratio, float threshold_db, float knee_db);
void jdsp_v4a_get_spectrum(float* out, int count);
void jdsp_v4a_process(jdsp_t* dsp, const float* input, float* output, int frames);

#ifdef __cplusplus
}
#endif

#endif
