#!/bin/bash
# watch_critical.sh
adb logcat -c
adb logcat | grep --color=always -E \
  "FATAL EXCEPTION|AndroidRuntime|signal 11|signal 6|SIGSEGV|SIGABRT|ANR |DeepEyeDSP|DeepEyeSafety|AudioTrack|MediaCodec|ExoPlayerImplInternal|StrictMode|Choreographer"
