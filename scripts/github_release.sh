#!/bin/bash
# DeepEyeMusicPro — GitHub Release Builder
# Generates APK, AAB, changelog, and attaches to GitHub Release

set -e

VERSION=${1:-$(git describe --tags --abbrev=0)}
PACKAGE="com.deepeye.musicpro"

echo "🚀 Building release $VERSION..."

# 1. Clean build
./gradlew clean

# 2. Run all tests
echo "🧪 Running tests..."
./gradlew test

# 3. Build signed release
echo "🔨 Building release APK/AAB..."
./gradlew assembleRelease bundleRelease

APK="app/build/outputs/apk/release/app-release.apk"
AAB="app/build/outputs/bundle/release/app-release.aab"

# 4. Verify
./scripts/verify_release.sh

# 5. Generate changelog snippet
echo "📝 Generating changelog..."
LAST_TAG=$(git describe --tags --abbrev=0 HEAD~1 2>/dev/null || echo "")
if [ -n "$LAST_TAG" ]; then
    git log --pretty=format:"- %s" $LAST_TAG..HEAD > CHANGELOG_TMP.md
else
    git log --pretty=format:"- %s" -20 > CHANGELOG_TMP.md
fi

# 6. Create GitHub Release (requires gh CLI)
echo "📦 Creating GitHub release $VERSION..."
if command -v gh &> /dev/null; then
    gh release create "$VERSION" \
        "$APK" \
        "$AAB" \
        --title "DeepEyeMusicPro $VERSION" \
        --notes-file CHANGELOG_TMP.md \
        --target main
else
    echo "⚠️ gh CLI not found. Upload manually:"
    echo "   APK: $APK"
    echo "   AAB: $AAB"
fi

rm -f CHANGELOG_TMP.md
echo "✅ Release $VERSION ready!"
