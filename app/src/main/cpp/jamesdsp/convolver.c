#include "convolver.h"
#include <ctype.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static float clampf_local(float value, float min_value, float max_value) {
    if (value < min_value) return min_value;
    if (value > max_value) return max_value;
    return value;
}

static int read_le16(const unsigned char* p) {
    return (int)((uint16_t)p[0] | ((uint16_t)p[1] << 8));
}

static int read_le32(const unsigned char* p) {
    return (int)((uint32_t)p[0] | ((uint32_t)p[1] << 8) | ((uint32_t)p[2] << 16) | ((uint32_t)p[3] << 24));
}

void v4a_convolver_init(v4a_convolver_t* convolver, int channels) {
    if (!convolver) return;
    memset(convolver, 0, sizeof(v4a_convolver_t));
    convolver->channels = channels > 0 && channels <= V4A_CONVOLVER_MAX_CHANNELS ? channels : 2;
    convolver->ir_length = 1;
    convolver->ir_left[0] = 1.0f;
    convolver->ir_right[0] = 1.0f;
}

void v4a_convolver_set_ir(v4a_convolver_t* convolver, const float* left, const float* right, int length) {
    if (!convolver || !left || length <= 0) return;
    int safe_length = length < V4A_CONVOLVER_MAX_IR ? length : V4A_CONVOLVER_MAX_IR;
    memset(convolver->ir_left, 0, sizeof(convolver->ir_left));
    memset(convolver->ir_right, 0, sizeof(convolver->ir_right));
    memset(convolver->ring, 0, sizeof(convolver->ring));
    convolver->ir_length = safe_length;
    convolver->ring_index = 0;
    float peak = 0.0f;
    for (int i = 0; i < safe_length; ++i) {
        float l = left[i];
        float r = right ? right[i] : l;
        if (l < 0.0f) { if (-l > peak) peak = -l; } else if (l > peak) peak = l;
        if (r < 0.0f) { if (-r > peak) peak = -r; } else if (r > peak) peak = r;
        convolver->ir_left[i] = l;
        convolver->ir_right[i] = r;
    }
    if (peak > 1.5f) {
        float scale = 1.0f / peak;
        for (int i = 0; i < safe_length; ++i) {
            convolver->ir_left[i] *= scale;
            convolver->ir_right[i] *= scale;
        }
    }
}

static int load_text_ir(v4a_convolver_t* convolver, const char* path) {
    FILE* file = fopen(path, "rb");
    if (!file) return 0;
    float left[V4A_CONVOLVER_MAX_IR];
    float right[V4A_CONVOLVER_MAX_IR];
    int count = 0;
    int stereo = 0;
    char line[256];
    while (fgets(line, sizeof(line), file) && count < V4A_CONVOLVER_MAX_IR) {
        char* cursor = line;
        while (*cursor && isspace((unsigned char)*cursor)) ++cursor;
        if (*cursor == 0 || *cursor == '#' || *cursor == ';') continue;
        char* end = NULL;
        float l = strtof(cursor, &end);
        if (end == cursor) continue;
        while (*end && (*end == ',' || isspace((unsigned char)*end))) ++end;
        char* end_right = NULL;
        float r = strtof(end, &end_right);
        if (end_right != end) stereo = 1; else r = l;
        left[count] = clampf_local(l, -4.0f, 4.0f);
        right[count] = clampf_local(r, -4.0f, 4.0f);
        ++count;
    }
    fclose(file);
    if (count <= 0) return 0;
    v4a_convolver_set_ir(convolver, left, stereo ? right : NULL, count);
    return 1;
}

