# Velcuri TV — Claude Code Agent Build Prompt
**Version:** 2.1 (Repo-Aware — Updated for existing fork)
**Repository:** https://github.com/albinchristo04/VelcuriTV.git
**Stack:** Laravel 12 · MySQL 8 · Redis · Filament v3 · Kotlin (CloudStream Fork)

---

## REPO STATE — READ FIRST

The Android project already exists as a fork of CloudStream at the repo above. It was cloned fresh — **one commit, zero custom changes made yet.** Every file is stock CloudStream 4.7.0 (versionCode 68).

Key facts Claude Code must know before touching a single file:

| Item | Current (Stock) | Target (Velcuri) |
|---|---|---|
| Package name | `com.lagradost.cloudstream3` | `io.velcuri.tv` |
| App name | `CloudStream` | `Velcuri TV` |
| versionName | `4.7.0` | `1.0.0` |
| versionCode | `68` | `100` |
| Primary color | `#3d50fa` | `#6C3FE8` |
| Update source | GitHub API (recloudstream/cloudstream) | `https://api.velcuri.io/update` |
| Repos | User-added via setup wizard | Hardcoded built-in, no user addition needed |
| Activation | None | Code-gated first launch |

**Do not rewrite CloudStream logic.** Only add, not replace, except where explicitly told to below.

---

## AGENT INSTRUCTIONS

Work through each phase in order. Verify and compile between phases. Never stub — every method must be fully implemented.

---

## PROJECT OVERVIEW

Velcuri TV is a subscription-gated Android streaming application. Users install the APK, enter an activation code on first launch, and get immediate access to built-in streaming repositories. Everything is managed from a centralized admin dashboard.

**Core user flow:**
```
Install APK → Enter Activation Code → API Validates → Device Registered → App Unlocks → Watch Content
```

**Distribution:** Sideload only (direct APK download from download.velcuri.io). Not Play Store.

---

## TECH STACK

### Backend
- **Framework:** Laravel 12
- **PHP:** 8.3
- **Database:** MySQL 8
- **Cache/Queue:** Redis
- **Admin Panel:** Filament v3
- **Authentication:** Laravel Sanctum (device-scoped tokens)
- **Server:** Nginx on Ubuntu 24.04 LTS (aaPanel)
- **SSL:** Let's Encrypt

### Android
- **Base:** Existing fork at https://github.com/albinchristo04/VelcuriTV.git
- **Language:** Kotlin (already in the project)
- **JSON:** Jackson (already in `gradle/libs.versions.toml`)
- **HTTP:** CloudStream's own `app.get()` / `app.post()` client — use this for Velcuri API calls too, not Retrofit
- **Storage:** CloudStream's `DataStore` (`setKey` / `getKey`) — use for token storage, not EncryptedSharedPreferences

### Domains
| Purpose | Domain |
|---|---|
| API | `api.velcuri.io` |
| Repository | `repo.velcuri.io` |
| Admin panel | `admin.velcuri.io` |
| APK download | `download.velcuri.io` |

---

## PHASE 1 — ANDROID: PACKAGE RENAME & BRANDING

Work inside the cloned repo root.

### Step 1.1: Package Rename

Run in the project root:
```bash
# Rename all source file package declarations and imports
find . -type f \( -name "*.kt" -o -name "*.java" \) \
  -exec sed -i 's/com\.lagradost\.cloudstream3/io.velcuri.tv/g' {} +

# Rename manifest, build files, and XML resources
find . -type f \( -name "*.xml" -o -name "*.gradle" -o -name "*.gradle.kts" \) \
  -exec sed -i 's/com\.lagradost\.cloudstream3/io.velcuri.tv/g' {} +

# Rename the actual source directory tree
mkdir -p app/src/main/java/io/velcuri/tv
cp -r app/src/main/java/com/lagradost/cloudstream3/* app/src/main/java/io/velcuri/tv/
rm -rf app/src/main/java/com
```

Also rename in `androidTest` and `test` source sets the same way.

### Step 1.2: Version Numbers

Edit `gradle/libs.versions.toml`:
```toml
versionCode = "100"
versionName = "1.0.0"
```

### Step 1.3: App Name — `app/src/main/res/values/strings.xml`

Find and replace:
```xml
<!-- BEFORE -->
<string name="app_name">CloudStream</string>
<string name="play_with_app_name">Play with CloudStream</string>

<!-- AFTER -->
<string name="app_name">Velcuri TV</string>
<string name="play_with_app_name">Play with Velcuri TV</string>
```

Also replace all other user-visible instances of "CloudStream" in `strings.xml` with "Velcuri TV":
- `blank_repo_message` — update to not mention CloudStream
- `download_all_plugins_from_repo` — replace "CloudStream" with "Velcuri TV"
- `app_info_intent_error` — replace "CloudStream" with "Velcuri TV"
- `biometric_authentication_title` — replace "CloudStream" with "Velcuri TV"
- `cs3wiki` — change to "Velcuri TV Help"

### Step 1.4: Brand Colors — `app/src/main/res/values/colors.xml`

```xml
<color name="colorPrimary">#6C3FE8</color>
<color name="colorPrimarySecond">@color/colorPrimary</color>
<color name="colorPrimaryDark">#4A2AAD</color>
<color name="colorAccent">#9B6BF5</color>
```

### Step 1.5: Launcher Icons

Replace all `ic_launcher*.png` and `ic_launcher*.webp` files across:
- `app/src/main/res/mipmap-mdpi/`
- `app/src/main/res/mipmap-hdpi/`
- `app/src/main/res/mipmap-xhdpi/`
- `app/src/main/res/mipmap-xxhdpi/`
- `app/src/main/res/mipmap-xxxhdpi/`
- `app/src/main/ic_launcher-playstore.png`
- `app/src/debug/ic_launcher-playstore.png`
- `app/src/prerelease/ic_launcher-playstore.png`

