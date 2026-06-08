package com.velcuri.cobaltvpn.actions.temp.fcast

import android.content.Context
import com.velcuri.cobaltvpn.CloudStreamApp.Companion.getActivity
import com.velcuri.cobaltvpn.R
import com.velcuri.cobaltvpn.USER_AGENT
import com.velcuri.cobaltvpn.actions.VideoClickAction
import com.velcuri.cobaltvpn.ui.result.LinkLoadingResult
import com.velcuri.cobaltvpn.ui.result.ResultEpisode
import com.velcuri.cobaltvpn.utils.txt
import com.velcuri.cobaltvpn.utils.DataStoreHelper.getViewPos
import com.velcuri.cobaltvpn.utils.ExtractorLink
import com.velcuri.cobaltvpn.utils.ExtractorLinkType
import com.velcuri.cobaltvpn.utils.SingleSelectionHelper.showBottomDialog

class FcastAction: VideoClickAction() {
    override val name = txt("Fcast to device")

    override val oneSource = true

    override val sourceTypes = setOf(
        ExtractorLinkType.VIDEO,
        ExtractorLinkType.DASH,
        ExtractorLinkType.M3U8
    )

    override fun shouldShow(context: Context?, video: ResultEpisode?) = FcastManager.currentDevices.isNotEmpty()

    override suspend fun runAction(
        context: Context?,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        val link = result.links.getOrNull(index ?: 0) ?: return
        val devices = FcastManager.currentDevices.toList()
        uiThread {
            context?.getActivity()?.showBottomDialog(
                devices.map { it.name },
                -1,
                txt(R.string.player_settings_select_cast_device).asString(context),
                false,
                {}) {
                val position = getViewPos(video.id)?.position
                castTo(devices.getOrNull(it), link, position)
            }
        }
    }


    private fun castTo(device: PublicDeviceInfo?, link: ExtractorLink, position: Long?) {
        val host = device?.host ?: return

        FcastSession(host).use { session ->
            session.sendMessage(
                Opcode.Play,
                PlayMessage(
                    link.type.getMimeType(),
                    link.url,
                    time = position?.let { it / 1000.0 },
                    headers = mapOf(
                        "referer" to link.referer,
                        "user-agent" to USER_AGENT
                    ) + link.headers
                )
            )
        }
    }
}