static int load_wav_ir(v4a_convolver_t* convolver, const char* path) {
    FILE* file = fopen(path, "rb");
    if (!file) return 0;
    unsigned char header[12];
    if (fread(header, 1, sizeof(header), file) != sizeof(header)) {
        fclose(file);
        return 0;
    }
    if (memcmp(header, "RIFF", 4) != 0 || memcmp(header + 8, "WAVE", 4) != 0) {
        fclose(file);
        return 0;
    }
    int channels = 1;
    int bits_per_sample = 16;
    int audio_format = 1;
    long data_offset = -1;
    int data_size = 0;
    while (!feof(file)) {
        unsigned char chunk[8];
        if (fread(chunk, 1, sizeof(chunk), file) != sizeof(chunk)) break;
        int chunk_size = read_le32(chunk + 4);
        long next = ftell(file) + chunk_size + (chunk_size & 1);
        if (memcmp(chunk, "fmt ", 4) == 0) {
            unsigned char fmt[32];
            int to_read = chunk_size < (int)sizeof(fmt) ? chunk_size : (int)sizeof(fmt);
            if (fread(fmt, 1, (size_t)to_read, file) == (size_t)to_read && to_read >= 16) {
                audio_format = read_le16(fmt);
                channels = read_le16(fmt + 2);
                bits_per_sample = read_le16(fmt + 14);
            }
        } else if (memcmp(chunk, "data", 4) == 0) {
            data_offset = ftell(file);
            data_size = chunk_size;
            break;
        }
        fseek(file, next, SEEK_SET);
    }
    if (data_offset < 0 || data_size <= 0 || channels <= 0) {
        fclose(file);
        return 0;
    }
    fseek(file, data_offset, SEEK_SET);
    int bytes_per_sample = bits_per_sample / 8;
    int frames = data_size / (bytes_per_sample * channels);
    if (frames > V4A_CONVOLVER_MAX_IR) frames = V4A_CONVOLVER_MAX_IR;
    float left[V4A_CONVOLVER_MAX_IR];
    float right[V4A_CONVOLVER_MAX_IR];
    memset(left, 0, sizeof(left));
    memset(right, 0, sizeof(right));
    for (int frame = 0; frame < frames; ++frame) {
        float values[2] = {0.0f, 0.0f};
        for (int ch = 0; ch < channels; ++ch) {
            float sample = 0.0f;
            if (audio_format == 3 && bits_per_sample == 32) {
                float f = 0.0f;
                fread(&f, 1, sizeof(float), file);
                sample = f;
            } else if (bits_per_sample == 16) {
                unsigned char b[2];
                fread(b, 1, 2, file);
                int16_t v = (int16_t)((uint16_t)b[0] | ((uint16_t)b[1] << 8));
                sample = (float)v / 32768.0f;
            } else if (bits_per_sample == 24) {
                unsigned char b[3];
                fread(b, 1, 3, file);
                int32_t v = (int32_t)((uint32_t)b[0] | ((uint32_t)b[1] << 8) | ((uint32_t)b[2] << 16));
                if (v & 0x800000) v |= ~0xFFFFFF;
                sample = (float)v / 8388608.0f;
            } else {
                fseek(file, bytes_per_sample, SEEK_CUR);
            }
            if (ch < 2) values[ch] = sample;
        }
        left[frame] = values[0];
        right[frame] = channels > 1 ? values[1] : values[0];
    }
    fclose(file);
    if (frames <= 0) return 0;
    v4a_convolver_set_ir(convolver, left, channels > 1 ? right : NULL, frames);
    return 1;
}

int v4a_convolver_load_file(v4a_convolver_t* convolver, const char* path) {
    if (!convolver || !path) return 0;
    if (load_wav_ir(convolver, path)) return 1;
    return load_text_ir(convolver, path);
}

float v4a_convolver_process(v4a_convolver_t* convolver, int channel, float input) {
    if (!convolver || convolver->ir_length <= 0) return input;
    if (channel < 0 || channel >= convolver->channels || channel >= V4A_CONVOLVER_MAX_CHANNELS) return input;
    int base = convolver->ring_index * convolver->channels + channel;
    convolver->ring[base] = input;
    float y = 0.0f;
    for (int i = 0; i < convolver->ir_length; ++i) {
        int idx = convolver->ring_index - i;
        while (idx < 0) idx += V4A_CONVOLVER_MAX_IR;
        float ir = channel == 1 ? convolver->ir_right[i] : convolver->ir_left[i];
        y += convolver->ring[idx * convolver->channels + channel] * ir;
    }
    return y;
}

void v4a_convolver_advance(v4a_convolver_t* convolver) {
    if (!convolver) return;
    convolver->ring_index = (convolver->ring_index + 1) % V4A_CONVOLVER_MAX_IR;
}
