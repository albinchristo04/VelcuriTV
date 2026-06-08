package com.velcuri.cricketapp.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.base64Decode
import com.velcuri.cricketapp.utils.AppUtils
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.getQualityFromName
import com.velcuri.cricketapp.utils.newExtractorLink

open class GUpload: ExtractorApi() {
    override val name: String = "GUpload"
    override val mainUrl: String = "https://gupload.xyz"
    override val requiresReferer: Boolean = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response = app.get(url, referer = referer).text

        val playerConfigEncoded = response.substringAfter("decodePayload('").substringBefore("');")
        val playerConfigString = base64Decode(playerConfigEncoded).substringAfter("|")

        val playerConfig = AppUtils.parseJson<VideoInfo>(playerConfigString)

        callback.invoke(
            newExtractorLink(
                source = name,
                name = name,
                url = playerConfig.videoUrl.replace("\\", ""),
            ) {
                Regex("/(\\d+p)\\.").find(playerConfig.videoUrl)?.groupValues?.get(1)?.let {
                    quality = getQualityFromName(it)
                }
            }
        )
    }

    private data class VideoInfo(
        @JsonProperty("videoUrl") val videoUrl: String,
        @JsonProperty("posterUrl") val posterUrl: String? = null,
        @JsonProperty("videoId") val videoId: String? = null,
        @JsonProperty("primaryColor") val primaryColor: String? = null,
        @JsonProperty("audioTracks") val audioTracks: List<Any?> = emptyList(),
        @JsonProperty("subtitleTracks") val subtitleTracks: List<Any?> = emptyList(),
        @JsonProperty("vastFallbackList") val vastFallbackList: List<String> = emptyList(),
        @JsonProperty("videoOwnerId") val videoOwnerId: Long = 0,
    )
}