package com.linkiaM13G3.akmAndroidClient.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.linkiaM13G3.akmAndroidClient.Entities.Tag

class DialogSelectTags(private val context: Context, private val tagList: List<Tag>, private val appliedTags: Array<Tag>?) : DialogFragment() {
    private var listener: OnSelectTagsListener? = null
    private var appliedTagsNames = appliedTags?.map { it.name }

    interface OnSelectTagsListener {
        fun onTagsSelected(tags: Array<Tag>?)
    }

    fun setOnSelectTagsListener(listener: OnSelectTagsListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val tagNames = tagList.map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(tagList.size) { index ->
            appliedTagsNames?.contains(tagList[index].name) == true
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose tags")
                .setPositiveButton("Save") { dialog, id ->
                    val selectedTags = tagList.filterIndexed { index, _ -> checkedItems[index] }.toTypedArray()
                    listener?.onTagsSelected(selectedTags)
                }
                .setMultiChoiceItems(tagNames, checkedItems) { dialog, which, isChecked ->
                    checkedItems[which] = isChecked
                }

        return builder.create()
    }
}