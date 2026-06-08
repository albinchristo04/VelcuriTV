package com.velcuri.tv.actions.temp

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.velcuri.tv.R
import com.velcuri.tv.actions.VideoClickAction
import com.velcuri.tv.ui.result.LinkLoadingResult
import com.velcuri.tv.ui.result.ResultEpisode
import com.velcuri.tv.utils.txt
import com.velcuri.tv.utils.ExtractorLinkType

class PlayInBrowserAction: VideoClickAction() {
    override val name = txt(R.string.episode_action_play_in_format, "Browser")

    override val oneSource = true

    override val isPlayer = true

    override val sourceTypes: Set<ExtractorLinkType> = setOf(
        ExtractorLinkType.VIDEO,
        ExtractorLinkType.DASH,
        ExtractorLinkType.M3U8
    )

    override fun shouldShow(context: Context?, video: ResultEpisode?) = true

    override suspend fun runAction(
        context: Context?,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        val link = result.links.getOrNull(index ?: 0) ?: return
        val i = Intent(Intent.ACTION_VIEW)
        i.data = link.url.toUri()
        launch(i)
    }
}