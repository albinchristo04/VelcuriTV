package io.velcuri.tv.extractors

import io.velcuri.tv.app
import io.velcuri.tv.utils.ExtractorApi
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.ExtractorLinkType
import io.velcuri.tv.utils.INFER_TYPE
import io.velcuri.tv.utils.JsUnpacker
import io.velcuri.tv.utils.Qualities
import io.velcuri.tv.utils.newExtractorLink

open class Streamhub : ExtractorApi() {
    override var mainUrl = "https://streamhub.to"
    override var name = "Streamhub"
    override val requiresReferer = false

    override fun getExtractorUrl(id: String): String {
        return "$mainUrl/e/$id"
    }

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val response = app.get(url).text
        Regex("eval((.|\\n)*?)</script>").find(response)?.groupValues?.get(1)?.let { jsEval ->
            JsUnpacker("eval$jsEval").unpack()?.let { unPacked ->
                Regex("sources:\\[\\{src:\"(.*?)\"").find(unPacked)?.groupValues?.get(1)?.let { link ->
                    return listOf(
                        newExtractorLink(
                            source = this.name,
                            this.name,
                            link,
                        )
                    )
                }
            }
        }
        return null
    }
}