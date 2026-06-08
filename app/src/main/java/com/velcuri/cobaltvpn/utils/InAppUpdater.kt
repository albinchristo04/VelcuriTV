package com.velcuri.cobaltvpn.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.fasterxml.jackson.annotation.JsonProperty
import com.velcuri.cobaltvpn.activation.ActivationHelper
import com.velcuri.cobaltvpn.BuildConfig
import com.velcuri.cobaltvpn.CommonActivity.showToast
import com.velcuri.cobaltvpn.MainActivity.Companion.deleteFileOnExit
import com.velcuri.cobaltvpn.R
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.mvvm.logError
import com.velcuri.cobaltvpn.mvvm.safe
import com.velcuri.cobaltvpn.services.PackageInstallerService
import com.velcuri.cobaltvpn.utils.AppContextUtils.setDefaultFocus
import com.velcuri.cobaltvpn.utils.AppUtils.parseJson
import com.velcuri.cobaltvpn.utils.Coroutines.ioSafe
import com.velcuri.cobaltvpn.utils.GitInfo.currentCommitHash
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okio.BufferedSink
import okio.buffer
import okio.sink
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

object InAppUpdater {
    private const val LOG_TAG = "InAppUpdater"
    private const val PRERELEASE_PACKAGE_NAME = "com.velcuri.cobaltvpn.prerelease"

    private data class Update(
        @JsonProperty("shouldUpdate") val shouldUpdate: Boolean,
        @JsonProperty("updateURL") val updateURL: String?,
        @JsonProperty("updateVersion") val updateVersion: String?,
        @JsonProperty("changelog") val changelog: String?,
        @JsonProperty("updateNodeId") val updateNodeId: String?,
        @JsonProperty("forceUpdate") val forceUpdate: Boolean = false,
    )

    private suspend fun Activity.getAppUpdate(installPrerelease: Boolean): Update {
        return try {
            if (BuildConfig.DEBUG) return Update(false, null, null, null, null)
            val updateResponse = ActivationHelper.checkUpdate(this)
                ?: return Update(false, null, null, null, null)
            Update(
                shouldUpdate = updateResponse.updateAvailable,
                updateURL = if (updateResponse.updateAvailable) updateResponse.downloadUrl else null,
                updateVersion = updateResponse.latestVersion,
                changelog = updateResponse.releaseNotes,
                updateNodeId = updateResponse.versionCode.toString(),
                forceUpdate = updateResponse.forceUpdate
            )
        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
            Update(false, null, null, null, null)
        }
    }

    private val updateLock = Mutex()

    private suspend fun Activity.downloadUpdate(url: String): Boolean {
        try {
            Log.d(LOG_TAG, "Downloading update: $url")
            val appUpdateName = "VelcuriTV"
            val appUpdateSuffix = "apk"

            // Delete all old updates
            this.cacheDir.listFiles()?.filter {
                it.name.startsWith(appUpdateName) && it.extension == appUpdateSuffix
            }?.forEach { deleteFileOnExit(it) }

            val downloadedFile = File.createTempFile(appUpdateName, ".$appUpdateSuffix")
            val sink: BufferedSink = downloadedFile.sink().buffer()

            updateLock.withLock {
                sink.writeAll(app.get(url).body.source())
                sink.close()
                openApk(this, Uri.fromFile(downloadedFile))
            }

            return true
        } catch (e: Exception) {
            logError(e)
            return false
        }
    }

