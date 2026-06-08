package com.velcuri.cobaltvpn.utils

import com.velcuri.cobaltvpn.InternalAPI
import kotlin.concurrent.Volatile

@InternalAPI
object AppDebug {
    @Volatile
    var isDebug: Boolean = false
}
