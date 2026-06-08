package com.velcuri.cricketapp.utils

import com.velcuri.cricketapp.InternalAPI
import kotlin.concurrent.Volatile

@InternalAPI
object AppDebug {
    @Volatile
    var isDebug: Boolean = false
}
