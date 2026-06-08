package com.velcuri.cricketapp.extractors

import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.extractors.helper.JwPlayerHelper
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.getAndUnpack
import com.velcuri.cricketapp.utils.getPacked

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
