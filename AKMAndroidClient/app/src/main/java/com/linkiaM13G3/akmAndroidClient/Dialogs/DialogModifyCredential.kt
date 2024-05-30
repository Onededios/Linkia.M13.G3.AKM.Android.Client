package com.linkiaM13G3.akmAndroidClient.Dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.linkiaM13G3.akmAndroidClient.Clients.PasswordClient
import com.linkiaM13G3.akmAndroidClient.Custom.CustomAppItem
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import com.linkiaM13G3.akmAndroidClient.Helpers.Updaters
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class DialogModifyCredential(
        private val context: Context,
        private val password: Password,
        private val appList: List<App>,
        private val tagList: List<Tag>?,
        private val api: PasswordClient
) : DialogFragment(), DialogSelectApp.OnAppPassedListener, DialogSelectTags.OnSelectTagsListener {
    private var passwordCopy = password.copy(password)
    private lateinit var aliasUpdate: EditText
    private lateinit var userUpdate: EditText
    private lateinit var passwordUpdate: EditText
    private lateinit var notesUpdate: TextInputEditText
    private lateinit var tagGroup: ChipGroup
    private lateinit var appGroup: ChipGroup
    private lateinit var buttonEditApp: Button
    private lateinit var buttonEditTags: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_credential_modify, null)
        aliasUpdate = contentView.findViewById(R.id.editTextAliasUpdate)
        userUpdate = contentView.findViewById(R.id.editTextUserUpdate)
        passwordUpdate = contentView.findViewById(R.id.editTextPasswordUpdate)
        notesUpdate = contentView.findViewById(R.id.noteTextInputEditTextUpdate)
        tagGroup = contentView.findViewById(R.id.chipGroupUpdateChips)
        appGroup = contentView.findViewById(R.id.chipGroupUpdateApp)
        buttonEditApp = contentView.findViewById(R.id.buttonChooseAppUpdate)
        buttonEditTags = contentView.findViewById(R.id.buttonEditChipsUpdate)

        aliasUpdate.setText(passwordCopy.alias)
        userUpdate.setText(passwordCopy.username)
        passwordUpdate.setText(passwordCopy.password)
        notesUpdate.setText(passwordCopy.description)

        updateTagChips()
        updateAppChips()

        buttonEditApp.setOnClickListener {
            val dialogApp = DialogSelectApp(context, appList)
            dialogApp.setOnDataPassedListener(this@DialogModifyCredential)
            dialogApp.show(childFragmentManager, "DialogSelectApp")
        }

        buttonEditTags.setOnClickListener {
            if (!tagList.isNullOrEmpty()) {
                val dialogTags = DialogSelectTags(context, tagList, passwordCopy.tags)
                dialogTags.setOnSelectTagsListener(this@DialogModifyCredential)
                dialogTags.show(childFragmentManager, "DialogSelectTags")
            }
        }


        return MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setTitle(R.string.credential_title_modify)
                .setView(contentView)
                .setPositiveButton("OK") { dialog, which ->
                    lifecycleScope.launch {
                        passwordCopy.alias = aliasUpdate.text.toString()
                        passwordCopy.username = userUpdate.text.toString()
                        passwordCopy.description = notesUpdate.text.toString()
                        api.updatePassword(passwordCopy)
                    }
                }
                .setNeutralButton("Delete") { dialog, which ->
                    lifecycleScope.launch {
                        api.deletePassword(password.id)
                    }
                }
                .setNegativeButton("Cancel") { dialog, which ->}
                .create()
    }

    private fun updateTagChips() {
        Updaters.updateChipGroup(tagGroup, passwordCopy.tags!!, context)
    }

    private fun updateAppChips() {
        appGroup.removeAllViews()
        if (passwordCopy.app != null) appGroup.addView(
                CustomAppItem(context).apply {
                    setAppName(passwordCopy.app?.name!!)
                    setAppIcon(passwordCopy.app?.icon!!)
                })
    }

    override fun onAppPassed(app: App?) {
        passwordCopy.app = app
        updateAppChips()
    }

    override fun onTagsSelected(tags: Array<Tag>?) {
        passwordCopy.tags = tags
        updateTagChips()
    }
}