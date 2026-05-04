#!/usr/bin/env bash
# ══════════════════════════════════════════════════════════════════
# DeepEyeMusicPro — Release APK/AAB Verification Script
# Run after building release: ./scripts/verify_release.sh
# ══════════════════════════════════════════════════════════════════
set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
APK="$PROJECT_DIR/app/build/outputs/apk/release/app-release.apk"
AAB="$PROJECT_DIR/app/build/outputs/bundle/release/app-release.aab"
MAPPING="$PROJECT_DIR/app/build/outputs/mapping/release/mapping.txt"

PASS=0
FAIL=0
WARN=0

pass() { echo -e "${GREEN}✅ $1${NC}"; ((PASS++)); }
fail() { echo -e "${RED}❌ $1${NC}"; ((FAIL++)); }
warn() { echo -e "${YELLOW}⚠️  $1${NC}"; ((WARN++)); }

echo "═══════════════════════════════════════════════════"
echo " DeepEyeMusicPro Release Verification"
echo "═══════════════════════════════════════════════════"
echo ""

# ── Detect artifact ──
ARTIFACT=""
if [ -f "$APK" ]; then
    ARTIFACT="$APK"
    echo "📦 Verifying APK: $APK"
elif [ -f "$AAB" ]; then
    ARTIFACT="$AAB"
    echo "📦 Verifying AAB: $AAB"
else
    fail "No release APK or AAB found. Run './gradlew assembleRelease' or './gradlew bundleRelease' first."
    exit 1
fi
echo ""

# ── 1. Size check ──
echo "── Size ──"
if [ -f "$APK" ]; then
    SIZE=$(stat -f%z "$APK" 2>/dev/null || stat -c%s "$APK")
    SIZE_MB=$(echo "scale=1; $SIZE / 1048576" | bc)
    MAX_SIZE=52428800  # 50 MB
    if [ "$SIZE" -gt "$MAX_SIZE" ]; then
        fail "APK too large: ${SIZE_MB}MB (limit: 50MB)"
    else
        pass "APK size: ${SIZE_MB}MB"
    fi
fi
if [ -f "$AAB" ]; then
    AAB_SIZE=$(stat -f%z "$AAB" 2>/dev/null || stat -c%s "$AAB")
    AAB_SIZE_MB=$(echo "scale=1; $AAB_SIZE / 1048576" | bc)
    MAX_AAB=157286400  # 150 MB
    if [ "$AAB_SIZE" -gt "$MAX_AAB" ]; then
        fail "AAB too large: ${AAB_SIZE_MB}MB (limit: 150MB)"
    else
        pass "AAB size: ${AAB_SIZE_MB}MB"
    fi
fi
echo ""

# ── 2. Native libs ──
echo "── Native Libraries ──"
if [ -f "$APK" ]; then
    for ABI in arm64-v8a armeabi-v7a x86_64; do
        if unzip -l "$APK" 2>/dev/null | grep -q "lib/$ABI/libdeepeye_dsp.so"; then
            pass "$ABI: libdeepeye_dsp.so present"
        else
            warn "$ABI: libdeepeye_dsp.so missing"
        fi
    done
fi
echo ""

# ── 3. Signature ──
echo "── Signing ──"
if [ -f "$APK" ]; then
    if command -v apksigner &>/dev/null; then
        if apksigner verify --verbose "$APK" 2>/dev/null | grep -q "Verified using"; then
            pass "APK signature valid"
        else
            fail "APK signature invalid or unsigned"
        fi
    else
        warn "apksigner not found — skipping signature check"
    fi
fi
echo ""

# ── 4. ZIP Alignment ──
echo "── Alignment ──"
if [ -f "$APK" ]; then
    if command -v zipalign &>/dev/null; then
        if zipalign -c 4 "$APK" 2>/dev/null; then
            pass "APK aligned (4-byte boundary)"
        else
            fail "APK NOT aligned"
        fi
    else
        warn "zipalign not found — skipping alignment check"
    fi
fi
echo ""

# ── 5. Debug flag ──
echo "── Debug Flags ──"
if [ -f "$APK" ] && command -v aapt2 &>/dev/null; then
    if aapt2 dump xmltree --file AndroidManifest.xml "$APK" 2>/dev/null | grep -q 'android:debuggable.*true'; then
        fail "android:debuggable=true found in release!"
    else
        pass "No debuggable flag"
    fi
elif [ -f "$APK" ] && command -v aapt &>/dev/null; then
    if aapt dump xmltree "$APK" AndroidManifest.xml 2>/dev/null | grep -q 'android:debuggable.*0xffffffff'; then
        fail "android:debuggable=true found in release!"
    else
        pass "No debuggable flag"
    fi
else
    warn "aapt/aapt2 not found — skipping debug flag check"
fi
echo ""

# ── 6. ProGuard mapping ──
echo "── ProGuard Mapping ──"
if [ -f "$MAPPING" ]; then
    LINES=$(wc -l < "$MAPPING")
    pass "mapping.txt generated ($LINES lines)"
else
    warn "mapping.txt not found — upload to Play Console manually"
fi
echo ""

# ── 7. Version info ──
echo "── Version Info ──"
if [ -f "$APK" ] && command -v aapt &>/dev/null; then
    VERSION_NAME=$(aapt dump badging "$APK" 2>/dev/null | grep "versionName" | sed "s/.*versionName='//" | sed "s/'.*//")
    VERSION_CODE=$(aapt dump badging "$APK" 2>/dev/null | grep "versionCode" | sed "s/.*versionCode='//" | sed "s/'.*//")
    TARGET_SDK=$(aapt dump badging "$APK" 2>/dev/null | grep "targetSdkVersion" | sed "s/.*targetSdkVersion:'//" | sed "s/'.*//")
    echo "  Version Name: $VERSION_NAME"
    echo "  Version Code: $VERSION_CODE"
    echo "  Target SDK:   $TARGET_SDK"
    if [ "${TARGET_SDK:-0}" -ge 34 ]; then
        pass "Target SDK >= 34 (Play Store compliant)"
    else
        fail "Target SDK < 34 — Play Store requires targetSdk 34+"
    fi
fi
echo ""

# ── 8. Permissions audit ──
echo "── Permissions ──"
if [ -f "$APK" ] && command -v aapt &>/dev/null; then
    PERMS=$(aapt dump permissions "$APK" 2>/dev/null | grep "uses-permission" | sed 's/.*name=/  /')
    echo "$PERMS"
    if echo "$PERMS" | grep -q "MANAGE_EXTERNAL_STORAGE"; then
        warn "MANAGE_EXTERNAL_STORAGE declared — needs Play Store justification"
    fi
    if echo "$PERMS" | grep -q "SYSTEM_ALERT_WINDOW"; then
        warn "SYSTEM_ALERT_WINDOW declared — may require review"
    fi
fi
echo ""

# ── Summary ──
echo "═══════════════════════════════════════════════════"
echo -e " Results: ${GREEN}$PASS passed${NC}  ${RED}$FAIL failed${NC}  ${YELLOW}$WARN warnings${NC}"
if [ "$FAIL" -gt 0 ]; then
    echo -e " ${RED}🛑 RELEASE NOT READY — fix failures above${NC}"
    exit 1
else
    echo -e " ${GREEN}🚀 Release verification complete${NC}"
fi
echo "═══════════════════════════════════════════════════"
