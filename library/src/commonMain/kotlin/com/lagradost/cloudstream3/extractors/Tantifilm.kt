package com.velcuri.cricketapp.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.AppUtils.parseJson
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.newExtractorLink

open class Tantifilm : ExtractorApi() {
    override var name = "Tantifilm"
    override var mainUrl = "https://cercafilm.net"
    override val requiresReferer = false

    data class TantifilmJsonData (
        @JsonProperty("success") val success : Boolean,
        @JsonProperty("data") val data : List<TantifilmData>,
        @JsonProperty("captions")val captions : List<String>,
        @JsonProperty("is_vr") val is_vr : Boolean
    )

    data class TantifilmData (
        @JsonProperty("file") val file : String,
        @JsonProperty("label") val label : String,
        @JsonProperty("type") val type : String
    )

    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val link = "$mainUrl/api/source/${url.substringAfterLast("/")}"
        val response = app.post(link).text.replace("""\""","")
        val jsonvideodata = parseJson<TantifilmJsonData>(response)
        return jsonvideodata.data.map {
            newExtractorLink(
                this.name,
                this.name,
                it.file+".${it.type}"
            ) {
                this.referer = mainUrl
                this.quality = it.label.filter{ it.isDigit() }.toInt()
            }
        }
    }
}