Replace all with Velcuri TV branded icons. If no brand asset is provided, generate a placeholder icon using a vector drawable with the letter "V" on a purple background (`#6C3FE8`). Create `app/src/main/res/drawable/ic_velcuri_logo.xml` as an adaptive icon:
```xml
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/colorPrimary" />
    <foreground>
        <text-view
            android:text="V"
            android:textColor="#FFFFFF"
            android:textSize="48sp" />
    </foreground>
</adaptive-icon>
```

### Step 1.6: About / Settings Page

Find the About preference screen XML (likely `res/xml/settings_general.xml` or similar) and update any links pointing to the CloudStream wiki, GitHub, or Discord to point to `https://velcuri.io` instead.

---

## PHASE 2 — ANDROID: BUILT-IN REPOSITORIES

### The Problem
`PREBUILT_REPOSITORIES` in `RepositoryManager.kt` is currently loaded from `DataStore` (user-saved), not hardcoded:
```kotlin
val PREBUILT_REPOSITORIES: Array<RepositoryData> by lazy {
    getKey("PREBUILT_REPOSITORIES") ?: emptyArray()
}
```

### The Fix
Replace that lazy property with hardcoded Velcuri repos that are always present and non-removable.

Edit `app/src/main/java/io/velcuri/tv/plugins/RepositoryManager.kt`:

```kotlin
// REPLACE THIS:
val PREBUILT_REPOSITORIES: Array<RepositoryData> by lazy {
    getKey("PREBUILT_REPOSITORIES") ?: emptyArray()
}

// WITH THIS:
val PREBUILT_REPOSITORIES: Array<RepositoryData> = arrayOf(
    RepositoryData(
        name = "Velcuri Repository",
        url = "https://repo.velcuri.io/velcuri/index.json"
    ),
    RepositoryData(
        name = "CNCVerse",
        url = "https://repo.velcuri.io/cncverse/index.json"
    ),
    RepositoryData(
        name = "Mega Repository",
        url = "https://repo.velcuri.io/mega/index.json"
    )
)
```

Check `RepositoryData` data class definition in `ui/settings/extensions/` — match its constructor parameters exactly.

### Hide the "Add Repository" button
Find `RepoAdapter.kt` or the settings fragment that shows the "Add repository" option. Wrap it with a check so users cannot add or remove the built-in repos. Built-in repos (matched by URL prefix `repo.velcuri.io`) should not show a remove/delete button.

---

## PHASE 3 — ANDROID: ACTIVATION SYSTEM

This is the core new feature. The activation screen intercepts first launch before the normal setup wizard.

### Step 3.1: Constants file

Create `app/src/main/java/io/velcuri/tv/activation/ActivationConstants.kt`:
```kotlin
package io.velcuri.tv.activation

object ActivationConstants {
    const val VELCURI_API_BASE = "https://api.velcuri.io/"
    const val KEY_ACTIVATION_TOKEN = "VELCURI_ACTIVATION_TOKEN"
    const val KEY_ACTIVATION_EXPIRY = "VELCURI_EXPIRY_DATE"
    const val KEY_ACTIVATION_PLAN = "VELCURI_PLAN"
    const val KEY_IS_ACTIVATED = "VELCURI_IS_ACTIVATED"
}
```

### Step 3.2: Data models

Create `app/src/main/java/io/velcuri/tv/activation/ActivationModels.kt`:
```kotlin
package io.velcuri.tv.activation

import com.fasterxml.jackson.annotation.JsonProperty

data class ActivationRequest(
    @JsonProperty("code") val code: String,
    @JsonProperty("device_id") val deviceId: String,
    @JsonProperty("device_model") val deviceModel: String,
    @JsonProperty("android_version") val androidVersion: String,
    @JsonProperty("app_version") val appVersion: String
)

data class ActivationResponse(
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("token") val token: String?,
    @JsonProperty("status") val status: String?,
    @JsonProperty("plan") val plan: String?,
    @JsonProperty("expiry") val expiry: String?,
    @JsonProperty("devices_allowed") val devicesAllowed: Int?,
    @JsonProperty("devices_used") val devicesUsed: Int?,
    @JsonProperty("error") val error: String?,
    @JsonProperty("message") val message: String?
)

data class UpdateResponse(
    @JsonProperty("latest_version") val latestVersion: String,
    @JsonProperty("version_code") val versionCode: Int,
    @JsonProperty("force_update") val forceUpdate: Boolean,
    @JsonProperty("update_available") val updateAvailable: Boolean,
    @JsonProperty("download_url") val downloadUrl: String,
    @JsonProperty("release_notes") val releaseNotes: String?
)
```

### Step 3.3: ActivationHelper

Create `app/src/main/java/io/velcuri/tv/activation/ActivationHelper.kt`:

