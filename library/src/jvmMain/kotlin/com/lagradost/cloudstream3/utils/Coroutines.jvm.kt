package com.velcuri.cricketapp.utils

import androidx.annotation.AnyThread
import androidx.annotation.MainThread

@AnyThread
actual fun runOnMainThreadNative(@MainThread work: () -> Unit) {
    work.invoke()
}
