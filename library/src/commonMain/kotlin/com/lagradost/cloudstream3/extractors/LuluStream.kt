package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.extractors.helper.JwPlayerHelper
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink


class Luluvdoo : LuluStream() {
    override var mainUrl = "https://luluvdoo.com"
}

class Lulustream1 : LuluStream() {
    override val name = "Lulustream"
    override val mainUrl = "https://lulustream.com"
}

class Lulustream2 : LuluStream() {
    override val name = "Lulustream"
    override val mainUrl = "https://kinoger.pw"
}

open class LuluStream : ExtractorApi() {
    override val  name = "LuluStream"
    override val mainUrl = "https://luluvdo.com"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val filecode = url.substringAfterLast("/")
        val postUrl = "$mainUrl/dl"
        val post = app.post(
            postUrl,
            data = mapOf(
                "op" to "embed",
                "file_code" to filecode,
                "auto" to "1",
                "referer" to (referer ?: "")
            )
        ).document
        post.selectFirst("script:containsData(vplayer)")?.data()
            ?.let { script ->
                JwPlayerHelper.extractStreamLinks(script, name, mainUrl, callback, subtitleCallback)
            }
    }
}