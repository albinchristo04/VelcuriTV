package com.velcuri.tv.extractors

import com.velcuri.tv.SubtitleFile
import com.velcuri.tv.app
import com.velcuri.tv.extractors.helper.JwPlayerHelper
import com.velcuri.tv.utils.ExtractorApi
import com.velcuri.tv.utils.ExtractorLink
import com.velcuri.tv.utils.JsUnpacker


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
