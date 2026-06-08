package com.velcuri.tv.activation

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
