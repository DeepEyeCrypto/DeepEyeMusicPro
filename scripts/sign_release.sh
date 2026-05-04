#!/usr/bin/env bash
# ══════════════════════════════════════════════════════════════════
# DeepEyeMusicPro — Local Release Signing Script
# Usage: ./scripts/sign_release.sh
#
# Prerequisites:
#   - ANDROID_HOME or ANDROID_SDK_ROOT set
#   - Release keystore generated (see docs/RELEASE_GUIDE.md)
#   - signing.properties in project root (not committed)
# ══════════════════════════════════════════════════════════════════
set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
SIGNING_PROPS="$PROJECT_DIR/signing.properties"

echo -e "${CYAN}═══════════════════════════════════════════════════${NC}"
echo -e "${CYAN} DeepEyeMusicPro — Release Signing${NC}"
echo -e "${CYAN}═══════════════════════════════════════════════════${NC}"
echo ""

# ── Load signing config ──
if [ ! -f "$SIGNING_PROPS" ]; then
    echo -e "${RED}❌ signing.properties not found at $SIGNING_PROPS${NC}"
    echo ""
    echo "Create it with:"
    echo "  STORE_FILE=/path/to/deepeye-release.keystore"
    echo "  STORE_PASSWORD=your_store_password"
    echo "  KEY_ALIAS=deepeye"
    echo "  KEY_PASSWORD=your_key_password"
    exit 1
fi

source "$SIGNING_PROPS"

for VAR in STORE_FILE STORE_PASSWORD KEY_ALIAS KEY_PASSWORD; do
    if [ -z "${!VAR:-}" ]; then
        echo -e "${RED}❌ $VAR not set in signing.properties${NC}"
        exit 1
    fi
done

if [ ! -f "$STORE_FILE" ]; then
    echo -e "${RED}❌ Keystore not found: $STORE_FILE${NC}"
    exit 1
fi

# ── Locate build tools ──
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
if [ -z "$SDK_ROOT" ]; then
    echo -e "${RED}❌ ANDROID_HOME or ANDROID_SDK_ROOT not set${NC}"
    exit 1
fi

BUILD_TOOLS_DIR=$(ls -d "$SDK_ROOT/build-tools/"* 2>/dev/null | sort -V | tail -1)
APKSIGNER="$BUILD_TOOLS_DIR/apksigner"
ZIPALIGN="$BUILD_TOOLS_DIR/zipalign"

if [ ! -f "$APKSIGNER" ]; then
    echo -e "${RED}❌ apksigner not found in $BUILD_TOOLS_DIR${NC}"
    exit 1
fi

# ── Build ──
echo -e "${CYAN}▶ Building release APK...${NC}"
cd "$PROJECT_DIR"
./gradlew assembleRelease --no-daemon -q

UNSIGNED_APK="$PROJECT_DIR/app/build/outputs/apk/release/app-release-unsigned.apk"
RELEASE_APK="$PROJECT_DIR/app/build/outputs/apk/release/app-release.apk"

# If Gradle already signed it (signingConfig set), use that
if [ -f "$RELEASE_APK" ]; then
    echo -e "${GREEN}✅ Gradle-signed APK found, verifying...${NC}"
    "$APKSIGNER" verify --verbose "$RELEASE_APK" && echo -e "${GREEN}✅ Signature valid${NC}" || echo -e "${RED}❌ Signature invalid${NC}"
elif [ -f "$UNSIGNED_APK" ]; then
    ALIGNED_APK="$PROJECT_DIR/app/build/outputs/apk/release/app-release-aligned.apk"

    echo -e "${CYAN}▶ Aligning APK...${NC}"
    "$ZIPALIGN" -v -p 4 "$UNSIGNED_APK" "$ALIGNED_APK"

    echo -e "${CYAN}▶ Signing APK...${NC}"
    "$APKSIGNER" sign \
        --ks "$STORE_FILE" \
        --ks-key-alias "$KEY_ALIAS" \
        --ks-pass "pass:$STORE_PASSWORD" \
        --key-pass "pass:$KEY_PASSWORD" \
        --v2-signing-enabled true \
        --v3-signing-enabled true \
        --out "$RELEASE_APK" \
        "$ALIGNED_APK"

    echo -e "${CYAN}▶ Verifying...${NC}"
    "$APKSIGNER" verify --verbose "$RELEASE_APK"
    echo -e "${GREEN}✅ Signed: $RELEASE_APK${NC}"

    rm -f "$ALIGNED_APK"
else
    echo -e "${RED}❌ No APK found to sign${NC}"
    exit 1
fi

# ── Also build AAB ──
echo ""
echo -e "${CYAN}▶ Building release AAB...${NC}"
./gradlew bundleRelease --no-daemon -q
AAB="$PROJECT_DIR/app/build/outputs/bundle/release/app-release.aab"
if [ -f "$AAB" ]; then
    echo -e "${GREEN}✅ AAB: $AAB${NC}"
else
    echo -e "${RED}❌ AAB build failed${NC}"
fi

echo ""
echo -e "${GREEN}═══════════════════════════════════════════════════${NC}"
echo -e "${GREEN} Release artifacts ready!${NC}"
echo -e "${GREEN}═══════════════════════════════════════════════════${NC}"
echo "  APK: $RELEASE_APK"
echo "  AAB: $AAB"
echo ""
echo "Next: Run ./scripts/verify_release.sh"