```kotlin
package io.velcuri.tv.activation

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.lagradost.cloudstream3.app  // CloudStream's HTTP client
import com.lagradost.cloudstream3.utils.AppUtils.tryParseJson
import io.velcuri.tv.BuildConfig
import io.velcuri.tv.CloudStreamApp.Companion.getKey
import io.velcuri.tv.CloudStreamApp.Companion.setKey
import java.security.MessageDigest

object ActivationHelper {

    fun isActivated(): Boolean {
        return getKey<Boolean>(ActivationConstants.KEY_IS_ACTIVATED) == true
            && getKey<String>(ActivationConstants.KEY_ACTIVATION_TOKEN) != null
    }

    fun getStoredToken(): String? {
        return getKey(ActivationConstants.KEY_ACTIVATION_TOKEN)
    }

    fun clearActivation() {
        setKey(ActivationConstants.KEY_IS_ACTIVATED, false)
        setKey(ActivationConstants.KEY_ACTIVATION_TOKEN, "")
    }

    fun generateDeviceId(context: Context): String {
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "unknown"
        val fingerprint = Build.FINGERPRINT
        val raw = "$androidId:$fingerprint"
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(raw.toByteArray())
            .joinToString("") { "%02x".format(it) }
            .take(32)
    }

    suspend fun activate(context: Context, code: String): ActivationResponse {
        val request = ActivationRequest(
            code = code.trim().uppercase(),
            deviceId = generateDeviceId(context),
            deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}",
            androidVersion = Build.VERSION.RELEASE,
            appVersion = BuildConfig.VERSION_NAME
        )
        val response = app.post(
            "${ActivationConstants.VELCURI_API_BASE}activate",
            json = request,
            headers = mapOf("Content-Type" to "application/json")
        )
        return tryParseJson<ActivationResponse>(response.text)
            ?: ActivationResponse(
                success = false,
                token = null, status = null, plan = null, expiry = null,
                devicesAllowed = null, devicesUsed = null,
                error = "parse_error",
                message = "Failed to parse server response"
            )
    }

    fun saveActivation(response: ActivationResponse) {
        setKey(ActivationConstants.KEY_IS_ACTIVATED, true)
        setKey(ActivationConstants.KEY_ACTIVATION_TOKEN, response.token ?: "")
        setKey(ActivationConstants.KEY_ACTIVATION_PLAN, response.plan ?: "")
        setKey(ActivationConstants.KEY_ACTIVATION_EXPIRY, response.expiry ?: "")
    }

    fun mapErrorMessage(error: String?): String {
        return when (error) {
            "invalid_code" -> "Invalid activation code. Please check and try again."
            "expired" -> "This code has expired. Please renew your subscription."
            "suspended" -> "This account has been suspended. Contact support at velcuri.io."
            "cancelled" -> "This subscription has been cancelled. Contact support at velcuri.io."
            "device_limit_reached" -> "Device limit reached. Remove a device from your account to continue."
            "device_blocked" -> "This device has been blocked. Contact support at velcuri.io."
            "rate_limited" -> "Too many attempts. Please wait a moment and try again."
            else -> "Connection failed. Please check your internet and try again."
        }
    }

    suspend fun checkUpdate(context: Context): UpdateResponse? {
        return try {
            val response = app.get(
                "${ActivationConstants.VELCURI_API_BASE}update",
                params = mapOf(
                    "version" to BuildConfig.VERSION_NAME,
                    "version_code" to BuildConfig.VERSION_CODE.toString()
                )
            )
            tryParseJson<UpdateResponse>(response.text)
        } catch (e: Exception) {
            null // Silently fail — never block launch for update check
        }
    }
}
```

### Step 3.4: Activation Fragment

Create `app/src/main/java/io/velcuri/tv/activation/ActivationFragment.kt`:

```kotlin
package io.velcuri.tv.activation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import io.velcuri.tv.R
import kotlinx.coroutines.launch

class ActivationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_activation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val codeInput = view.findViewById<EditText>(R.id.activation_code_input)
        val activateButton = view.findViewById<Button>(R.id.activation_button)
        val progressBar = view.findViewById<ProgressBar>(R.id.activation_progress)
        val errorText = view.findViewById<TextView>(R.id.activation_error)

        // Auto-uppercase as user types
        codeInput.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val upper = s.toString().uppercase()
                if (upper != s.toString()) {
                    codeInput.removeTextChangedListener(this)
                    codeInput.setText(upper)
                    codeInput.setSelection(upper.length)
                    codeInput.addTextChangedListener(this)
                }
            }
        })

        activateButton.setOnClickListener {
            val code = codeInput.text.toString().trim()
            if (code.isEmpty()) {
                errorText.text = "Please enter your activation code."
                errorText.isVisible = true
                return@setOnClickListener
            }

            // Show loading state
            progressBar.isVisible = true
            activateButton.isEnabled = false
            errorText.isVisible = false

            lifecycleScope.launch {
                try {
                    val response = ActivationHelper.activate(requireContext(), code)
                    if (response.success && response.token != null) {
                        ActivationHelper.saveActivation(response)
                        // Navigate to the normal setup wizard
                        findNavController().navigate(R.id.action_activation_to_setup_language)
                    } else {
                        errorText.text = ActivationHelper.mapErrorMessage(response.error)
                        errorText.isVisible = true
                    }
                } catch (e: Exception) {
                    errorText.text = "Connection failed. Check your internet connection."
                    errorText.isVisible = true
                } finally {
                    progressBar.isVisible = false
                    activateButton.isEnabled = true
                }
            }
        }
    }
}
```

### Step 3.5: Activation Layout

