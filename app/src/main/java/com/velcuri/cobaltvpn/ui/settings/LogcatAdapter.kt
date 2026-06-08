package com.velcuri.cobaltvpn.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.velcuri.cobaltvpn.databinding.ItemLogcatBinding
import com.velcuri.cobaltvpn.ui.BaseDiffCallback
import com.velcuri.cobaltvpn.ui.NoStateAdapter
import com.velcuri.cobaltvpn.ui.ViewHolderState

class LogcatAdapter() : NoStateAdapter<String>(
    diffCallback = BaseDiffCallback(
        itemSame = String::equals,
        contentSame = String::equals
    )
) {
    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        return ViewHolderState(
            ItemLogcatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindContent(holder: ViewHolderState<Any>, item: String, position: Int) {
        (holder.view as? ItemLogcatBinding)?.apply {
            logText.text = item
        }
    }
}