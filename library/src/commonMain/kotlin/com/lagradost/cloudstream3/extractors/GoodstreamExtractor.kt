package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.Qualities
import io.velcuri.tv.utils.newExtractorLink

class GoodstreamExtractor : ExtractorApi() {
    override var name = "Goodstream"
    override val mainUrl = "https://goodstream.uno"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        app.get(url).document.select("script").map { script ->
            if (script.data().contains(Regex("file|player"))) {
                val urlRegex = Regex("file: \"(https:\\/\\/[a-z0-9.\\/-_?=&]+)\",")
                urlRegex.find(script.data())?.groupValues?.get(1).let { link ->
                    callback.invoke(
                        newExtractorLink(
                            name,
                            name,
                            link!!,
                        ) {
                            this.referer = mainUrl
                        }
                    )
                }
            }
        }
    }
}