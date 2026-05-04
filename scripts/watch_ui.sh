#!/bin/bash
# watch_ui.sh
adb logcat | grep --color=always -E \
  "Choreographer|Skipped | frames|InputDispatcher|WindowManager|DeepEye|Compose"
