# VelcuriTV Backend — Full Audit & Implementation Plan

## Project Summary

The VelcuriTV backend (`api/` directory) is a Laravel 12 + Filament v3 admin panel for managing activation codes, devices, subscriptions, and app versions for the Velcuri TV Android app. The API handles device activation, update checking, heartbeat monitoring, and analytics.

## Current State Assessment

### ✅ What EXISTS and is correctly built

| Component | Files | Status |
|---|---|---|
| **Models** (6/6) | `User`, `ActivationCode`, `Device`, `Subscription`, `AppVersion`, `AnalyticsEvent` | ✅ Complete, well-structured |
| **Migrations** (7/7) | users, activation_codes, devices, subscriptions, app_versions, analytics_events, personal_access_tokens | ✅ Complete |
| **API Controllers** (3/3) | `ActivationController`, `UpdateController`, `AnalyticsController` | ✅ Complete with full logic |
| **API Routes** | `routes/api.php` — all 5 endpoints with throttle + auth | ✅ Complete |
| **Rate Limiting** | `bootstrap/app.php` — activation, update, api limiters | ✅ Complete |
| **Filament Resources** (4/4) | `ActivationCodeResource`, `DeviceResource`, `AppVersionResource`, `SubscriptionResource` | ✅ Complete |
| **Filament Widgets** (2/2) | `StatsOverviewWidget`, `ActivationsChartWidget` | ✅ Complete |
| **Panel Provider** | `AdminPanelProvider` — branded with `#6C3FE8`, discovery paths, widgets | ✅ Complete |
| **Seeder** | `AdminSeeder` — creates admin user | ✅ Complete |
| **Repositories** (3/3) | `velcuri/`, `cncverse/`, `mega/` — each with `index.json` + `plugins.json` | ✅ Complete |
| **composer.json** | Correct dependencies: laravel 12, sanctum 4, filament 3 | ✅ Complete |
| **.env.example** | All required vars: DB, Redis, Sanctum, Filament | ✅ Complete |

### ❌ What is MISSING

---

## Critical Missing Files

> [!CAUTION]
> These files are **required for the Laravel app to boot and function**. Without them, `composer install` / `php artisan` commands will fail or the app will not serve requests.

### 1. `composer.lock` — NOT PRESENT in `api/`
The `api/` directory has no `composer.lock` and no `vendor/` directory. Dependencies have never been installed here. The `api-scaffold/` directory has both, but `api/` is the intended working directory.

### 2. `routes/web.php` — MISSING
`bootstrap/app.php` line 15 references `__DIR__ . '/../routes/web.php'` but the file does not exist. Laravel will throw a fatal error on boot.

### 3. `routes/console.php` — MISSING
`bootstrap/app.php` line 17 references `__DIR__ . '/../routes/console.php'` but the file does not exist. This will also cause a boot error.

### 4. `app/Http/Controllers/Controller.php` — MISSING
All three controllers (`ActivationController`, `UpdateController`, `AnalyticsController`) extend `Controller` but no base controller file exists in the `Controllers/` directory.

### 5. `database/seeders/DatabaseSeeder.php` — MISSING
Standard Laravel entry-point seeder. `php artisan db:seed` requires this file. The DEPLOY.md calls `php artisan db:seed --class=AdminSeeder` which bypasses it, but it should still exist for completeness.

### 6. `config/sanctum.php` — MISSING
While Sanctum can work with auto-discovery, the spec requires Sanctum configuration (stateful domains, token model) and the `.env.example` references `SANCTUM_STATEFUL_DOMAINS`. The published config file was never generated.

### 7. `config/cors.php` — MISSING
The API serves cross-origin requests from the Android app. Laravel 12's default CORS config should be present.

---

## High Priority Missing Files

> [!WARNING]
> These are required by the spec or Filament conventions and will cause partial functionality failures.

### 8. `app/Http/Middleware/` — EMPTY / MISSING
No custom middleware directory. While Laravel 12 uses slim middleware via `bootstrap/app.php`, having the directory is standard practice.

