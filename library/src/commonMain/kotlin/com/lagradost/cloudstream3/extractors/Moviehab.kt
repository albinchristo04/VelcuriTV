package com.velcuri.cricketapp.extractors

import com.velcuri.cricketapp.SubtitleFile
import com.velcuri.cricketapp.app
import com.velcuri.cricketapp.newSubtitleFile
import com.velcuri.cricketapp.utils.ExtractorApi
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.M3u8Helper

class MoviehabNet : Moviehab() {
    override var mainUrl = "https://play.moviehab.asia"
}

open class Moviehab : ExtractorApi() {
    override var name = "Moviehab"
    override var mainUrl = "https://play.moviehab.com"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        val res = app.get(url)
        res.document.select("video#player").let {
            //should redirect first for making it works
            val link = app.get("$mainUrl/${it.select("source").attr("src")}", referer = url).url
            M3u8Helper.generateM3u8(
                this.name,
                link,
                url
            ).forEach(callback)

            Regex("src[\"|'],\\s[\"|'](\\S+)[\"|']\\)").find(res.text)?.groupValues?.get(1).let {sub ->
                subtitleCallback.invoke(
                    newSubtitleFile(
                        it.select("track").attr("label"),
                        "$mainUrl/$sub"
                    )
                )
            }
        }
    }
}
