package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.Clients.UserClient
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignUpDTO
import com.linkiaM13G3.akmAndroidClient.Helpers.FieldCheck
import com.linkiaM13G3.akmAndroidClient.Helpers.Validators
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class PageSignUp : AppCompatActivity() {
    private var _api = UserClient()
    private lateinit var _btnReturn: Button
    private lateinit var _btnSignUp: Button
    private lateinit var _tilFName: TextInputLayout
    private lateinit var _tilLName: TextInputLayout
    private lateinit var _tilEmail: TextInputLayout
    private lateinit var _tilUname: TextInputLayout
    private lateinit var _tilCCode: TextInputLayout
    private lateinit var _actvCCode: MaterialAutoCompleteTextView
    private lateinit var _tilTele: TextInputLayout
    private lateinit var _tilPass: TextInputLayout
    private lateinit var _tilRPass: TextInputLayout
    private lateinit var _arrayCCodes: Array<String>
    private fun initializeView() {
        _btnReturn = findViewById(R.id.buttonReturnCreateNewUser)
        _tilFName = findViewById(R.id.textInputFName)
        _btnSignUp = findViewById(R.id.buttonSignUp)
        _tilLName = findViewById(R.id.textInputLName)
        _tilEmail = findViewById(R.id.textInputMail)
        _tilCCode = findViewById(R.id.textInputCountryCode)
        _actvCCode = findViewById(R.id.autoCompleteCountryCode)
        _tilTele = findViewById(R.id.textInputTelephone)
        _tilPass = findViewById(R.id.textInputPassword)
        _tilRPass = findViewById(R.id.textInputREPassword)
        _tilUname = findViewById(R.id.textInputUsername)
        _actvCCode.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, _arrayCCodes))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_up)
        _arrayCCodes = resources.getStringArray(R.array.country_codes)
        initializeView()

        _btnReturn.setOnClickListener {
            startActivity(Intent(this, PageSignIn::class.java))
        }

        _btnSignUp.setOnClickListener {
            val firstName = _tilFName.editText?.text.toString().trim()
            val lastName = _tilLName.editText?.text.toString().trim()
            val email = _tilEmail.editText?.text.toString().trim()
            val password = _tilPass.editText?.text.toString().trim()
            val username = _tilUname.editText?.text.toString().trim()
            var countryCode = _actvCCode.text.toString().trim()
            val telephone = _tilTele.editText?.text.toString().trim()
            val rePassword = _tilRPass.editText?.text.toString().trim()

            val fields = listOf(_tilFName, _tilLName, _tilEmail, _tilTele, _tilPass, _tilRPass, _tilCCode, _tilUname)

            val validations = listOf(
                Validators.validateName(firstName) to _tilFName,
                Validators.validateName(lastName) to _tilLName,
                Validators.validatePassword(password) to _tilPass,
                Validators.validateRePassword(password, rePassword) to _tilRPass,
                Validators.validateUsername(username) to _tilUname,
                Validators.validateMail(email) to _tilEmail,
                Validators.validateTelephone(telephone) to _tilTele,
                Validators.validateCountryCode(countryCode, _arrayCCodes) to _tilCCode
            )

            fields.forEach { it.error = null }

            var allValidationsPassed = true
            validations.forEach { (fieldCheck, textInputLayout) ->
                if (!fieldCheck.isValid) {
                    textInputLayout.error = fieldCheck.responseText
                    allValidationsPassed = false
                }
            }

            if (allValidationsPassed) {
                lifecycleScope.launch {
                    val usernameNotExists = !goCheckUsernameNotExists(username)
                    val mailNotExists = !goCheckMailNotExists(email)

                    val databaseValidations = listOf(
                        FieldCheck(
                            usernameNotExists,
                            if (usernameNotExists) null else "There is already an account with that username"
                        ) to _tilUname,
                        FieldCheck(
                            mailNotExists,
                            if (mailNotExists) null else "There is already an account with that mail"
                        ) to _tilEmail
                    )

                    databaseValidations.forEach { (fieldCheck, textInputLayout) ->
                        if (!fieldCheck.isValid) {
                            textInputLayout.error = fieldCheck.responseText
                            allValidationsPassed = false
                        }
                    }

                    if (allValidationsPassed) {
                        countryCode = countryCode.substringBefore(" (").trim()
                        goSignUp(UserSignUpDTO(firstName,lastName,email,username,password,telephone,countryCode))
                    }
                }
            }
            else showToast("There are fields with invalid inputs")
        }
    }

    private suspend fun goSignUp(dto: UserSignUpDTO) {
        try {
            val resp = _api.userSignUpAsync(dto)
            if (resp) {
                showToast("Sign Up successful")
                startActivity(Intent(this@PageSignUp, PageSignIn::class.java))
            }
            else showToast("Sign Up failed")
        } catch (e: Exception) {
            Log.e("SignUp", "Sign up error", e)
            showToast("Sign up error: ${e.localizedMessage}")
        }
    }

    private suspend fun goCheckUsernameNotExists(user: String): Boolean {
        return try {
            _api.checkUsername(user)
        } catch (e: Exception){
            Log.e("CheckUsername", "Username check error", e)
            showToast("There was an error checking the username: ${e.localizedMessage}")
            true
        }
    }

    private suspend fun goCheckMailNotExists(mail: String): Boolean {
        return try {
            _api.checkMail(mail)
        } catch (e: Exception){
            Log.e("CheckMail", "Mail check error", e)
            showToast("There was an error checking the mail: ${e.localizedMessage}")
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}