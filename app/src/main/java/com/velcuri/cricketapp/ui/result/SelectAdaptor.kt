package com.velcuri.cricketapp.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.velcuri.cricketapp.databinding.ResultSelectionBinding
import com.velcuri.cricketapp.ui.BaseDiffCallback
import com.velcuri.cricketapp.ui.NoStateAdapter
import com.velcuri.cricketapp.ui.ViewHolderState
import com.velcuri.cricketapp.ui.settings.Globals.TV
import com.velcuri.cricketapp.ui.settings.Globals.isLayout
import com.velcuri.cricketapp.utils.UiText
import com.velcuri.cricketapp.utils.setText

typealias SelectData = Pair<UiText?, Any>

class SelectAdaptor(val callback: (Any) -> Unit) :
    NoStateAdapter<SelectData>(diffCallback = BaseDiffCallback(itemSame = { a, b ->
        a.second == b.second
    }, contentSame = { a, b ->
        a == b
    })) {
    private var selectedIndex: Int = -1

    override fun onCreateContent(parent: ViewGroup): ViewHolderState<Any> {
        return ViewHolderState(
            ResultSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindContent(holder: ViewHolderState<Any>, item: SelectData, position: Int) {
        when (val binding = holder.view) {
            is ResultSelectionBinding -> {
                binding.root.apply {
                    if (isLayout(TV)) {
                        isFocusable = true
                        isFocusableInTouchMode = true
                    }

                    isSelected = position == selectedIndex
                    setText(item.first)
                    setOnClickListener {
                        callback.invoke(item.second)
                    }
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolderState<Any>) {
        if (holder.itemView.hasFocus()) {
            holder.itemView.clearFocus()
        }
    }

    fun select(newIndex: Int, recyclerView: RecyclerView?) {
        if (recyclerView == null) return
        if (newIndex == selectedIndex) return
        val oldIndex = selectedIndex
        selectedIndex = newIndex

        notifyItemChanged(selectedIndex)
        notifyItemChanged(oldIndex)
    }
}
