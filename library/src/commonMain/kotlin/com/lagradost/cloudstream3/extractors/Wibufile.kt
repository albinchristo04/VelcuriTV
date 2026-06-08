package com.velcuri.tv.extractors

import com.velcuri.tv.SubtitleFile
import com.velcuri.tv.app
import com.velcuri.tv.utils.ExtractorApi
import com.velcuri.tv.utils.ExtractorLink
import com.velcuri.tv.utils.INFER_TYPE
import com.velcuri.tv.utils.Qualities
import com.velcuri.tv.utils.newExtractorLink

open class Wibufile : ExtractorApi() {
    override val name: String = "Wibufile"
    override val mainUrl: String = "https://wibufile.com"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val res = app.get(url).text
        val video = Regex("src: ['\"](.*?)['\"]").find(res)?.groupValues?.get(1)

        callback.invoke(
            newExtractorLink(
                name,
                name,
                video ?: return,
            ) {
                this.referer = "$mainUrl/"
                this.quality = Qualities.Unknown.value
            }
        )
    }
}