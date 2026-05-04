# GitHub Open Source Migration Checklist

## Removed Play Store Specific Files
- [ ] `play_store_metadata/` → Optional, remove if not needed
- [ ] `fastlane/Fastfile` → Keep for CI lanes, remove Play Store upload steps
- [ ] `docs/PRIVACY_POLICY.md` → Keep as project policy, no hosted URL requirement
- [ ] Remove `nativeSymbolUploadEnabled` from Crashlytics if not using Firebase
- [ ] Remove any `google-services.json` references if going fully self-hosted

## Added Open Source Files
- [x] `README.md` — Project landing page
- [x] `LICENSE` — MIT License
- [x] `CONTRIBUTING.md` — Contributor guide
- [x] `CODE_OF_CONDUCT.md` — Community standards
- [x] `.github/workflows/android.yml` — CI/CD
- [x] `.github/ISSUE_TEMPLATE/` — Bug reports & features
- [x] `.github/pull_request_template.md` — PR checklist
- [x] `.github/dependabot.yml` — Dependency updates
- [x] `scripts/github_release.sh` — Release automation

## GitHub Repo Settings
- [ ] Enable Issues
- [ ] Enable Discussions
- [ ] Enable Security Advisories
- [ ] Add branch protection for `main`
- [ ] Require CI pass before merge
- [ ] Add topics: android, music-player, dsp, audio, kotlin, c-plus-plus, exoplayer, jetpack-compose
- [ ] Pin README screenshot to repo social preview

## F-Droid (Optional Future)
- [ ] Create `metadata/com.deepeye.musicpro.yml`
- [ ] Ensure reproducible builds
- [ ] Submit merge request to fdroiddata