    private fun openApk(context: Context, uri: Uri) = safe {
        val path = uri.path ?: return@safe
        val contentUri = FileProvider.getUriForFile(
            context, BuildConfig.APPLICATION_ID + ".provider", File(path)
        )
        val installIntent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
            data = contentUri
        }
        context.startActivity(installIntent)
    }

    fun Activity.installPreReleaseIfNeeded() = ioSafe {
        val isInstalled = try {
            packageManager.getPackageInfo(PRERELEASE_PACKAGE_NAME, 0)
            true
        } catch (_: NameNotFoundException) {
            false
        }

        if (isInstalled) {
            showToast(R.string.prerelease_already_installed)
        } else if (!runAutoUpdate(checkAutoUpdate = false, installPrerelease = true)) {
            showToast(R.string.prerelease_install_failed)
        }
    }


    /**
     * @param checkAutoUpdate if the update check was launched automatically
     * @param installPrerelease if we want to install the pre-release version
     */
    suspend fun Activity.runAutoUpdate(
        checkAutoUpdate: Boolean = true, installPrerelease: Boolean = false
    ): Boolean {
        val settingsManager = PreferenceManager.getDefaultSharedPreferences(this)
        val autoUpdateEnabled =
            settingsManager.getBoolean(getString(R.string.auto_update_key), true)
        if (checkAutoUpdate && !autoUpdateEnabled) {
            return false
        }

        val update = getAppUpdate(installPrerelease)
        if (!update.shouldUpdate || update.updateURL == null) {
            return false
        }

        // Check if update should be skipped
        val updateNodeId = settingsManager.getString(
            getString(R.string.skip_update_key), ""
        )

        // Skips the update if its an automatic update and the update is skipped
        // This allows updating manually
        if (update.updateNodeId.equals(updateNodeId) && checkAutoUpdate) {
            return false
        }

        runOnUiThread {
            safe {
                val currentVersion = packageName?.let {
                    packageManager.getPackageInfo(it, 0)
                }

                val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
                builder.setTitle(
                    getString(R.string.new_update_format).format(
                        currentVersion?.versionName, update.updateVersion
                    )
                )

                val logRegex = Regex("\\[(.*?)]\\((.*?)\\)")
                val sanitizedChangelog = update.changelog?.replace(logRegex) { matchResult ->
                    matchResult.groupValues[1]
                } // Sanitized because it looks cluttered

                builder.setMessage(sanitizedChangelog)
                builder.apply {
                    setPositiveButton(R.string.update) { _, _ ->
                        // Forcefully start any delayed installations
                        if (ApkInstaller.delayedInstaller?.startInstallation() == true) return@setPositiveButton

                        showToast(R.string.download_started, Toast.LENGTH_LONG)

                        // Check if the setting hasn't been changed
                        if (settingsManager.getInt(
                                getString(R.string.apk_installer_key), -1
                            ) == -1
                        ) {
                            // Set to legacy installer if using MIUI
                            if (isMiUi()) {
                                settingsManager.edit {
                                    putInt(getString(R.string.apk_installer_key), 1)
                                }
                            }
                        }

                        val currentInstaller = settingsManager.getInt(
                            getString(R.string.apk_installer_key), 1
                        )

                        when (currentInstaller) {
                            // New method
                            0 -> {
                                val intent = PackageInstallerService.Companion.getIntent(
                                    this@runAutoUpdate, update.updateURL
                                )
                                ContextCompat.startForegroundService(
                                    this@runAutoUpdate, intent
                                )
                            }
                            // Legacy
                            1 -> {
                                ioSafe {
                                    if (!downloadUpdate(update.updateURL)) {
                                        runOnUiThread {
                                            showToast(
                                                R.string.download_failed, Toast.LENGTH_LONG
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Only allow dismissing if not a force update
                    if (!update.forceUpdate) {
                        setNegativeButton(R.string.cancel) { _, _ -> }

                        if (checkAutoUpdate) {
                            setNeutralButton(R.string.skip_update) { _, _ ->
                                settingsManager.edit {
                                    putString(
                                        getString(R.string.skip_update_key), update.updateNodeId ?: ""
                                    )
                                }
                            }
                        }
                    }
                }
                builder.show().setDefaultFocus()
            }
        }
        return true
    }

    private fun isMiUi(): Boolean = !getSystemProperty("ro.miui.ui.version.name").isNullOrEmpty()

    private fun getSystemProperty(propName: String): String? = try {
        val p = Runtime.getRuntime().exec("getprop $propName")
        BufferedReader(InputStreamReader(p.inputStream), 1024).use {
            it.readLine()
        }
    } catch (_: IOException) {
        null
    }
}