Create `app/src/main/res/layout/fragment_activation.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="?attr/primaryBlackBackground">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:gravity="center_horizontal"
        android:padding="32dp">

        <!-- Logo area -->
        <ImageView
            android:layout_width="120dp"
            android:layout_height="120dp"
            android:layout_marginTop="48dp"
            android:layout_marginBottom="24dp"
            android:src="@drawable/ic_velcuri_logo"
            android:contentDescription="Velcuri TV logo" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Velcuri TV"
            android:textSize="28sp"
            android:textStyle="bold"
            android:textColor="?attr/textColor"
            android:layout_marginBottom="8dp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Enter your activation code to get started"
            android:textSize="14sp"
            android:textColor="?attr/textColorSecondary"
            android:gravity="center"
            android:layout_marginBottom="40dp" />

        <com.google.android.material.textfield.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="VEL-XXXXXX"
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/activation_code_input"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textCapCharacters"
                android:maxLength="10"
                android:letterSpacing="0.15"
                android:textSize="18sp" />
        </com.google.android.material.textfield.TextInputLayout>

        <TextView
            android:id="@+id/activation_error"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:textColor="#CF6679"
            android:textSize="13sp"
            android:visibility="gone" />

        <Button
            android:id="@+id/activation_button"
            android:layout_width="match_parent"
            android:layout_height="56dp"
            android:layout_marginTop="24dp"
            android:text="Activate"
            android:textSize="16sp"
            android:backgroundTint="@color/colorPrimary" />

        <ProgressBar
            android:id="@+id/activation_progress"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:visibility="gone" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="32dp"
            android:text="Need a code? Visit velcuri.io"
            android:textSize="13sp"
            android:textColor="@color/colorAccent" />

    </LinearLayout>
</ScrollView>
```

### Step 3.6: Add Activation to Navigation Graph

Edit `app/src/main/res/navigation/nav_graph.xml` (or whichever file contains the setup navigation).

Add the activation fragment destination:
```xml
<fragment
    android:id="@+id/navigation_activation"
    android:name="io.velcuri.tv.activation.ActivationFragment"
    android:label="Activation"
    tools:layout="@layout/fragment_activation">
    <action
        android:id="@+id/action_activation_to_setup_language"
        app:destination="@id/navigation_setup_language" />
</fragment>
```

### Step 3.7: Hook Activation into MainActivity

In `app/src/main/java/io/velcuri/tv/MainActivity.kt`, find the first-launch check block (around line 2017 in original):

```kotlin
// EXISTING CODE:
if (getKey(HAS_DONE_SETUP_KEY, false) != true) {
    navController.navigate(R.id.navigation_setup_language)
```

**Replace with:**
```kotlin
if (!ActivationHelper.isActivated()) {
    // First priority: activation gate
    navController.navigate(R.id.navigation_activation)
} else if (getKey(HAS_DONE_SETUP_KEY, false) != true) {
    // Already activated but setup not done (re-install case)
    navController.navigate(R.id.navigation_setup_language)
```

Add the import at the top:
```kotlin
import io.velcuri.tv.activation.ActivationHelper
```

---

## PHASE 4 — ANDROID: UPDATE SYSTEM REPLACEMENT

The existing `InAppUpdater.kt` checks GitHub releases from `recloudstream/cloudstream`. Replace with Velcuri API.

### Step 4.1: Replace InAppUpdater

Edit `app/src/main/java/io/velcuri/tv/utils/InAppUpdater.kt`.

Find the constants at the top:
```kotlin
private const val GITHUB_USER_NAME = "recloudstream"
private const val GITHUB_REPO = "cloudstream"
```

Replace the entire `runAutoUpdate` function logic. Instead of calling GitHub API, call:
```
GET https://api.velcuri.io/update?version={versionName}&version_code={versionCode}
```

Parse the response as `UpdateResponse` (defined in Phase 3.2).

The update dialog behavior stays the same — reuse the existing dialog logic, just swap the data source. Specifically:
- Replace `getAppUpdate()` function to call `ActivationHelper.checkUpdate(context)` 
- If `updateResponse.forceUpdate == true`, show dialog with no dismiss option
- If `updateResponse.forceUpdate == false`, show dialog with optional dismiss
- Download URL is `updateResponse.downloadUrl` — use the existing APK download logic unchanged

### Step 4.2: Remove Pre-release Update Option

In `SettingsUpdates.kt`, remove or hide the pre-release/beta update preference since Velcuri distributes through its own pipeline. Look for `installPreReleaseIfNeeded` and hide the preference.

---

## PHASE 5 — ANDROID: CLEANUP

### Step 5.1: Remove CloudStream About Links
In settings XML files (`res/xml/settings_*.xml`), find and update:
- Any URL pointing to `github.com/recloudstream` → remove or point to `velcuri.io`
- Any URL pointing to CloudStream Discord → remove or point to `velcuri.io`
- Any URL pointing to CloudStream wiki → point to `velcuri.io/help`

### Step 5.2: Suppress Setup Wizard Repository Step
Since repos are now built-in, the user does not need to add repos during setup. In `SetupFragmentExtensions.kt`, the existing logic already shows PREBUILT_REPOSITORIES. Since those are now hardcoded Velcuri repos, the step will display them correctly. No change needed — but verify the "Next" button advances correctly after repos are shown.

### Step 5.3: Compile and Verify
```bash
./gradlew assembleRelease
```
Fix any compilation errors from the package rename (look for residual `com.lagradost` references). The build should produce `app/build/outputs/apk/release/app-release-unsigned.apk`.

---

## PHASE 6 — BACKEND: LARAVEL PROJECT SETUP

Start fresh in the `api/` directory.

```bash
composer create-project laravel/laravel . "^12.0"
composer require laravel/sanctum
composer require filament/filament:"^3.0" -W
php artisan filament:install --panels
php artisan vendor:publish --provider="Laravel\Sanctum\SanctumServiceProvider"
```

### .env Configuration
```env
APP_NAME="Velcuri TV API"
APP_ENV=production
APP_DEBUG=false
APP_URL=https://api.velcuri.io

DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=velcuri_tv
DB_USERNAME=velcuri_user
DB_PASSWORD=CHANGE_ME_STRONG_PASSWORD

REDIS_HOST=127.0.0.1
REDIS_PASSWORD=CHANGE_ME
CACHE_STORE=redis
QUEUE_CONNECTION=redis
SESSION_DRIVER=redis

SANCTUM_STATEFUL_DOMAINS=admin.velcuri.io
FILAMENT_FILESYSTEM_DISK=local

VELCURI_APP_SECRET=CHANGE_ME_32_CHAR_HEX_SECRET
```

