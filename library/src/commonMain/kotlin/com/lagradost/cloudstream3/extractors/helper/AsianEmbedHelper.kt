package com.velcuri.tv.extractors.helper

import com.lagradost.api.Log
import com.velcuri.tv.SubtitleFile
import com.velcuri.tv.amap
import com.velcuri.tv.app
import com.velcuri.tv.utils.ExtractorLink
import com.velcuri.tv.utils.loadExtractor

class AsianEmbedHelper {
    companion object {
        suspend fun getUrls(
            url: String,
            subtitleCallback: (SubtitleFile) -> Unit,
            callback: (ExtractorLink) -> Unit
        ) {
            // Fetch links
            val doc = app.get(url).document
            val links = doc.select("div#list-server-more > ul > li.linkserver")
            if (!links.isNullOrEmpty()) {
                links.amap {
                    val datavid = it.attr("data-video")
                    //Log.i("AsianEmbed", "Result => (datavid) ${datavid}")
                    if (datavid.isNotBlank()) {
                        val res = loadExtractor(datavid, url, subtitleCallback, callback)
                        Log.i("AsianEmbed", "Result => ($res) (datavid) $datavid")
                    }
                }
            }
        }
    }
}