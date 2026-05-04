cmake_minimum_required(VERSION 3.22.1)
project("deepeye_dsp")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fexceptions")

# Find Oboe
find_package(oboe REQUIRED CONFIG)

# ── Build Type Configuration ──
if(CMAKE_BUILD_TYPE STREQUAL "Debug")
    # Debug build: standard symbols, no sanitizers (to avoid libclang_rt dependency on device)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -O1 -g")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O1 -g")
    message(STATUS "DeepEye DSP: Standard Debug build (No Sanitizers)")
elseif(CMAKE_BUILD_TYPE STREQUAL "Fuzz")
    # Fuzzing build: libFuzzer + ASAN
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -O1 -g -fsanitize=fuzzer,address,undefined -fno-omit-frame-pointer")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O1 -g -fsanitize=fuzzer,address,undefined -fno-omit-frame-pointer")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -fsanitize=fuzzer,address,undefined")
    message(STATUS "DeepEye DSP: FUZZER + ASAN + UBSAN enabled (Fuzzing build)")
else()
    # Release: Maximum optimization, fast-math, denormal FTZ
    set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -O3 -ffast-math -fomit-frame-pointer")
    set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -O3 -ffast-math -fomit-frame-pointer")
    message(STATUS "DeepEye DSP: Release build with denormal FTZ via -ffast-math")
endif()

# ── Security flags ──
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fstack-protector-strong -D_FORTIFY_SOURCE=2")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fstack-protector-strong -D_FORTIFY_SOURCE=2")

# ── Source files ──
add_library(${PROJECT_NAME} SHARED
    jni_bridge.cpp
    dsp/dsp_engine.cpp
    dsp/fft_capture.cpp
    oboe_engine.cpp
)

target_include_directories(${PROJECT_NAME} PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
)

find_library(log-lib log)
target_link_libraries(${PROJECT_NAME} 
    ${log-lib}
    oboe::oboe
)

# ── Fuzz target (only in Fuzz build) ──
if(CMAKE_BUILD_TYPE STREQUAL "Fuzz")
    add_executable(
        fuzz_dsp
        fuzzing/FuzzTargetDSP.cpp
        dsp/dsp_engine.cpp
    )
    target_link_libraries(fuzz_dsp log)
endif()
