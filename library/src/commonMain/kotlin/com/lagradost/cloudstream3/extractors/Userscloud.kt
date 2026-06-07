package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.Qualities
import io.velcuri.tv.utils.newExtractorLink

open class Userscloud : ExtractorApi() {
    override val name = "Userscloud"
    override val mainUrl = "https://userscloud.com"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val res = app.get(url).document
        val video = res.selectFirst("video#vjsplayer source")?.attr("src")
        val quality = res.selectFirst("div.innerTB h2 b")?.text()
        callback.invoke(
            newExtractorLink(
                this.name,
                this.name,
                video ?: return,
            ) {
                this.referer = "$mainUrl/"
                this.quality = getQuality(quality)
                this.headers = mapOf(
                    "Accept" to "video/webm,video/ogg,video/*;q=0.9,application/ogg;q=0.7,audio/*;q=0.6,*/*;q=0.5",
                    "Range" to "bytes=0-",
                    "Sec-Fetch-Dest" to "video",
                    "Sec-Fetch-Mode" to "no-cors",
                )
            }
        )
    }

    private fun getQuality(str: String?): Int {
        return Regex("(\\d{3,4})[pP]").find(str ?: "")?.groupValues?.getOrNull(1)?.toIntOrNull()
            ?: Qualities.Unknown.value
    }

}