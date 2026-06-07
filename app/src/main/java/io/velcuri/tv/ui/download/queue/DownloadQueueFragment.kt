package io.velcuri.tv.ui.download.queue

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import io.velcuri.tv.R
import io.velcuri.tv.databinding.FragmentDownloadQueueBinding
import io.velcuri.tv.mvvm.observe
import io.velcuri.tv.ui.BaseFragment
import io.velcuri.tv.ui.settings.Globals.EMULATOR
import io.velcuri.tv.ui.settings.Globals.PHONE
import io.velcuri.tv.ui.settings.Globals.TV
import io.velcuri.tv.ui.settings.Globals.isLandscape
import io.velcuri.tv.ui.settings.Globals.isLayout
import io.velcuri.tv.utils.UIHelper.fixSystemBarsPadding
import io.velcuri.tv.utils.UIHelper.setAppBarNoScrollFlagsOnTV
import io.velcuri.tv.utils.downloader.DownloadQueueManager
import io.velcuri.tv.utils.txt


class DownloadQueueFragment :
    BaseFragment<FragmentDownloadQueueBinding>(BindingCreator.Inflate(FragmentDownloadQueueBinding::inflate)) {
    private val queueViewModel: DownloadQueueViewModel by activityViewModels()

    override fun onBindingCreated(binding: FragmentDownloadQueueBinding) {
        val adapter = DownloadQueueAdapter(this@DownloadQueueFragment)
        val clearQueueItem = binding.downloadQueueToolbar.menu?.findItem(R.id.cancel_all)

        observe(queueViewModel.childCards) { cards ->
            val size = cards.queue.size + cards.currentDownloads.size
            val isEmptyQueue = size == 0
            binding.downloadQueueList.isGone = isEmptyQueue
            binding.textNoQueue.isGone = !isEmptyQueue
            clearQueueItem?.isVisible = !isEmptyQueue

            adapter.submitQueue(cards)
        }

        binding.apply {
            downloadQueueToolbar.apply {
                title = txt(R.string.download_queue).asString(context)
                if (isLayout(PHONE or EMULATOR)) {
                    setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                    setNavigationOnClickListener {
                        dispatchBackPressed()
                    }
                }
                setAppBarNoScrollFlagsOnTV()
                clearQueueItem?.setOnMenuItemClickListener {
                        AlertDialog.Builder(context, R.style.AlertDialogCustom)
                            .setTitle(R.string.cancel_all)
                            .setMessage(R.string.cancel_queue_message)
                            .setPositiveButton(R.string.yes) { _, _ ->
                                DownloadQueueManager.removeAllFromQueue()
                            }
                            .setNegativeButton(R.string.no) { _, _ ->
                            }.show()

                    true
                }
            }

            downloadQueueList.adapter = adapter

            // Drag and drop
            val helper = DragAndDropTouchHelper(adapter)
            helper.attachToRecyclerView(downloadQueueList)
        }
    }
    
    override fun fixLayout(view: View) {
        fixSystemBarsPadding(
            view,
            padBottom = isLandscape(),
            padLeft = isLayout(TV or EMULATOR)
        )
    }
}