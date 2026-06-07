package io.velcuri.tv.ui.settings.extensions

import android.content.res.ColorStateList
import android.text.format.Formatter.formatFileSize
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import io.velcuri.tv.CloudStreamApp.Companion.openBrowser
import io.velcuri.tv.databinding.FragmentPluginDetailsBinding
import io.velcuri.tv.plugins.PluginManager
import io.velcuri.tv.plugins.VotingApi.canVote
import io.velcuri.tv.plugins.VotingApi.getVotes
import io.velcuri.tv.plugins.VotingApi.hasVoted
import io.velcuri.tv.plugins.VotingApi.vote
import io.velcuri.tv.R
import io.velcuri.tv.ui.BaseBottomSheetDialogFragment
import io.velcuri.tv.ui.BaseFragment
import io.velcuri.tv.ui.settings.Globals.EMULATOR
import io.velcuri.tv.ui.settings.Globals.TV
import io.velcuri.tv.ui.settings.Globals.isLandscape
import io.velcuri.tv.ui.settings.Globals.isLayout
import io.velcuri.tv.utils.Coroutines.ioSafe
import io.velcuri.tv.utils.Coroutines.main
import io.velcuri.tv.utils.getImageFromDrawable
import io.velcuri.tv.utils.ImageLoader.loadImage
import io.velcuri.tv.utils.SubtitleHelper.getNameNextToFlagEmoji
import io.velcuri.tv.utils.UIHelper.colorFromAttribute
import io.velcuri.tv.utils.UIHelper.fixSystemBarsPadding
import io.velcuri.tv.utils.UIHelper.toPx

class PluginDetailsFragment(val data: PluginViewData) : BaseBottomSheetDialogFragment<FragmentPluginDetailsBinding>(
    BaseFragment.BindingCreator.Inflate(FragmentPluginDetailsBinding::inflate)
) {

    companion object {
        private tailrec fun findClosestBase2(target: Int, current: Int = 16, max: Int = 512): Int {
            if (current >= max) return max
            if (current >= target) return current
            return findClosestBase2(target, current * 2, max)
        }

        private val iconSizeExact = 50.toPx
        private val iconSize by lazy {
            findClosestBase2(iconSizeExact, 16, 512)
        }
    }

    override fun fixLayout(view: View) {
        fixSystemBarsPadding(
            view,
            padBottom = isLandscape(),
            padLeft = isLayout(TV or EMULATOR)
        )
    }

    override fun onBindingCreated(binding: FragmentPluginDetailsBinding) {
        val metadata = data.plugin.second
        binding.apply {
            pluginIcon.loadImage(metadata.iconUrl?.replace("%size%", "$iconSize")
                ?.replace("%exact_size%", "$iconSizeExact")) {
                error { getImageFromDrawable(context ?: return@error null , R.drawable.ic_baseline_extension_24) }
            }
            pluginName.text = metadata.name.removeSuffix("Provider")
            pluginVersion.text = metadata.version.toString()
            pluginDescription.text = metadata.description ?: getString(R.string.no_data)
            pluginSize.text =
                if (metadata.fileSize == null) getString(R.string.no_data) else formatFileSize(
                    context,
                    metadata.fileSize
                )
            pluginAuthor.text =
                if (metadata.authors.isEmpty()) getString(R.string.no_data) else metadata.authors.joinToString(
                    ", "
                )
            pluginStatus.text =
                resources.getStringArray(R.array.extension_statuses)[metadata.status]
            pluginTypes.text =
                if (metadata.tvTypes.isNullOrEmpty()) getString(R.string.no_data) else metadata.tvTypes.joinToString(
                    ", "
                )
            pluginLang.text = if (metadata.language == null)
                    getString(R.string.no_data)
                else
                    getNameNextToFlagEmoji(metadata.language) ?: metadata.language

            githubBtn.setOnClickListener {
                if (metadata.repositoryUrl != null) {
                    openBrowser(metadata.repositoryUrl)
                }
            }

            if (!metadata.canVote()) {
                upvote.alpha = .6f
            }

            if (data.isDownloaded) {
                // On local plugins page the filepath is provided instead of url.
                val plugin =
                    (PluginManager.urlPlugins[metadata.url] ?: PluginManager.plugins[metadata.url]) as? io.velcuri.tv.plugins.Plugin
                if (plugin?.openSettings != null && context != null) {
                    actionSettings.isVisible = true
                    actionSettings.setOnClickListener {
                        try {
                            plugin.openSettings!!.invoke(requireContext())
                        } catch (e: Throwable) {
                            Log.e(
                                "PluginAdapter",
                                "Failed to open ${metadata.name} settings: ${
                                    Log.getStackTraceString(e)
                                }"
                            )
                        }
                    }
                } else {
                    actionSettings.isVisible = false
                }
            } else {
                actionSettings.isVisible = false
            }

            upvote.setOnClickListener {
                ioSafe {
                    metadata.vote().main {
                        updateVoting(it)
                    }
                }
            }

            ioSafe {
                metadata.getVotes().main {
                    updateVoting(it)
                }
            }
        }
    }

    private fun updateVoting(value: Int) {
        val metadata = data.plugin.second
        binding?.apply {
            pluginVotes.text = value.toString()
            if (metadata.hasVoted()) {
                upvote.imageTintList = ColorStateList.valueOf(
                    context?.colorFromAttribute(R.attr.colorPrimary) ?: R.color.colorPrimary
                )
            } else {
                upvote.imageTintList = ColorStateList.valueOf(
                    context?.colorFromAttribute(com.google.android.material.R.attr.colorOnSurface) ?: R.color.white
                )
            }
        }
    }
}