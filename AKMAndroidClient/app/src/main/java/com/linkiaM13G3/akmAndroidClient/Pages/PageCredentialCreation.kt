package com.linkiaM13G3.akmAndroidClient.Pages

import AppClient
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.Clients.PasswordClient
import com.linkiaM13G3.akmAndroidClient.Clients.TagClient
import com.linkiaM13G3.akmAndroidClient.Custom.CustomAppItem
import com.linkiaM13G3.akmAndroidClient.DTOs.PasswordCreationDTO
import com.linkiaM13G3.akmAndroidClient.Dialogs.DialogSelectApp
import com.linkiaM13G3.akmAndroidClient.Dialogs.DialogSelectTags
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import com.linkiaM13G3.akmAndroidClient.Helpers.Updaters
import com.linkiaM13G3.akmAndroidClient.Helpers.Validators
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class PageCredentialCreation : AppCompatActivity(), DialogSelectApp.OnAppPassedListener, DialogSelectTags.OnSelectTagsListener {
    private val noApp = listOf(App(UUID.randomUUID(), "None", "None"))
    private var _allApps: List<App> = noApp
    private var _allTags: List<Tag>? = null
    private var _apiTags = TagClient()
    private var _apiApp = AppClient()
    private var _apiPassword = PasswordClient()
    private lateinit var newPass: PasswordCreationDTO
    private lateinit var _buttonReturn: Button
    private lateinit var _buttonSaveNewCredential: Button
    private lateinit var _buttonChooseApp: Button
    private lateinit var _buttonAddTag: Button
    private lateinit var _tilNote: TextInputLayout
    private lateinit var _tilPassword: TextInputLayout
    private lateinit var _tilUser: TextInputLayout
    private lateinit var _tilAlias: TextInputLayout
    private lateinit var tagGroup: ChipGroup
    private lateinit var appGroup: ChipGroup
    private lateinit var passwordHandler: Password
    private var selectedAppId : UUID? = null
    private var selectedTags : List<UUID>? = null
    private lateinit var context: Context
    private fun initView() {
        tagGroup = findViewById(R.id.chipGroupCreateChips)
        appGroup = findViewById(R.id.chipGroupCreateApp)
        _buttonReturn = findViewById(R.id.buttonReturn)
        _buttonChooseApp = findViewById(R.id.buttonChooseAppCreate)
        _buttonSaveNewCredential = findViewById(R.id.buttonSaveNewCredential)
        _buttonAddTag = findViewById(R.id.buttonEditChipsCreate)
        _tilNote = findViewById(R.id.noteTextInputLayout)
        _tilPassword = findViewById(R.id.passwordInputLayout)
        _tilUser = findViewById(R.id.userTextInputLayout)
        _tilAlias = findViewById(R.id.aliasTextInputLayout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_credential_creation)
        context = this

        passwordHandler = Password(
                id = UUID.randomUUID(),
                password = "",
                date_expiration = ""
        )

        initView()
        newPass = PasswordCreationDTO(
                userId = UserSingleton.id,
                password = ""
        )

        lifecycleScope.launch {
            val fetchedApps = fetchApps()
            _allApps = if (fetchedApps != null) {
                noApp + fetchedApps
            } else {
                noApp
            }

            Toast.makeText(baseContext, "Retreived ${_allApps?.size} apps", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            _allTags = fetchTags()
            Toast.makeText(baseContext, "Retreived ${_allTags?.size} tags", Toast.LENGTH_SHORT).show()
        }

        _buttonReturn.setOnClickListener {
            startActivity(Intent(this, PageCredentials::class.java))
        }

        _buttonChooseApp.setOnClickListener {
            if (_allApps.isNotEmpty()) {
                val dialogApp = DialogSelectApp(context, listOf(passwordHandler.app))
                dialogApp.setOnDataPassedListener(this@PageCredentialCreation)
                dialogApp.show(supportFragmentManager, "DialogSelectApp")
            }
        }

        _buttonAddTag.setOnClickListener {
            if (!_allTags.isNullOrEmpty()) {
                val dialogTags = DialogSelectTags(context, _allTags!!.toList(), passwordHandler.tags)
                dialogTags.setOnSelectTagsListener(this@PageCredentialCreation)
                dialogTags.show(supportFragmentManager, "DialogSelectTags")
            }
        }

        _buttonSaveNewCredential.setOnClickListener {
            val alias = _tilAlias.editText?.text.toString().trim()
            val user = _tilUser.editText?.text.toString().trim()
            val password = _tilPassword.editText?.text.toString().trim()
            val notes = _tilNote.editText?.text.toString()

            val fields = mutableListOf(_tilAlias, _tilPassword)
            val validations = mutableListOf(
                Validators.validatePassword(password) to _tilPassword,
                Validators.validateUsername(alias) to _tilAlias,
            )
            if (user.isNotEmpty()) {
                fields.add(_tilUser)
                validations.add(Validators.validateUsername(user) to _tilUser)
            }
            if (notes.isNotEmpty()) {
                fields.add(_tilNote)
            }

            fields.forEach { it.error = null }

            var allValidationsPassed = true
            validations.forEach { (fieldCheck, textInputLayout) ->
                if (!fieldCheck.isValid) {
                    textInputLayout.error = fieldCheck.responseText
                    allValidationsPassed = false
                }
            }
            if (allValidationsPassed) {

                newPass.alias = alias
                newPass.username = user
                newPass.password = password
                newPass.description = notes
                newPass.appId = selectedAppId
                newPass.tags = selectedTags

                lifecycleScope.launch {
                    _apiPassword.createPassword(newPass)
                    delay(3500) // Wait until credential is created
                }

                startActivity(Intent(this, PageCredentials::class.java))
            }
            else showToast("There are fields with invalid inputs")
        }
    }

    private suspend fun fetchApps(): List<App>? {
        return try {
            _apiApp.fetchApps()
        } catch (e: Exception) {
            Log.e("PageCredentialCreation", e.message.toString())
            null
        }
    }

    private suspend fun fetchTags(): List<Tag>? {
        return try {
            _apiTags.getTags()
        } catch (e: Exception) {
            Log.e("PageCredentialCreation", e.message.toString())
            null
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateAppChips() {
        appGroup.removeAllViews()
        if (passwordHandler.app != null) appGroup.addView(
                CustomAppItem(context).apply {
                    setAppName(passwordHandler.app?.name!!)
                    setAppIcon(passwordHandler.app?.icon!!)
                })
    }

    private fun updateTagChips() {
        Updaters.updateChipGroup(tagGroup, passwordHandler.tags!!, context)
    }

    override fun onAppPassed(app: App?) {
        passwordHandler.app = app
        updateAppChips()
    }

    override fun onTagsSelected(tags: Array<Tag>?) {
        passwordHandler.tags = tags
        updateTagChips()
    }
}

