package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.extractors.helper.JwPlayerHelper
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.JsUnpacker

open class Supervideo : ExtractorApi() {
    override var name = "Supervideo"
    override var mainUrl = "https://supervideo.cc"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response = app.get(url).text
        val jstounpack = Regex("eval((.|\\n)*?)</script>").find(response)?.groups?.get(1)?.value
        val unpacked = JsUnpacker(jstounpack).unpack()

        JwPlayerHelper.extractStreamLinks(unpacked.orEmpty(), name, mainUrl, callback, subtitleCallback)
    }
}