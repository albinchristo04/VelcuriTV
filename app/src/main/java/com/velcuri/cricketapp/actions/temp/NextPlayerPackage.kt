package com.velcuri.cricketapp.actions.temp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.velcuri.cricketapp.actions.OpenInAppAction
import com.velcuri.cricketapp.ui.result.LinkLoadingResult
import com.velcuri.cricketapp.ui.result.ResultEpisode
import com.velcuri.cricketapp.utils.ExtractorLinkType
import com.velcuri.cricketapp.utils.txt

/** https://github.com/anilbeesetti/nextplayer */
class NextPlayerPackage : OpenInAppAction(
    appName = txt("NextPlayer"),
    packageName = "dev.anilbeesetti.nextplayer",
    intentClass = "dev.anilbeesetti.nextplayer.feature.player.PlayerActivity"
) {
    override val sourceTypes: Set<ExtractorLinkType> =
        setOf(ExtractorLinkType.VIDEO, ExtractorLinkType.M3U8, ExtractorLinkType.DASH)

    override val oneSource: Boolean = true

    override suspend fun putExtra(
        context: Context,
        intent: Intent,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        intent.data = result.links[index!!].url.toUri()
    }

    override fun onResult(activity: Activity, intent: Intent?) = Unit
}