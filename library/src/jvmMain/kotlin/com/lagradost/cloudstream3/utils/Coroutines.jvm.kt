package com.velcuri.cobaltvpn.utils

import androidx.annotation.AnyThread
import androidx.annotation.MainThread

@AnyThread
actual fun runOnMainThreadNative(@MainThread work: () -> Unit) {
    work.invoke()
}
