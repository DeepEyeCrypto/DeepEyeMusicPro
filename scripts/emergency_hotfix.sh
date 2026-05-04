#!/usr/bin/env bash
# ══════════════════════════════════════════════════════════════════
# DeepEyeMusicPro — Emergency Hotfix Deployment Script
# ══════════════════════════════════════════════════════════════════

set -e

echo "🚨 EMERGENCY HOTFIX PROTOCOL INITIATED 🚨"

# 1. Update Version Code
BUILD_FILE="app/build.gradle"
CURRENT_VC=$(grep "versionCode" "$BUILD_FILE" | awk '{print $2}')
NEW_VC=$((CURRENT_VC + 1))
sed -i.bak "s/versionCode $CURRENT_VC/versionCode $NEW_VC/" "$BUILD_FILE"
echo "[1/4] Bumped versionCode from $CURRENT_VC to $NEW_VC."

# 2. Safety Disable Flags
# In an actual emergency, we might inject a BuildConfig flag or change a default to disable 
# the crashing feature (e.g., Native DSP or Visualizer).
# sed -i.bak "s/val ENABLE_DSP = true/val ENABLE_DSP = false/" app/src/main/java/com/deepeye/musicpro/Config.kt

# 3. Clean and Build Release
echo "[2/4] Building hotfix release bundle..."
./gradlew clean :app:bundleRelease --no-daemon

# 4. Sign Release
echo "[3/4] Signing release bundle..."
./scripts/sign_release.sh

# 5. Fastlane Emergency Deploy (5% Staged Rollout)
echo "[4/4] Deploying to Play Console via Fastlane (5% staged rollout)..."
# Use Fastlane to deploy immediately to production track at 5%
cd fastlane
fastlane production rollout:0.05
cd ..

echo "✅ HOTFIX DEPLOYED (versionCode $NEW_VC)."
echo "Monitor Google Play Console & Crashlytics for the next 24 hours before increasing rollout."
