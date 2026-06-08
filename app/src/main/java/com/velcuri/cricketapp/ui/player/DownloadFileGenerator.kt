package com.velcuri.cricketapp.ui.player

import android.net.Uri
import com.velcuri.cricketapp.CloudStreamApp.Companion.context
import com.velcuri.cricketapp.CommonActivity.activity
import com.velcuri.cricketapp.R
import com.velcuri.cricketapp.ui.player.PlayerSubtitleHelper.Companion.toSubtitleMimeType
import com.velcuri.cricketapp.utils.ExtractorLink
import com.velcuri.cricketapp.utils.ExtractorLinkType
import com.velcuri.cricketapp.utils.SubtitleHelper.fromLanguageToTagIETF
import com.velcuri.cricketapp.utils.SubtitleUtils.cleanDisplayName
import com.velcuri.cricketapp.utils.SubtitleUtils.isMatchingSubtitle
import com.velcuri.cricketapp.utils.downloader.DownloadFileManagement.getFolder
import com.velcuri.cricketapp.utils.downloader.VideoDownloadManager.getDownloadFileInfo

class DownloadFileGenerator(
    episodes: List<ExtractorUri>
) : VideoGenerator<ExtractorUri>(episodes) {
    override val hasCache = false
    override val canSkipLoading = false

    override fun getId(index: Int): Int? = this.videos.getOrNull(index)?.id

    override suspend fun generateLinks(
        clearCache: Boolean,
        sourceTypes: Set<ExtractorLinkType>,
        callback: (Pair<ExtractorLink?, ExtractorUri?>) -> Unit,
        subtitleCallback: (SubtitleData) -> Unit,
        offset: Int,
        isCasting: Boolean
    ): Boolean {
        val meta = videos.getOrNull(offset) ?: return false

        if (meta.uri == Uri.EMPTY) {
            // We do this here so that we only load it when
            // we actually need it as it can be more expensive.
            val info = meta.id?.let { id ->
                activity?.let { act ->
                    getDownloadFileInfo(act, id)
                }
            }

            if (info != null) {
                val newMeta = meta.copy(uri = info.path)
                callback(null to newMeta)
            } else callback(null to meta)
        } else callback(null to meta)

        val ctx = context ?: return true
        val relative = meta.relativePath ?: return true
        val display = meta.displayName ?: return true

        val cleanDisplay = cleanDisplayName(display)

        getFolder(ctx, relative, meta.basePath)?.forEach { (name, uri) ->
            if (isMatchingSubtitle(name, display, cleanDisplay)) {
                val cleanName = cleanDisplayName(name)
                val lastNum = Regex(" ([0-9]+)$")
                val nameSuffix = lastNum.find(cleanName)?.groupValues?.get(1) ?: ""
                val originalName = cleanName.removePrefix(cleanDisplay).replace(lastNum, "").trim()

                subtitleCallback(
                    SubtitleData(
                        originalName.ifBlank { ctx.getString(R.string.default_subtitles) },
                        nameSuffix,
                        uri.toString(),
                        SubtitleOrigin.DOWNLOADED_FILE,
                        name.toSubtitleMimeType(),
                        emptyMap(),
                        fromLanguageToTagIETF(originalName, true)
                    )
                )
            }
        }

        return true
    }
}