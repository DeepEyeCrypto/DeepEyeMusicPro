cmake_minimum_required(VERSION 3.22.1)
project("deepeye_dsp")

set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -O3 -ffast-math -Wall -Wextra")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3 -ffast-math -Wall -Wextra")

add_library(
    deepeye_dsp
    SHARED
    jni_bridge.cpp
    jamesdsp/jdsp_controller.c
    jamesdsp/convolver.c
    jamesdsp/ddc_engine.c
    jamesdsp/fet_compressor.c
    jamesdsp/spectrum.c
)

find_library(log-lib log)

target_include_directories(deepeye_dsp PRIVATE jamesdsp)
target_link_libraries(deepeye_dsp ${log-lib} m)
