package com.linkiaM13G3.akmAndroidClient.Helpers;

import android.content.Context
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.linkiaM13G3.akmAndroidClient.R

class Updaters {
    companion object {
        fun updateChipGroup(group: ChipGroup, tags: Array<Tag>, context: Context) {
            group.removeAllViews()
            for (tag in tags) {
                val chip = Chip(context)
                chip.text = tag.name
                chip.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
                chip.isClickable = false
                chip.setChipBackgroundColorResource(R.color.grey_700)
                chip.setTextColor(ContextCompat.getColor(context, R.color.white))
                group.addView(chip)
            }
        }
    }
}
