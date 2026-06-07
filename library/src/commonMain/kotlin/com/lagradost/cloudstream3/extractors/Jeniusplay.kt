package io.velcuri.tv.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.extractors.helper.JwPlayerHelper
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.M3u8Helper
import io.velcuri.tv.utils.getAndUnpack
import io.velcuri.tv.utils.getPacked

open class Jeniusplay : ExtractorApi() {
    override val name = "Jeniusplay"
    override val mainUrl = "https://jeniusplay.com"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val document = app.get(url, referer = "$mainUrl/").document
        val hash = url.split("/").last().substringAfter("data=")

        val m3uLink = app.post(
            url = "$mainUrl/player/index.php?data=$hash&do=getVideo",
            data = mapOf("hash" to hash, "r" to "$referer"),
            referer = url,
            headers = mapOf("X-Requested-With" to "XMLHttpRequest")
        ).parsed<ResponseSource>().videoSource

        M3u8Helper.generateM3u8(
            this.name,
            m3uLink,
            url,
        ).forEach(callback)

        document.select("script").map { script ->
            if (getPacked(script.data()) != null) {
                val unpacked = getAndUnpack(script.data())
                JwPlayerHelper.extractStreamLinks(unpacked, name, mainUrl, callback, subtitleCallback)
            }
        }
    }

    data class ResponseSource(
        @JsonProperty("hls") val hls: Boolean,
        @JsonProperty("videoSource") val videoSource: String,
        @JsonProperty("securedLink") val securedLink: String?,
    )
}