package io.velcuri.tv.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import io.velcuri.tv.LoadResponse
import io.velcuri.tv.databinding.HomeScrollViewBinding
import io.velcuri.tv.databinding.HomeScrollViewTvBinding
import io.velcuri.tv.ui.BaseDiffCallback
import io.velcuri.tv.ui.NoStateAdapter
import io.velcuri.tv.ui.ViewHolderState
import io.velcuri.tv.ui.result.ResultFragment.bindLogo
import io.velcuri.tv.ui.settings.Globals.EMULATOR
import io.velcuri.tv.ui.settings.Globals.TV
import io.velcuri.tv.ui.settings.Globals.isLayout
import io.velcuri.tv.utils.AppContextUtils.html
import io.velcuri.tv.utils.ImageLoader.loadImage

class HomeScrollAdapter(
    val callback: ((View, Int, LoadResponse) -> Unit)
) : NoStateAdapter<LoadResponse>(diffCallback = BaseDiffCallback(itemSame = { a, b ->
    a.uniqueUrl == b.uniqueUrl && a.name == b.name
})) {
    var hasMoreItems: Boolean = false

    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (isLayout(TV or EMULATOR)) {
            HomeScrollViewTvBinding.inflate(inflater, parent, false)
        } else {
            HomeScrollViewBinding.inflate(inflater, parent, false)
        }

        return ViewHolderState(binding)
    }

    override fun onClearView(holder: ViewHolderState<Any>) {
        when (val binding = holder.view) {
            is HomeScrollViewBinding -> {
                clearImage(binding.homeScrollPreview)
            }

            is HomeScrollViewTvBinding -> {
                clearImage(binding.homeScrollPreview)
            }
        }
    }

    override fun onBindContent(
        holder: ViewHolderState<Any>,
        item: LoadResponse,
        position: Int,
    ) {
        val binding = holder.view

        val posterUrl = item.backgroundPosterUrl ?: item.posterUrl

        when (binding) {
            is HomeScrollViewBinding -> {
                binding.homeScrollPreview.loadImage(posterUrl)
                binding.homeScrollPreviewTags.apply {
                    text = item.tags?.joinToString(" • ") ?: ""
                    isGone = item.tags.isNullOrEmpty()
                    maxLines = 2
                }
                binding.homeScrollPreviewTitle.text = item.name.html()

                bindLogo(
                    url = item.logoUrl,
                    headers = item.posterHeaders,
                    titleView = binding.homeScrollPreviewTitle,
                    logoView = binding.homePreviewLogo
                )
            }

            is HomeScrollViewTvBinding -> {
                binding.homeScrollPreview.isFocusable = false
                binding.homeScrollPreview.setOnClickListener { view ->
                    callback.invoke(view ?: return@setOnClickListener, position, item)
                }
                binding.homeScrollPreview.loadImage(posterUrl)
            }
        }
    }
}