package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.extractors.helper.JwPlayerHelper
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink


open class GamoVideo : ExtractorApi() {
    override val name = "GamoVideo"
    override val mainUrl = "https://gamovideo.com"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        app.get(url, referer = referer).document.select("script")
            .firstOrNull { JwPlayerHelper.canParseJwScript(it.data()) }!!.let {
                JwPlayerHelper.extractStreamLinks(it.data(), name, mainUrl, callback, subtitleCallback)
            }
    }
}
