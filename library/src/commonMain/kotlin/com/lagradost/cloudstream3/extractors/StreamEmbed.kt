package com.velcuri.cricketapp.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.utils.AppUtils.parseJson
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.M3u8Helper

open class StreamEmbed : ExtractorApi() {
    override var name = "StreamEmbed"
    override var mainUrl = "https://watch.gxplayer.xyz"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val jsonString = app.get(url, referer = mainUrl).text
            .substringAfter("var video = ").substringBefore(";")
        val video = parseJson<Details>(jsonString)

        M3u8Helper.generateM3u8(
            this.name,
            "$mainUrl/m3u8/${video.uid}/${video.md5}/master.txt?s=1&id=${video.id}&cache=${video.status}",
            referer = "$mainUrl/",
        ).forEach(callback)
    }

    private data class Details(
        @JsonProperty("id") val id: String,
        @JsonProperty("uid") val uid: String,
        @JsonProperty("slug") val slug: String,
        @JsonProperty("title") val title: String,
        @JsonProperty("quality") val quality: String,
        @JsonProperty("type") val type: String,
        @JsonProperty("status") val status: String,
        @JsonProperty("md5") val md5: String,
    )
}
