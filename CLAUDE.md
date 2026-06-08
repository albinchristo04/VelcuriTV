# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Velcuri TV is a subscription-gated Android streaming app forked from CloudStream, plus a Laravel/Filament backend that issues activation codes and ships OTA updates. The fork retains CloudStream's plugin/extension architecture; Velcuri-specific code adds activation, a hardcoded repository list, and the update channel pointed at `api.velcuri.io`.

The codebase is split into three independently buildable pieces that share a single Git repo:

- **`app/`** — Android app (Gradle, Kotlin, Android Application plugin). Module name in `settings.gradle.kts` is `:app`. Application ID `com.velcuri.cobaltvpn`. Namespace `com.velcuri.cobaltvpn`. Older source still uses `com.lagradost.cloudstream3` imports — both packages coexist because `library/` publishes under `com.lagradost.api`.
- **`library/`** — Kotlin Multiplatform module (`:library`, JVM + Android targets) that contains plugin-facing APIs (extractors, plugin loaders, `MainAPI`, shared utils). This is the API surface that CloudStream extensions are compiled against. It uses `buildkonfig` to inject `MDL_API_KEY` and `TRAKT_CLIENT_ID` into a generated `com.lagradost.api.BuildConfig`. Published as a Maven artifact (`com.lagradost.api`).
- **`api/`** — Laravel 12 + Filament v3 backend (PHP 8.3). Issues activation codes, registers devices via Sanctum tokens, serves update manifests, accepts analytics events. Deployed to `api.velcuri.io`.

There are also `api-backup/` and `api-scaffold/` directories — these are *not* the live API. Always edit `api/`.

## Commands

### Android

JDK 17 toolchain (via Gradle) is required; the host JDK can be newer. `jvmTarget` is still 1.8 (intentional — see `gradle/libs.versions.toml`). Use `gradlew.bat` on Windows / `./gradlew` elsewhere.

Common flavors: `stable` and `prerelease` (CI builds `prerelease` for PRs and tags). Two build types: `debug`, `release`.

```bash
./gradlew assemblePrereleaseDebug        # default PR build (matches CI)
./gradlew assembleStableRelease          # production APK (signing config required)
./gradlew lint check                     # full lint + unit tests (matches CI)
./gradlew :app:testPrereleaseDebugUnitTest --tests "com.velcuri.cobaltvpn.MyTest.method"   # single test
./gradlew :library:jvmTest               # multiplatform JVM tests
./gradlew :app:makeJar                   # produces classes.jar used by extension authors
./gradlew :library:publishToMavenLocal   # publish library to ~/.m2 for local extension dev
```

Signing for the `prerelease` flavor is only wired up when `SIGNING_KEY_ALIAS` is set in the env (see `app/build.gradle.kts`). Local builds without those env vars silently fall back to debug signing — don't add a fallback signing config.

`SIMKL_CLIENT_ID` / `SIMKL_CLIENT_SECRET` and `MDL_API_KEY` / `TRAKT_CLIENT_ID` can come from env vars **or** `local.properties` keys (`simkl.id`, `simkl.secret`, `mdl.key`, `trakt.id`). Missing values compile fine but disable the corresponding integrations.

### Laravel API (in `api/`)

```bash
composer install
php artisan migrate --force
php artisan db:seed --class=AdminSeeder       # creates initial admin (DEPLOY.md §12)
php artisan serve                             # local dev
php artisan queue:work --queue=default        # background worker (required for activation expiry)
php artisan schedule:run                      # one-shot; prod runs this via cron every minute
vendor/bin/pest                               # tests (Pest 3)
vendor/bin/pint                               # formatter
```

Production deployment is documented step-by-step in `DEPLOY.md` (aaPanel-based). The Supervisor worker and the `schedule:run` cron job are both load-bearing — activation expiry, analytics rollups, and similar tasks run there.

## Architecture notes

### Plugin / extension system (Android)

CloudStream's "extensions" are external JARs/APKs that subclass `MainAPI` (in `:library`). At runtime:

1. `RepositoryManager` (`app/.../plugins/RepositoryManager.kt`) fetches `index.json` + `plugins.json` from configured repository URLs.
2. `PluginManager` (`app/.../plugins/PluginManager.kt`) downloads plugin files, verifies sha256, and loads classes via `DexClassLoader`.
3. Loaded plugins register providers into `APIHolder`, which the rest of the app (search, home, result, player) iterates.

**Velcuri customization:** the repository list is intended to be hardcoded built-in (rather than user-added via the setup wizard). When adding repos, edit the prebuilt list in `RepositoryManager` (`PREBUILT_REPOSITORIES`) — don't reintroduce the user-add flow. The static JSON those repos point at lives in `repositories/` and is served from `repo.velcuri.io`.

### Activation flow

`app/.../activation/` (`ActivationFragment`, `ActivationHelper`, `ActivationModels`, `ActivationConstants`):

- First-launch gate. `KEY_IS_ACTIVATED` in CloudStream's `DataStore` (NOT `EncryptedSharedPreferences`) controls whether the app proceeds to the main UI.
- Code is POSTed to `https://api.velcuri.io/activate` → server returns a Sanctum token stored under `KEY_ACTIVATION_TOKEN`, plus expiry/plan metadata.
- Subsequent calls (`/heartbeat`, `/analytics/event`) use that token via `auth:sanctum`.
- Use CloudStream's `app.get()` / `app.post()` HTTP client (`nicehttp`) for Velcuri API calls — do NOT add Retrofit.

The matching server side: `ActivationController`, `UpdateController`, `AnalyticsController` in `api/app/Http/Controllers/`. Models: `ActivationCode`, `Device`, `Subscription`, `AppVersion`, `AnalyticsEvent`, `User`. Filament admin resources mirror those models in `api/app/Filament/Resources/`.

### Update channel

Android queries `GET https://api.velcuri.io/update` (Laravel `UpdateController@check`) instead of the GitHub Releases API that upstream CloudStream uses. The `AppVersion` model + Filament resource controls what's returned. APKs themselves are served as static files from `download.velcuri.io`.

### Branding / namespace caveat

Application ID is `com.velcuri.cobaltvpn` but a significant portion of the Kotlin source still uses `com.lagradost.cloudstream3.*` imports (especially in `:library`). The library namespace is intentionally `com.lagradost.api` so that `com.velcuri.cobaltvpn.R` does not collide with library resources (see comment in `library/build.gradle.kts`). When renaming things, leave the `com.lagradost.api` namespace alone unless you also update every consuming extension. The repo previously used `io.velcuri.tv` — historical references to that name in `velcuri_tv_claude_code_prompt.md` are left as-is for traceability.

The fork is a **rename + additive features**, not a rewrite. Do not refactor CloudStream subsystems unless the task explicitly requires it.

## CI

`.github/workflows/`:
- `pull_request.yml` — every PR runs `./gradlew assemblePrereleaseDebug lint check` and uploads the APK.
- `prerelease.yml` — tagged prerelease builds; uses `SIGNING_*` secrets and writes the keystore to `~/work/_temp/keystore/`.
- `build_to_archive.yml`, `generate_dokka.yml`, `update_locales.yml` — auxiliary.

`AI-POLICY.md` requires disclosing AI-generated contributions in PRs and that the author can explain/maintain any submitted code.
