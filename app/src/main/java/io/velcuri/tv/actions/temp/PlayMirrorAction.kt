package io.velcuri.tv.actions.temp

import android.app.Activity
import android.content.Context
import io.velcuri.tv.R
import io.velcuri.tv.actions.VideoClickAction
import io.velcuri.tv.ui.player.ExtractorUri
import io.velcuri.tv.ui.player.GeneratorPlayer
import io.velcuri.tv.ui.player.LOADTYPE_INAPP
import io.velcuri.tv.ui.player.SubtitleData
import io.velcuri.tv.ui.player.VideoGenerator
import io.velcuri.tv.ui.result.LinkLoadingResult
import io.velcuri.tv.ui.result.ResultEpisode
import io.velcuri.tv.utils.ExtractorLink
import io.velcuri.tv.utils.ExtractorLinkType
import io.velcuri.tv.utils.UIHelper.navigate
import io.velcuri.tv.utils.txt

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