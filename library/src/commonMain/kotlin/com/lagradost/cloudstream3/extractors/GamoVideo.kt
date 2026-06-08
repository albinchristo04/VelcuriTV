package com.velcuri.cobaltvpn.extractors

import com.velcuri.cobaltvpn.SubtitleFile
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.extractors.helper.JwPlayerHelper
import com.velcuri.cobaltvpn.utils.ExtractorApi
import com.velcuri.cobaltvpn.utils.ExtractorLink


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
