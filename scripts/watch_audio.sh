#!/bin/bash
# watch_audio.sh
adb logcat -c
adb logcat | grep --color=always -E \
  "AudioFlinger|AudioTrack|AudioPolicy|MediaCodec|ExoPlayerImpl|DeepEyeDSP|NativeDSP|AudioSecurity"