---

## PHASE 7 — BACKEND: DATABASE MIGRATIONS

Run migrations in this exact order.

### Migration 1: users
```php
Schema::create('users', function (Blueprint $table) {
    $table->id();
    $table->string('name');
    $table->string('email')->unique()->nullable();
    $table->string('password')->nullable();
    $table->enum('role', ['admin', 'reseller'])->default('admin');
    $table->enum('status', ['active', 'suspended'])->default('active');
    $table->timestamps();
});
```

### Migration 2: activation_codes
```php
Schema::create('activation_codes', function (Blueprint $table) {
    $table->id();
    $table->string('code', 20)->unique(); // VEL-XXXXXX
    $table->enum('plan', ['trial', 'monthly', 'quarterly', 'semiannual', 'annual', 'lifetime']);
    $table->enum('status', ['unused', 'active', 'expired', 'suspended', 'cancelled'])->default('unused');
    $table->timestamp('expiry_date')->nullable(); // null = lifetime
    $table->unsignedTinyInteger('devices_allowed')->default(1);
    $table->unsignedTinyInteger('devices_used')->default(0);
    $table->text('notes')->nullable();
    $table->foreignId('created_by')->nullable()->constrained('users')->nullOnDelete();
    $table->timestamps();
});
```

### Migration 3: devices
```php
Schema::create('devices', function (Blueprint $table) {
    $table->id();
    $table->foreignId('activation_id')->constrained('activation_codes')->onDelete('cascade');
    $table->string('device_id', 64);
    $table->string('device_model', 100)->nullable();
    $table->string('android_version', 20)->nullable();
    $table->string('app_version', 20)->nullable();
    $table->string('ip_address', 45)->nullable();
    $table->string('country', 10)->nullable();
    $table->timestamp('last_seen')->nullable();
    $table->boolean('is_blocked')->default(false);
    $table->timestamps();
    $table->unique(['activation_id', 'device_id']);
});
```

### Migration 4: subscriptions
```php
Schema::create('subscriptions', function (Blueprint $table) {
    $table->id();
    $table->foreignId('activation_id')->constrained('activation_codes')->onDelete('cascade');
    $table->enum('plan', ['trial', 'monthly', 'quarterly', 'semiannual', 'annual', 'lifetime']);
    $table->timestamp('start_date');
    $table->timestamp('end_date')->nullable();
    $table->enum('status', ['active', 'expired', 'suspended', 'cancelled'])->default('active');
    $table->timestamps();
});
```

### Migration 5: app_versions
```php
Schema::create('app_versions', function (Blueprint $table) {
    $table->id();
    $table->string('version', 20);
    $table->unsignedInteger('version_code');
    $table->string('download_url');
    $table->boolean('force_update')->default(false);
    $table->text('release_notes')->nullable();
    $table->boolean('is_active')->default(true);
    $table->timestamps();
});
```

### Migration 6: analytics_events
```php
Schema::create('analytics_events', function (Blueprint $table) {
    $table->id();
    $table->foreignId('device_id')->nullable()->constrained('devices')->nullOnDelete();
    $table->string('event_type', 50);
    $table->string('extension_name', 100)->nullable();
    $table->string('country', 10)->nullable();
    $table->string('app_version', 20)->nullable();
    $table->json('meta')->nullable();
    $table->timestamp('created_at')->useCurrent();
    $table->index(['event_type', 'created_at']);
});
```

---

## PHASE 8 — BACKEND: MODELS

### `ActivationCode` Model
```php
class ActivationCode extends Model
{
    protected $fillable = [
        'code', 'plan', 'status', 'expiry_date', 'devices_allowed', 'devices_used', 'notes', 'created_by'
    ];

    protected $casts = [
        'expiry_date' => 'datetime',
        'devices_allowed' => 'integer',
        'devices_used' => 'integer',
    ];

    public function devices() { return $this->hasMany(Device::class, 'activation_id'); }
    public function subscription() { return $this->hasOne(Subscription::class, 'activation_id'); }

    public function isExpired(): bool {
        if ($this->plan === 'lifetime') return false;
        if (is_null($this->expiry_date)) return false;
        return $this->expiry_date->isPast();
    }

    public function canAddDevice(): bool {
        return $this->devices_used < $this->devices_allowed;
    }

    public static function generateCode(): string {
        do {
            $code = 'VEL-' . strtoupper(\Str::random(6));
        } while (static::where('code', $code)->exists());
        return $code;
    }

    public function scopeActive($query) {
        return $query->where('status', 'active');
    }

    public function scopeExpired($query) {
        return $query->where('status', 'expired')
            ->orWhere(function($q) {
                $q->whereNotNull('expiry_date')
                  ->where('expiry_date', '<', now())
                  ->where('plan', '!=', 'lifetime');
            });
    }
}
```

### `Device` Model
```php
class Device extends Model
{
    protected $fillable = [
        'activation_id', 'device_id', 'device_model', 'android_version',
        'app_version', 'ip_address', 'country', 'last_seen', 'is_blocked'
    ];

    protected $casts = [
        'last_seen' => 'datetime',
        'is_blocked' => 'boolean',
    ];

    public function activationCode() { 
        return $this->belongsTo(ActivationCode::class, 'activation_id'); 
    }
}
```

