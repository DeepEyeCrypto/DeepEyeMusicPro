// DeepEyeMusicPro — libFuzzer DSP Engine Fuzz Target
// Oracle checks:
// - No NaN/Inf in output (filter stability)
// - No buffer overruns (ASAN catches)
// - No infinite loops (libFuzzer timeout catches)
// - Denormal-safe CPU usage

#include <stdint.h>
#include <stddef.h>
#include <vector>
#include <cmath>
#include <cstring>
#include "dsp/dsp_engine.h"
#include "security/NativeSafetyWrapper.h"

using namespace de_security;

extern "C" int LLVMFuzzerTestOneInput(const uint8_t *data, size_t size) {
    // Minimum data for parameters
    if (size < 32) return 0;

    // ── 1. Initialize engine ──
    // Use fixed 48k/2ch for fuzzing core logic
    DSPEngine engine(48000, 2);

    // ── 2. Decode fuzzer parameters ──
    float preampDb      = ((float)(data[0]) / 255.0f) * 60.0f - 30.0f;
    float eqBand0Gain   = ((float)(data[1]) / 255.0f) * 48.0f - 24.0f;
    float subIntensity  = (float)(data[2]) / 255.0f;
    float ceiling       = ((float)(data[3]) / 255.0f) * -30.0f;

    engine.setPreamp(preampDb);
    engine.setEqGain(0, eqBand0Gain);
    engine.setSubIntensity(subIntensity);
    engine.setLimiterCeiling(ceiling);

    // ── 3. Decode remaining data as PCM ──
    size_t pcmBytes = size - 4;
    int frames = static_cast<int>(pcmBytes / (2 * sizeof(float)));
    if (frames <= 0) return 0;
    if (frames > 1024) frames = 1024; // Limit frames for fuzz speed

    std::vector<float> input(frames * 2);
    std::vector<float> output(frames * 2);

    const uint8_t* pcmPtr = data + 4;
    std::memcpy(input.data(), pcmPtr, frames * 2 * sizeof(float));

    // Sanitize input to avoid immediate NaN crashes in process
    sanitizeBuffer(input.data(), frames * 2);

    // ── 4. Trigger DSP processing ──
    engine.process(input.data(), output.data(), frames);

    // ── 5. Oracle: Check output validity ──
    for (int i = 0; i < frames * 2; i++) {
        if (isInvalidFloat(output[i])) {
            // Found instability!
            __builtin_trap();
        }
    }

    return 0;
}
