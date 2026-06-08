package com.velcuri.cricketapp.extractors

import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.extractors.helper.JwPlayerHelper
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink


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
