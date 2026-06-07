package io.velcuri.tv.extractors

import io.velcuri.tv.app
import io.velcuri.tv.network.WebViewResolver
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.M3u8Helper.Companion.generateM3u8

open class WatchSB : ExtractorApi() {
    override var name = "WatchSB"
    override var mainUrl = "https://watchsb.com"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val response = app.get(
            url, interceptor = WebViewResolver(
                Regex("""master\.m3u8""")
            )
        )

        return generateM3u8(name, response.url, url, headers = response.headers.toMap())
    }
}