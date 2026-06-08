package com.velcuri.tv.actions.temp

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.velcuri.tv.actions.OpenInAppAction
import com.velcuri.tv.ui.result.LinkLoadingResult
import com.velcuri.tv.ui.result.ResultEpisode
import com.velcuri.tv.utils.txt

/** https://github.com/devgianlu/Aria2Android */
@Suppress("unused")
class Aria2Package : OpenInAppAction(
    appName = txt("Aria2"),
    packageName = "com.gianlu.aria2android",
    intentClass = "com.gianlu.aria2android.MainActivity"
) {
    override val oneSource: Boolean = true
    override suspend fun putExtra(
        context: Context,
        intent: Intent,
        video: ResultEpisode,
        result: LinkLoadingResult,
        index: Int?
    ) {
        throw NotImplementedError("Aria2Android is missing getIntent, and onNewIntent, meaning it cant handle intents")
    }

    override fun onResult(activity: Activity, intent: Intent?) = Unit
}