### `Subscription` Model
```php
class Subscription extends Model
{
    protected $fillable = ['activation_id', 'plan', 'start_date', 'end_date', 'status'];
    protected $casts = ['start_date' => 'datetime', 'end_date' => 'datetime'];

    public function activationCode() { 
        return $this->belongsTo(ActivationCode::class, 'activation_id'); 
    }

    public function isActive(): bool {
        return $this->status === 'active';
    }
}
```

### `AppVersion` Model
```php
class AppVersion extends Model
{
    protected $fillable = ['version', 'version_code', 'download_url', 'force_update', 'release_notes', 'is_active'];
    protected $casts = ['force_update' => 'boolean', 'is_active' => 'boolean'];

    public function scopeLatestActive($query) {
        return $query->where('is_active', true)->orderByDesc('version_code');
    }
}
```

---

## PHASE 9 — BACKEND: API CONTROLLERS

Register in `routes/api.php`:
```php
Route::post('/activate', [ActivationController::class, 'activate']);
Route::get('/update', [UpdateController::class, 'check']);
Route::get('/ping', fn() => response()->json(['status' => 'ok', 'timestamp' => now()->toIso8601String()]));

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/heartbeat', [ActivationController::class, 'heartbeat']);
    Route::post('/analytics/event', [AnalyticsController::class, 'store']);
});
```

### `ActivationController`

#### `POST /activate`
```php
public function activate(Request $request): JsonResponse
{
    $request->validate([
        'code' => 'required|string|max:20',
        'device_id' => 'required|string|max:64',
        'device_model' => 'nullable|string|max:100',
        'android_version' => 'nullable|string|max:20',
        'app_version' => 'nullable|string|max:20',
    ]);

    $code = ActivationCode::where('code', strtoupper($request->code))->first();

    if (!$code) {
        return response()->json(['success' => false, 'error' => 'invalid_code', 'message' => 'Invalid activation code.'], 404);
    }

    // Check suspended/cancelled first
    if (in_array($code->status, ['suspended', 'cancelled'])) {
        return response()->json(['success' => false, 'error' => $code->status, 'message' => 'Account ' . $code->status . '.'], 403);
    }

    // Check expiry (and update status)
    if ($code->isExpired()) {
        $code->update(['status' => 'expired']);
        return response()->json(['success' => false, 'error' => 'expired', 'message' => 'Subscription expired.'], 403);
    }

    // Find existing device registration
    $device = Device::where('activation_id', $code->id)
        ->where('device_id', $request->device_id)
        ->first();

    if (!$device) {
        // New device — check slot availability
        if (!$code->canAddDevice()) {
            return response()->json(['success' => false, 'error' => 'device_limit_reached', 'message' => 'Device limit reached.'], 403);
        }

        $device = Device::create([
            'activation_id' => $code->id,
            'device_id' => $request->device_id,
            'device_model' => $request->device_model,
            'android_version' => $request->android_version,
            'app_version' => $request->app_version,
            'ip_address' => $request->ip(),
            'last_seen' => now(),
        ]);

        $code->increment('devices_used');

        // Activate code on first use
        if ($code->status === 'unused') {
            $expiryDate = $this->calculateExpiry($code->plan);
            $code->update(['status' => 'active', 'expiry_date' => $expiryDate]);
            Subscription::create([
                'activation_id' => $code->id,
                'plan' => $code->plan,
                'start_date' => now(),
                'end_date' => $expiryDate,
                'status' => 'active',
            ]);
            $code->refresh();
        }
    } else {
        // Existing device — check if blocked
        if ($device->is_blocked) {
            return response()->json(['success' => false, 'error' => 'device_blocked', 'message' => 'This device is blocked.'], 403);
        }
        // Update device info on re-activation
        $device->update([
            'app_version' => $request->app_version,
            'last_seen' => now(),
        ]);
    }

    // Issue Sanctum token scoped to this device
    $tokenName = "device:{$device->id}";
    // Revoke previous tokens for this device
    $device->activationCode->tokens()->where('name', $tokenName)->delete();
    $token = $device->activationCode->createToken($tokenName);

    return response()->json([
        'success' => true,
        'token' => $token->plainTextToken,
        'status' => $code->status,
        'plan' => $code->plan,
        'expiry' => $code->expiry_date?->toIso8601String(),
        'devices_allowed' => $code->devices_allowed,
        'devices_used' => $code->devices_used,
    ]);
}

private function calculateExpiry(string $plan): ?Carbon
{
    return match($plan) {
        'trial' => now()->addDays(7),
        'monthly' => now()->addDays(30),
        'quarterly' => now()->addDays(90),
        'semiannual' => now()->addDays(180),
        'annual' => now()->addDays(365),
        'lifetime' => null,
        default => now()->addDays(30),
    };
}
```

Note: `ActivationCode` needs `HasApiTokens` trait from Sanctum since tokens are issued on the model. Add `use HasApiTokens;` to `ActivationCode`.

#### `POST /heartbeat`
```php
public function heartbeat(Request $request): JsonResponse
{
    // Get token, find activation code
    $token = $request->user(); // returns ActivationCode model
    // Update last_seen on the device matching this token
    // Re-check expiry and return current status
    return response()->json(['status' => 'active', 'expiry' => $token->expiry_date?->toIso8601String()]);
}
```

### `UpdateController`

#### `GET /update`
```php
public function check(Request $request): JsonResponse
{
    $currentVersionCode = (int) $request->query('version_code', 0);
    $latest = AppVersion::latestActive()->first();

    if (!$latest) {
        return response()->json(['update_available' => false]);
    }

    return response()->json([
        'latest_version' => $latest->version,
        'version_code' => $latest->version_code,
        'force_update' => $latest->force_update,
        'update_available' => $latest->version_code > $currentVersionCode,
        'download_url' => $latest->download_url,
        'release_notes' => $latest->release_notes,
    ]);
}
```

