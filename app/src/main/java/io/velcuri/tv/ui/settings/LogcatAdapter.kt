package io.velcuri.tv.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import io.velcuri.tv.databinding.ItemLogcatBinding
import io.velcuri.tv.ui.BaseDiffCallback
import io.velcuri.tv.ui.NoStateAdapter
import io.velcuri.tv.ui.ViewHolderState

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