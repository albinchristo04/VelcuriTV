package com.velcuri.cobaltvpn.extractors

import com.velcuri.cobaltvpn.SubtitleFile
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.utils.ExtractorApi
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.INFER_TYPE
import com.velcuri.cobaltvpn.utils.Qualities
import com.velcuri.cobaltvpn.utils.newExtractorLink

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