---

## PHASE 10 — BACKEND: RATE LIMITING

In `bootstrap/app.php`, configure rate limits:
```php
RateLimiter::for('activation', function (Request $request) {
    return Limit::perMinute(5)->by($request->ip())->response(function () {
        return response()->json(['success' => false, 'error' => 'rate_limited', 'message' => 'Too many attempts.'], 429);
    });
});

RateLimiter::for('update', function (Request $request) {
    return Limit::perMinute(60)->by($request->ip());
});

RateLimiter::for('api', function (Request $request) {
    return Limit::perMinute(120)->by($request->bearerToken() ?? $request->ip());
});
```

Apply in `routes/api.php`:
```php
Route::middleware('throttle:activation')->post('/activate', ...);
Route::middleware('throttle:update')->get('/update', ...);
```

---

## PHASE 11 — BACKEND: FILAMENT v3 ADMIN PANEL

Configure `AdminPanelProvider` to serve at `admin.velcuri.io` (or path `/admin`).

### Admin Seeder
```php
class AdminSeeder extends Seeder
{
    public function run(): void
    {
        User::create([
            'name' => 'Velcuri Admin',
            'email' => 'admin@velcuri.io',
            'password' => Hash::make('VelcuriAdmin2025!'),
            'role' => 'admin',
            'status' => 'active',
        ]);
    }
}
```

### Resource 1: `ActivationCodeResource`

**Table columns:**
- `code` — copyable badge
- `plan` — badge (trial=gray, monthly=blue, quarterly=indigo, semiannual=purple, annual=green, lifetime=gold)
- `status` — badge (unused=gray, active=success, expired=danger, suspended=warning, cancelled=secondary)
- Devices: `devices_used` / `devices_allowed` shown as "2/3"
- `expiry_date` — human diff ("expires in 30 days" / "expired 5 days ago"); null shows "Lifetime"
- `created_at` — date

**Filters:** Status, Plan, Expiry range

**Header Actions:**
- `GenerateCodesAction` — modal with: Plan (select), Quantity (1–500), Devices Allowed (1/3/5), Custom Expiry (optional date override). On submit: bulk create codes, return downloadable CSV.

**Row Actions:** View Devices, Suspend/Unsuspend (toggle), Reset Devices (clear all devices, set `devices_used=0`), Delete.

**Bulk Actions:** Suspend selected, Delete selected, Export CSV.

### Resource 2: `DeviceResource`

**Table columns:** Activation Code (link), Device Model, Android Version, App Version, Country, Last Seen, Blocked (toggle badge).

**Actions:** Block/Unblock (toggle `is_blocked`), Remove (delete device, decrement `devices_used` on activation code).

### Resource 3: `AppVersionResource`

**Table columns:** Version, Version Code, Force Update (toggle), Active (toggle), Created At.

**Form:** Version string, Version Code (integer), Download URL, Force Update (toggle), Release Notes (textarea), Active (toggle).

**Logic:** When saving a new version and `is_active=true`, auto-deactivate all other versions.

### Resource 4: `SubscriptionResource`

**Table columns:** Activation Code, Plan, Start Date, End Date, Status.

**Actions:** Extend (+30/+90/+365 days modal), Expire Now, Change Plan.

### Dashboard Widgets

Create a `StatsOverviewWidget` with:
- Total Active Codes
- New Activations Today (codes activated today)
- Active Devices (last_seen within 24h)
- Expiring This Week (codes expiring in 7 days)

Create a `ActivationsChartWidget` (line chart) showing activations per day for last 30 days.

---

## PHASE 12 — REPOSITORY SERVER

Create the JSON structure served at `repo.velcuri.io/`:

```
repositories/
├── velcuri/
│   ├── index.json      ← repo manifest
│   └── plugins.json    ← extension list
├── cncverse/
│   ├── index.json
│   └── plugins.json
└── mega/
    ├── index.json
    └── plugins.json
```

**`velcuri/index.json`:**
```json
{
  "name": "Velcuri Repository",
  "description": "Official Velcuri TV extensions",
  "manifestVersion": 1,
  "iconUrl": "https://velcuri.io/assets/logo.png",
  "pluginLists": [
    "https://repo.velcuri.io/velcuri/plugins.json"
  ]
}
```

**`velcuri/plugins.json`:**
```json
{
  "plugins": []
}
```

Start with empty plugin lists. Serve this folder via Nginx as a static site at `repo.velcuri.io`. Extensions (`.cs3` files) are uploaded and `plugins.json` is updated manually or via the admin panel.

---

## PHASE 13 — DEPLOYMENT GUIDE

Create `DEPLOY.md` with these exact steps for Ubuntu 24.04 + aaPanel:

