package io.velcuri.tv.ui.player

import android.net.Uri
import io.velcuri.tv.CloudStreamApp.Companion.context
import io.velcuri.tv.CommonActivity.activity
import io.velcuri.tv.R
import io.velcuri.tv.ui.player.PlayerSubtitleHelper.Companion.toSubtitleMimeType
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.ExtractorLinkType
import io.velcuri.tv.utils.SubtitleHelper.fromLanguageToTagIETF
import io.velcuri.tv.utils.SubtitleUtils.cleanDisplayName
import io.velcuri.tv.utils.SubtitleUtils.isMatchingSubtitle
import io.velcuri.tv.utils.downloader.DownloadFileManagement.getFolder
import io.velcuri.tv.utils.downloader.VideoDownloadManager.getDownloadFileInfo

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