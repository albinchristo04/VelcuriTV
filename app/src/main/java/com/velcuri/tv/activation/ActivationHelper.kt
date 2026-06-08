package com.velcuri.tv.activation

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.velcuri.tv.app
import com.velcuri.tv.BuildConfig
import com.velcuri.tv.CloudStreamApp.Companion.getKey
import com.velcuri.tv.CloudStreamApp.Companion.setKey
import com.velcuri.tv.utils.AppUtils.tryParseJson
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
