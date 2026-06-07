package io.velcuri.tv.extractors

import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.app
import io.velcuri.tv.extractors.helper.JwPlayerHelper
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.getAndUnpack
import io.velcuri.tv.utils.getPacked

open class StreamoUpload : ExtractorApi() {
    override val name = "StreamoUpload"
    override val mainUrl = "https://streamoupload.xyz"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val response = app.get(url, referer = referer)
        response.document.select("script").map { script ->
            if (getPacked(script.data()) != null) {
                val data = getAndUnpack(script.data())
                JwPlayerHelper.extractStreamLinks(data, name, mainUrl, callback, subtitleCallback)
            }
        }
    }
}
