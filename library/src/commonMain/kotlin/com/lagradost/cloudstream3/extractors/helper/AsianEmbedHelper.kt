package com.velcuri.cobaltvpn.extractors.helper

import com.lagradost.api.Log
import com.velcuri.cobaltvpn.SubtitleFile
import com.velcuri.cobaltvpn.amap
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.loadExtractor

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