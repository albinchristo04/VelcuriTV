package com.velcuri.cobaltvpn.actions.temp

import android.app.Activity
import android.content.Context
import com.velcuri.cobaltvpn.R
import com.velcuri.cobaltvpn.actions.VideoClickAction
import com.velcuri.cobaltvpn.ui.player.ExtractorUri
import com.velcuri.cobaltvpn.ui.player.GeneratorPlayer
import com.velcuri.cobaltvpn.ui.player.LOADTYPE_INAPP
import com.velcuri.cobaltvpn.ui.player.SubtitleData
import com.velcuri.cobaltvpn.ui.player.VideoGenerator
import com.velcuri.cobaltvpn.ui.result.LinkLoadingResult
import com.velcuri.cobaltvpn.ui.result.ResultEpisode
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.ExtractorLinkType
import com.velcuri.cobaltvpn.utils.UIHelper.navigate
import com.velcuri.cobaltvpn.utils.txt

class PlayMirrorAction : VideoClickAction() {
    override val name = txt(R.string.episode_action_play_mirror)

    override val oneSource = true

    override val isPlayer = true

    override val sourceTypes: Set<ExtractorLinkType> = LOADTYPE_INAPP

    override fun shouldShow(context: Context?, video: ResultEpisode?) = true

    override suspend fun runAction(
        context: Context?,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        //Implemented a generator to handle the single
        val activity = context as? Activity ?: return
        val link = index?.let { result.links[it] }
        val generatorMirror = object : VideoGenerator<ResultEpisode>(listOf(video)) {
            override val hasCache: Boolean = false
            override val canSkipLoading: Boolean = false
            override fun getId(index: Int): Int = video.id

            override suspend fun generateLinks(
                clearCache: Boolean,
                sourceTypes: Set<ExtractorLinkType>,
                callback: (Pair<ExtractorLink?, ExtractorUri?>) -> Unit,
                subtitleCallback: (SubtitleData) -> Unit,
                offset: Int,
                isCasting: Boolean
            ): Boolean {
                index?.let { callback(link to null) }
                result.subs.forEach { subtitle -> subtitleCallback(subtitle) }
                return true
            }
        }

        activity.navigate(
            R.id.global_to_navigation_player,
            GeneratorPlayer.newInstance(
                generatorMirror, 0, result.syncData
            )
        )
    }
}