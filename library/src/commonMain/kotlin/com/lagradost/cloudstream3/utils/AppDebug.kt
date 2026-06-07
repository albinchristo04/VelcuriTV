package io.velcuri.tv.utils

import io.velcuri.tv.InternalAPI
import kotlin.concurrent.Volatile

@InternalAPI
object AppDebug {
    @Volatile
    var isDebug: Boolean = false
}
