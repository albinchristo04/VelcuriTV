package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.INFER_TYPE
import io.velcuri.tv.utils.Qualities
import io.velcuri.tv.utils.newExtractorLink

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