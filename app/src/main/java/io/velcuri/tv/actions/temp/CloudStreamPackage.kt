package io.velcuri.tv.actions.temp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.fasterxml.jackson.annotation.JsonProperty
import io.velcuri.tv.actions.OpenInAppAction
import io.velcuri.tv.BuildConfig
import io.velcuri.tv.ui.player.ExtractorUri
import io.velcuri.tv.ui.player.SubtitleData
import io.velcuri.tv.ui.player.SubtitleOrigin
import io.velcuri.tv.ui.result.LinkLoadingResult
import io.velcuri.tv.ui.result.ResultEpisode
import io.velcuri.tv.utils.AppUtils.toJson
import io.velcuri.tv.utils.DataStoreHelper.getViewPos
import io.velcuri.tv.utils.DrmExtractorLink
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.ExtractorLinkPlayList
import io.velcuri.tv.utils.ExtractorLinkType
import io.velcuri.tv.utils.newExtractorLink
import io.velcuri.tv.utils.Qualities
import io.velcuri.tv.utils.SubtitleHelper.fromCodeToLangTagIETF
import io.velcuri.tv.utils.SubtitleHelper.fromLanguageToTagIETF
import io.velcuri.tv.utils.txt

/**
 * If you want to support CloudStream 3 as an external player, then this shows how to play any video link
 * For basic interactions, just `intent.data = uri` works
 *
 * However for more advanced use, CloudStream 3 also supports playlists of MinimalVideoLink and MinimalSubtitleLink with a `String[]` of JSON
 * These are passed as LINKS_EXTRA and SUBTITLE_EXTRA respectively
 */
@Suppress("Unused")
class CloudStreamPackage : OpenInAppAction(
    appName = txt("CloudStream"),
    packageName = BuildConfig.APPLICATION_ID, //"io.velcuri.tv" or "io.velcuri.tv.prerelease"
    intentClass = "io.velcuri.tv.ui.player.DownloadedPlayerActivity"
) {
    override val oneSource: Boolean = false

    companion object {
        const val SUBTITLE_EXTRA: String = "subs" // Json of an array of MinimalVideoLink
        const val LINKS_EXTRA: String = "links" // Json of an array of MinimalSubtitleLink
        const val TITLE_EXTRA: String = "title" // Unused (String)
        const val ID_EXTRA: String =
            "id" // Identification number for the video(s), used to store start time (Int)
        const val POSITION_EXTRA: String = "pos" // Start time in MS (Long)
        const val DURATION_EXTRA: String = "dur" // Duration time in MS (Long)
    }

    data class MinimalVideoLink(
        @JsonProperty("uri")
        val uri: Uri?,
        @JsonProperty("url")
        val url: String?,
        @JsonProperty("mimeType")
        val mimeType: String = "video/mp4",
        @JsonProperty("name")
        val name: String?,
        @JsonProperty("headers")
        var headers: Map<String, String> = mapOf(),
        @JsonProperty("quality")
        val quality: Int?,
    ) {
        companion object {
            fun fromExtractor(link: ExtractorLink): MinimalVideoLink = MinimalVideoLink(
                uri = null,
                url = link.url,
                name = link.name,
                mimeType = link.type.getMimeType(),
                headers = if (link.referer.isBlank()) emptyMap() else mapOf("referer" to link.referer) + link.headers,
                quality = link.quality
            )
        }

        suspend fun toExtractorLink(): Pair<ExtractorLink?, ExtractorUri?> =
            url?.let { url ->
                newExtractorLink(
                    source = "NONE",
                    name = name ?: "Unknown",
                    url = url,
                    type = ExtractorLinkType.entries.firstOrNull { ty -> ty.getMimeType() == mimeType }
                        ?: ExtractorLinkType.VIDEO) {

                    this@newExtractorLink.headers =
                        this@MinimalVideoLink.headers

                    this@newExtractorLink.quality =
                        this@MinimalVideoLink.quality ?: Qualities.Unknown.value
                }
            } to uri?.let { uri ->
                ExtractorUri(
                    uri = uri,
                    name = name ?: "Unknown",
                )
            }
    }


    data class MinimalSubtitleLink(
        @JsonProperty("url")
        val url: String,
        @JsonProperty("mimeType")
        val mimeType: String = "text/vtt",
        @JsonProperty("name")
        val name: String?,
        @JsonProperty("headers")
        var headers: Map<String, String> = mapOf(),
    ) {
        companion object {
            fun fromSubtitle(sub: SubtitleData): MinimalSubtitleLink = MinimalSubtitleLink(
                url = sub.url,
                mimeType = sub.mimeType,
                name = sub.originalName,
                headers = sub.headers,
            )
        }

        fun toSubtitleData(): SubtitleData = SubtitleData(
            url = url,
            nameSuffix = "",
            mimeType = mimeType,
            originalName = name ?: "Unknown",
            headers = headers,
            origin = SubtitleOrigin.URL,
            languageCode = fromCodeToLangTagIETF(name) ?:
                           fromLanguageToTagIETF(name, true) ?:
                           name,
        )
    }

    override suspend fun putExtra(
        context: Context,
        intent: Intent,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        intent.apply {
            val position = getViewPos(video.id)?.position
            if (position != null)
                putExtra(POSITION_EXTRA, position)

            putExtra(ID_EXTRA, video.id)
            putExtra(TITLE_EXTRA, video.name)
            putExtra(
                SUBTITLE_EXTRA,
                result.subs.map { MinimalSubtitleLink.fromSubtitle(it).toJson() }.toTypedArray()
            )
            putExtra(
                LINKS_EXTRA,
                result.links.filter { it !is ExtractorLinkPlayList && it !is DrmExtractorLink }
                    .map { MinimalVideoLink.fromExtractor(it).toJson() }.toTypedArray()
            )
        }
    }

    override fun onResult(activity: Activity, intent: Intent?) {
        // No results yet
    }
}