```markdown
# Velcuri TV Deployment Guide

## 1. Server Setup
- Ubuntu 24.04 LTS VPS (minimum 2 vCPU, 4GB RAM)
- Install aaPanel: bash <(curl -o- https://raw.githubusercontent.com/aaPanel/aapanel/master/install.sh)
- Install: Nginx, PHP 8.3, MySQL 8, Redis, Let's Encrypt via aaPanel

## 2. DNS Records (all pointing to VPS IP)
- api.velcuri.io → A record
- admin.velcuri.io → A record  
- repo.velcuri.io → A record
- download.velcuri.io → A record

## 3. Database
CREATE DATABASE velcuri_tv CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'velcuri_user'@'localhost' IDENTIFIED BY 'STRONG_PASSWORD';
GRANT ALL PRIVILEGES ON velcuri_tv.* TO 'velcuri_user'@'localhost';

## 4. Laravel Deployment
cd /www/wwwroot/api.velcuri.io
git clone https://github.com/YOUR_FORK/velcuri-api.git .
composer install --no-dev --optimize-autoloader
cp .env.example .env
php artisan key:generate
# Edit .env with DB credentials
php artisan migrate --force
php artisan db:seed --class=AdminSeeder
php artisan config:cache && php artisan route:cache && php artisan view:cache

## 5. Nginx for api.velcuri.io
root /www/wwwroot/api.velcuri.io/public;
# Standard Laravel Nginx config with try_files / PHP-FPM

## 6. Nginx for repo.velcuri.io
root /www/wwwroot/repo.velcuri.io;
# Static file server, add CORS headers:
add_header Access-Control-Allow-Origin *;
add_header Access-Control-Allow-Methods "GET, OPTIONS";

## 7. Nginx for download.velcuri.io
root /www/wwwroot/download.velcuri.io;
# Static file server for APK files

## 8. Queue Worker (Supervisor via aaPanel)
Command: php /www/wwwroot/api.velcuri.io/artisan queue:work --queue=default --sleep=3 --tries=3

## 9. Firewall (UFW)
ufw allow 80/tcp
ufw allow 443/tcp
ufw allow 22/tcp
ufw enable

## 10. First APK Release
- Build signed APK: ./gradlew assembleRelease
- Sign with your keystore
- Upload to /www/wwwroot/download.velcuri.io/velcuri-tv-1.0.0.apk
- Create AppVersion record in admin panel with download URL
```

---

## PHASE 14 — TESTING CHECKLIST

### API
- [ ] `POST /activate` valid unused code → 200, token returned
- [ ] `POST /activate` same code + same device → 200, re-activation works
- [ ] `POST /activate` expired code → 403, error: `expired`
- [ ] `POST /activate` suspended code → 403, error: `suspended`
- [ ] `POST /activate` devices full → 403, error: `device_limit_reached`
- [ ] `POST /activate` invalid code → 404, error: `invalid_code`
- [ ] `GET /update` old version_code → `update_available: true`
- [ ] `GET /update` latest version_code → `update_available: false`
- [ ] `GET /ping` → 200
- [ ] 6th activate request in 1 min from same IP → 429

### Admin Panel
- [ ] Login at admin.velcuri.io
- [ ] Generate 10 monthly codes → download CSV
- [ ] Suspend a code → API returns 403 for that code
- [ ] View devices on code
- [ ] Remove device → `devices_used` decrements
- [ ] Upload new APK version, mark active, set force_update=true
- [ ] Dashboard stats load without errors

### Android
- [ ] Fresh install → activation screen appears (not setup wizard)
- [ ] Invalid code → correct error message shown
- [ ] Valid code → activation succeeds, proceeds to setup wizard
- [ ] Re-launch after activation → skips activation screen
- [ ] All 3 built-in repos visible without user action
- [ ] No "Add repository" option visible for built-in repos
- [ ] Force update dialog blocks app when `force_update=true`
- [ ] Optional update dialog shows "Later" dismiss option
- [ ] Settings shows "Velcuri TV" not "CloudStream"

---

## API ERROR CODES REFERENCE

| Error Code | HTTP | Description |
|---|---|---|
| `invalid_code` | 404 | Code does not exist |
| `expired` | 403 | Subscription has expired |
| `suspended` | 403 | Manually suspended by admin |
| `cancelled` | 403 | Subscription cancelled |
| `device_limit_reached` | 403 | All device slots used |
| `device_blocked` | 403 | This device was blocked |
| `validation_error` | 422 | Missing/invalid fields |
| `rate_limited` | 429 | Too many requests |

---

## PLAN DURATIONS

| Plan | Days Added | `expiry_date` |
|---|---|---|
| trial | 7 | now + 7 days |
| monthly | 30 | now + 30 days |
| quarterly | 90 | now + 90 days |
| semiannual | 180 | now + 180 days |
| annual | 365 | now + 365 days |
| lifetime | ∞ | `null` |

`isExpired()` always returns `false` when `plan === 'lifetime'` regardless of `expiry_date`.

---

## NOTES FOR CLAUDE CODE

1. **Use CloudStream's `app.get()` / `app.post()`** for all Velcuri API HTTP calls in the Android app. Do not add Retrofit.
2. **Use CloudStream's `setKey()` / `getKey()`** from `DataStore` for storing the activation token. Do not add EncryptedSharedPreferences.
3. **Jackson is already in the project** (`libs.versions.toml`). Use `@JsonProperty` annotations for all model classes.
4. **The package rename must be complete** before any new files are created — all new Kotlin files go in `io.velcuri.tv.*` packages.
5. **Do not modify CloudStream's streaming, player, download, or subtitle logic.** Only touch: MainActivity, InAppUpdater, RepositoryManager (PREBUILT_REPOSITORIES), strings.xml, colors.xml, icons.
6. **Filament v3 syntax** — use `->schema()` in forms, `->columns()` in tables, `Tables\Actions\Action` for custom actions.
7. **`ActivationCode` needs `HasApiTokens`** from Sanctum since tokens are issued directly on that model (not on a User model).
8. **All API timestamps** in responses must be ISO 8601 UTC (`->toIso8601String()`).
9. **The nav graph file** — check whether it's `nav_graph.xml` or split across multiple files before editing. Search the res/navigation/ directory.
10. **Test after each phase.** The Android app should compile after Phase 1-5. The API should return expected responses after Phase 6-10.

---

*End of Velcuri TV Agent Build Prompt — v2.1 (Repo-Aware)*