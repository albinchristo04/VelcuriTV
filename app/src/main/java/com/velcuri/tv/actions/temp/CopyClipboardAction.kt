package com.velcuri.tv.actions.temp

import android.content.Context
import com.velcuri.tv.actions.VideoClickAction
import com.velcuri.tv.ui.result.LinkLoadingResult
import com.velcuri.tv.ui.result.ResultEpisode
import com.velcuri.tv.utils.txt
import com.velcuri.tv.utils.UIHelper.clipboardHelper

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