#ifndef DEEPEYE_V4A_CONVOLVER_H
#define DEEPEYE_V4A_CONVOLVER_H

#ifdef __cplusplus
extern "C" {
#endif

#define V4A_CONVOLVER_MAX_IR 4096
#define V4A_CONVOLVER_MAX_CHANNELS 8

typedef struct v4a_convolver_s {
    int channels;
    int ir_length;
    int ring_index;
    float ir_left[V4A_CONVOLVER_MAX_IR];
    float ir_right[V4A_CONVOLVER_MAX_IR];
    float ring[V4A_CONVOLVER_MAX_IR * V4A_CONVOLVER_MAX_CHANNELS];
} v4a_convolver_t;

void v4a_convolver_init(v4a_convolver_t* convolver, int channels);
int v4a_convolver_load_file(v4a_convolver_t* convolver, const char* path);
void v4a_convolver_set_ir(v4a_convolver_t* convolver, const float* left, const float* right, int length);
float v4a_convolver_process(v4a_convolver_t* convolver, int channel, float input);
void v4a_convolver_advance(v4a_convolver_t* convolver);

#ifdef __cplusplus
}
#endif

#endif
