package com.velcuri.cobaltvpn.extractors

import com.velcuri.cobaltvpn.SubtitleFile
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.extractors.helper.JwPlayerHelper
import com.velcuri.cobaltvpn.utils.ExtractorApi
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.JsUnpacker


open class Vtbe : ExtractorApi() {
    override var name = "Vtbe"
    override var mainUrl = "https://vtbe.to"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response = app.get(url,referer=mainUrl).document
        val extractedpack = response.selectFirst("script:containsData(function(p,a,c,k,e,d))")?.data().toString()
        JsUnpacker(extractedpack).unpack()?.let { unPacked ->
            JwPlayerHelper.extractStreamLinks(unPacked, name, mainUrl, callback, subtitleCallback)
        }
    }
}
