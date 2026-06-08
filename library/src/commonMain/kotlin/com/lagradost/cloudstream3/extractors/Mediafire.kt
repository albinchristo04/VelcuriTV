package com.velcuri.cricketapp.extractors

import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.INFER_TYPE
import com.velcuri.cricketapp.utils.Qualities
import com.velcuri.cricketapp.utils.newExtractorLink

open class Mediafire : ExtractorApi() {
    override val name = "Mediafire"
    override val mainUrl = "https://www.mediafire.com"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val res = app.get(url, referer = referer).document
        val title = res.select("div.dl-btn-label").text()
        val video = res.selectFirst("a#downloadButton")?.attr("href")

        callback.invoke(
            newExtractorLink(
                this.name,
                this.name,
                video ?: return
            ) {
                this.quality = getQuality(title)
            }
        )

    }

    private fun getQuality(str: String?): Int {
        return Regex("(\\d{3,4})[pP]").find(str ?: "")?.groupValues?.getOrNull(1)?.toIntOrNull()
            ?: Qualities.Unknown.value
    }

}