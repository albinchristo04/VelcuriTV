package com.velcuri.cricketapp.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.utils.AppUtils
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.Qualities
import com.velcuri.cricketapp.utils.newExtractorLink

open class Uservideo : ExtractorApi() {
    override val name: String = "Uservideo"
    override val mainUrl: String = "https://uservideo.xyz"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val script = app.get(url).document.selectFirst("script:containsData(hosts =)")?.data()
        val host = script?.substringAfter("hosts = [\"")?.substringBefore("\"];")
        val servers = script?.substringAfter("servers = \"")?.substringBefore("\";")

        val sources = app.get("$host/s/$servers").text.substringAfter("\"sources\":[").substringBefore("],").let {
            AppUtils.tryParseJson<List<Sources>>("[$it]")
        }
        val quality = Regex("(\\d{3,4})[Pp]").find(url)?.groupValues?.getOrNull(1)?.toIntOrNull()

        sources?.map { source ->
            callback.invoke(
                newExtractorLink(
                    name,
                    name,
                    source.src ?: return@map null
                ) {
                    this.referer = url
                    this.quality = quality ?: Qualities.Unknown.value
                }
            )
        }

    }

    data class Sources(
        @JsonProperty("src") val src: String? = null,
        @JsonProperty("type") val type: String? = null,
        @JsonProperty("label") val label: String? = null,
    )

}