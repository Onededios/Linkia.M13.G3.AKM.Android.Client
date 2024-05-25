package com.linkiaM13G3.akmAndroidClient.Pages

import AppClient
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.Dialogs.DialogSelectApp
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Helpers.Validators
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class PageCredentialCreation : AppCompatActivity() {
    private val noApp = listOf(App("None", "None"))
    private lateinit var _buttonReturn: Button
    private lateinit var _buttonSaveNewCredential: Button
    private lateinit var _buttonChooseApp: Button
    private lateinit var _buttonAddTag: Button
    private lateinit var _tilNote: TextInputLayout
    private lateinit var _tilPassword: TextInputLayout
    private lateinit var _tilUser: TextInputLayout
    private lateinit var _tilAlias: TextInputLayout
    private var _api = AppClient()
    private var _allApps: List<App> = noApp

    private fun initView() {
        _buttonReturn = findViewById(R.id.buttonReturn)
        _buttonChooseApp = findViewById(R.id.buttonChooseApp)
        _buttonSaveNewCredential = findViewById(R.id.buttonSaveNewCredential)
        _buttonAddTag = findViewById(R.id.buttonAddTag)
        _tilNote = findViewById(R.id.noteTextInputLayout)
        _tilPassword = findViewById(R.id.passwordInputLayout)
        _tilUser = findViewById(R.id.userTextInputLayout)
        _tilAlias = findViewById(R.id.aliasTextInputLayout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_credential_creation)

        initView()

        lifecycleScope.launch {
            val fetchedApps = fetchApps()
            _allApps = if (fetchedApps != null) {
                noApp + fetchedApps
            } else {
                noApp
            }
        }

        _buttonChooseApp.setOnClickListener {
            DialogSelectApp(this, _allApps).show(supportFragmentManager, "DialogSelectApp")
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
                lifecycleScope.launch {}
            }
            else showToast("There are fields with invalid inputs")
        }
    }

    private suspend fun fetchApps(): List<App>? {
        return try {
            _api.fetchApps()
        } catch (e: Exception) {
            Log.e("AppsPage", e.message.toString())
            null
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

