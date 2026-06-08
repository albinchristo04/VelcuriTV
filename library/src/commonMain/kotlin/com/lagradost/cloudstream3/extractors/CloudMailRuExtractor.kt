// ! Bu araç @Kraptor123 tarafından | @kekikanime için yazılmıştır.
package com.velcuri.tv.extractors

import com.velcuri.tv.SubtitleFile
import com.velcuri.tv.USER_AGENT
import com.velcuri.tv.app
import com.velcuri.tv.base64Encode
import com.velcuri.tv.utils.ExtractorApi
import com.velcuri.tv.utils.ExtractorLink
import com.velcuri.tv.utils.INFER_TYPE
import com.velcuri.tv.utils.Qualities
import com.velcuri.tv.utils.newExtractorLink

open class CloudMailRu : ExtractorApi() {
    override val name            = "CloudMailRu"
    override val mainUrl         = "https://cloud.mail.ru"
    override val requiresReferer = false

    override suspend fun getUrl(url: String, referer: String?, subtitleCallback: (SubtitleFile) -> Unit, callback: (ExtractorLink) -> Unit) {
//        Log.d("kraptor_${this.name}","url = $url")
        val headers = mapOf(
            "Accept" to "*/*",
            "Connection" to "keep-alive",
            "Sec-Fetch-Dest" to "empty",
            "Sec-Fetch-Mode" to "cors",
            "Sec-Fetch-Site" to "cross-site",
            "Origin" to mainUrl,
            "User-Agent" to USER_AGENT,
        )
        val vidId      = url.substringAfter("public/").encodeToByteArray()
        val vidIdEnc   = base64Encode(vidId)
        val videoReq   = app.get(url, headers=headers).text
        val regex      = Regex(pattern = "videowl_view\":\\{\"count\":\"1\",\"url\":\"([^\"]*)\"\\}", options = setOf(RegexOption.IGNORE_CASE))
        val videoMatch = regex.find(videoReq)?.groupValues?.get(1).toString()
        val videoUrl   = "$videoMatch/0p/$vidIdEnc.m3u8?double_encode=1"
//        Log.d("kraptor_${this.name}","videoMatch = $videoMatch hani base64 = $vidIdEnc vidurl = $videoUrl")


        callback.invoke(
            newExtractorLink(
                source  = this.name,
                name    = this.name,
                url     = videoUrl,
                type    = INFER_TYPE
            ) {
                this.referer = "$mainUrl/"
                this.quality = Qualities.Unknown.value
                this.headers = headers
            }
        )
    }
}