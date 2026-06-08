package com.velcuri.cobaltvpn.actions.temp

import android.content.Context
import com.velcuri.cobaltvpn.actions.VideoClickAction
import com.velcuri.cobaltvpn.ui.result.LinkLoadingResult
import com.velcuri.cobaltvpn.ui.result.ResultEpisode
import com.velcuri.cobaltvpn.utils.txt
import com.velcuri.cobaltvpn.utils.UIHelper.clipboardHelper

class CopyClipboardAction: VideoClickAction() {
    override val name = txt("Copy to clipboard")

    override val oneSource = true

    override fun shouldShow(context: Context?, video: ResultEpisode?) = true

    override suspend fun runAction(
        context: Context?,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        if (index == null) return
        val link = result.links.getOrNull(index) ?: return
        clipboardHelper(txt(link.name), link.url)
    }
}