### 9. `app/Filament/Pages/` — MISSING DIRECTORY
`AdminPanelProvider` line 40 calls `discoverPages(in: app_path('Filament/Pages'))` but this directory doesn't exist. Filament will log a warning.

### 10. `database/factories/` — MISSING DIRECTORY
No model factories exist. The `composer.json` autoloads `Database\Factories\` but the directory is absent. Required for testing.

### 11. Filament Resource Page Files — SEVERAL MISSING

| Resource | Required Pages | Existing | Missing |
|---|---|---|---|
| ActivationCode | List, Create, Edit | ✅ All 3 | — |
| AppVersion | List, Create, Edit | ✅ All 3 | — |
| **Device** | List | ✅ List | ⚠️ No Create/Edit (acceptable — view-only) |
| **Subscription** | List | ✅ List | ⚠️ No Create/Edit (acceptable — managed via ActivationCode) |

> Device and Subscription resources are list-only by design, which is correct per the spec.

### 12. `app/Console/Kernel.php` or Scheduled Commands — MISSING
The DEPLOY.md requires `php artisan schedule:run` cron job. No scheduler commands are defined anywhere. Need at minimum an expiry-check command to auto-expire codes.

---

## Medium Priority Missing Files

> [!IMPORTANT]
> These improve robustness, deployment-readiness, and spec compliance.

### 13. `app/Exceptions/Handler.php` — MISSING
Laravel 12 uses `bootstrap/app.php` exceptions config (which is empty in this project). Custom API error formatting should be added.

### 14. `app/Http/Kernel.php` — NOT NEEDED (Laravel 12)
Laravel 12 uses slim bootstrapping; this file is not needed.

### 15. Feature Tests — EMPTY
Only stock `ExampleTest.php` exists. The spec (Phase 14) defines 10+ test scenarios that should be implemented:
- `POST /activate` with valid/expired/suspended/device-limit codes
- `GET /update` with old/current version codes
- `GET /ping`
- Rate limiting tests

### 16. `app/Providers/AppServiceProvider.php` — MISSING
Standard Laravel provider for boot-time registrations (model policies, observers, etc.).

### 17. `.gitignore` in `api/` — EXISTS but may need review
Currently has standard Laravel ignores but `vendor/` and `.env` should be confirmed.

---

## Code Quality Issues Found

### Issue A: Deprecated `BadgeColumn` in Filament v3
Files [ActivationCodeResource.php](file:///c:/Users/albin/OneDrive/Documents/GitHub/VelcuriTV/api/app/Filament/Resources/ActivationCodeResource.php#L59-L75) and [SubscriptionResource.php](file:///c:/Users/albin/OneDrive/Documents/GitHub/VelcuriTV/api/app/Filament/Resources/SubscriptionResource.php#L54-L60) use `Tables\Columns\BadgeColumn` which is deprecated in Filament v3. Should use `Tables\Columns\TextColumn::make(...)->badge()->color(...)` instead.

### Issue B: Auth Guard for Sanctum API Tokens
The `config/auth.php` only defines a `web` guard with the `users` provider. For Sanctum API tokens on the `ActivationCode` model, a custom guard or provider mapping may be needed. The `auth:sanctum` middleware uses token-based auth, but the `tokenable` model must resolve correctly. Need to verify `personal_access_tokens.tokenable_type` stores `App\Models\ActivationCode`.

### Issue C: `SubscriptionResource` Missing "Change Plan" Action
The spec (Phase 11) requires a "Change Plan" action on the SubscriptionResource. The current implementation has Extend (+30/+90/+365 days) and "Expire Now" but no plan change action.

### Issue D: `ActivationsChartWidget` queries by `updated_at` instead of dedicated activation date
The chart counts activations by `updated_at` field, which can be misleading since any edit triggers `updated_at`. Should filter by subscription `start_date` or a dedicated field.

### Issue E: `AnalyticsController` stores `meta` as `json_encode($request->meta)` but `AnalyticsEvent` model casts `meta` to `array`
Double-encoding risk: the model will `json_encode` again on save. Should pass the array directly and let the cast handle encoding.

---

## Proposed Changes

### Phase 1: Critical Infrastructure (Boot-blocking files)

#### [NEW] `api/routes/web.php`
Standard empty web routes file. Required for app to boot.

#### [NEW] `api/routes/console.php`
Console routes with scheduled command for expiring codes.

#### [NEW] `api/app/Http/Controllers/Controller.php`
Base controller class extending Laravel's base controller.

#### [NEW] `api/database/seeders/DatabaseSeeder.php`
Standard database seeder that calls `AdminSeeder`.

#### [NEW] `api/app/Providers/AppServiceProvider.php`
Standard service provider with boot registrations.

---

### Phase 2: Configuration & Middleware

#### [NEW] `api/config/sanctum.php`
Published Sanctum config with `stateful` domains and `ActivationCode` as token model.

#### [NEW] `api/config/cors.php`
CORS configuration allowing API access from Android app.

#### [NEW] `api/app/Filament/Pages/` (empty directory marker)
Create directory so Filament discovery doesn't warn.

---

### Phase 3: Code Quality Fixes

#### [MODIFY] [ActivationCodeResource.php](file:///c:/Users/albin/OneDrive/Documents/GitHub/VelcuriTV/api/app/Filament/Resources/ActivationCodeResource.php)
Replace deprecated `BadgeColumn` with `TextColumn::make()->badge()->color()`.

#### [MODIFY] [SubscriptionResource.php](file:///c:/Users/albin/OneDrive/Documents/GitHub/VelcuriTV/api/app/Filament/Resources/SubscriptionResource.php)
- Replace deprecated `BadgeColumn`
- Add missing "Change Plan" action per spec

#### [MODIFY] [ActivationsChartWidget.php](file:///c:/Users/albin/OneDrive/Documents/GitHub/VelcuriTV/api/app/Filament/Widgets/ActivationsChartWidget.php)
Fix to query by subscription `start_date` or first-activation date instead of `updated_at`.

#### [MODIFY] [AnalyticsController.php](file:///c:/Users/albin/OneDrive/Documents/GitHub/VelcuriTV/api/app/Http/Controllers/AnalyticsController.php)
Fix double-encoding: pass `$request->meta` directly instead of `json_encode()`.

---

### Phase 4: Scheduled Commands

#### [NEW] `api/app/Console/Commands/ExpireActivationCodes.php`
Artisan command to check and expire activation codes past their expiry date. Run via Laravel scheduler.

---

### Phase 5: Testing & Factories

#### [NEW] `api/database/factories/ActivationCodeFactory.php`
Factory for generating test activation codes.

#### [NEW] `api/database/factories/DeviceFactory.php`
Factory for generating test devices.

#### [NEW] `api/database/factories/UserFactory.php`
Factory for generating test users.

#### [NEW] `api/tests/Feature/ActivationTest.php`
Full test suite covering all 10 activation scenarios from Phase 14 of the spec.

#### [NEW] `api/tests/Feature/UpdateTest.php`
Tests for the update endpoint.

---

### Phase 6: Dependency Installation

Run `composer install` in the `api/` directory to generate `vendor/` and `composer.lock`.

---

## Open Questions

> [!IMPORTANT]
> **Q1: Which `api/` directory is the canonical one?**
> There are three directories: `api/`, `api-scaffold/`, and `api-backup/`. The `api-scaffold/` has `vendor/` and `composer.lock` (fully installed), while `api/` does not. Should we work in `api/` and install deps, or should `api-scaffold/` be the base?

> [!IMPORTANT]
> **Q2: Do you want me to proceed and create ALL missing files now?**
> I can create every missing file listed above in one execution pass. This includes ~15 new files and ~4 file modifications.

> [!IMPORTANT]
> **Q3: Should the `api-backup/` and `api-scaffold/` directories be cleaned up?**
> They appear to be earlier iterations. Having three copies of the API could cause confusion during deployment.

---

## Verification Plan

### Automated Tests
```bash
cd api
composer install
php artisan migrate --force
php artisan db:seed --class=AdminSeeder
php artisan test
```

### Manual Verification
- Confirm `php artisan serve` boots without errors
- Confirm `/admin` login page loads
- Confirm `POST /api/activate` returns expected JSON
- Confirm `GET /api/ping` returns `200 OK`
