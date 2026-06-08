package com.velcuri.cobaltvpn.extractors

import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.utils.ExtractorApi
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.ExtractorLinkType
import com.velcuri.cobaltvpn.utils.Qualities
import com.velcuri.cobaltvpn.utils.newExtractorLink

open class EmturbovidExtractor : ExtractorApi() {
    override var name = "Emturbovid"
    override var mainUrl = "https://emturbovid.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val response = app.get(
            url, referer = referer ?: "$mainUrl/"
        )
        val playerScript =
            response.document.selectXpath("//script[contains(text(),'var urlPlay')]")
                .html()

        val sources = mutableListOf<ExtractorLink>()
        if (playerScript.isNotBlank()) {
            val m3u8Url =
                playerScript.substringAfter("var urlPlay = '").substringBefore("'")

            sources.add(
                newExtractorLink(
                    source = name,
                    name = name,
                    url = m3u8Url,
                    type = ExtractorLinkType.M3U8
                ) {
                    this.referer = "$mainUrl/"
                    this.quality = Qualities.Unknown.value
                }
            )
        }
        return sources
    }
}