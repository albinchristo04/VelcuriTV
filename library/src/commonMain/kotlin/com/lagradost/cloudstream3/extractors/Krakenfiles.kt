package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.Qualities
import io.velcuri.tv.utils.httpsify
import io.velcuri.tv.utils.newExtractorLink

open class Krakenfiles : ExtractorApi() {
    override val name = "Krakenfiles"
    override val mainUrl = "https://krakenfiles.com"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val id = Regex("/(?:view|embed-video)/([\\da-zA-Z]+)").find(url)?.groupValues?.get(1)
        val doc = app.get("$mainUrl/embed-video/$id").document
        val link = doc.selectFirst("source")?.attr("src")

        callback.invoke(
            newExtractorLink(
                this.name,
                this.name,
                httpsify(link ?: return),
            )
        )

    }

}