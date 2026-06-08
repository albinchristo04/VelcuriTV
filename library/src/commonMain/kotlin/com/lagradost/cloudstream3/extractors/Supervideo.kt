package com.velcuri.cobaltvpn.extractors

import com.velcuri.cobaltvpn.SubtitleFile
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.extractors.helper.JwPlayerHelper
import com.velcuri.cobaltvpn.utils.ExtractorApi
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.JsUnpacker

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