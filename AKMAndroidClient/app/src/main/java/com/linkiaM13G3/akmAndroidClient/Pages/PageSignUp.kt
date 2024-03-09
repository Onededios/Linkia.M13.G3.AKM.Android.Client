package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.R
class PageSignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_up)

        val buttonSignUp: Button = findViewById(R.id.buttonSignUp)
        val textInputFName: TextInputLayout = findViewById(R.id.textInputFName)
        val textInputLName: TextInputLayout = findViewById(R.id.textInputLName)
        val emailTextInputLayout: TextInputLayout = findViewById(R.id.textInputMail)
        val telephoneTextInputLayout: TextInputLayout = findViewById(R.id.textInputTelephone)
        val usernameTextInputLayout: TextInputLayout = findViewById(R.id.textInputUsername)
        val passwordTextInputLayout: TextInputLayout = findViewById(R.id.textInputPassword)
        val rePasswordTextInputLayout: TextInputLayout = findViewById(R.id.textInputREPassword)

        buttonSignUp.setOnClickListener {
            val fName = textInputFName.editText?.text.toString().trim()
            val lName = textInputLName.editText?.text.toString().trim()
            val email = emailTextInputLayout.editText?.text.toString().trim()
            val telephone = telephoneTextInputLayout.editText?.text.toString().trim()
            val usernameOrEmail = usernameTextInputLayout.editText?.text.toString().trim()
            val password = passwordTextInputLayout.editText?.text.toString().trim()
            val rePassword = rePasswordTextInputLayout.editText?.text.toString().trim()

            val isValidFName = validateName(fName, textInputFName)
            val isValidLName = validateName(lName, textInputLName)
            val isValidEmail = validateEmail(email, emailTextInputLayout)
            val isValidTelephone = validateTelephone(telephone, telephoneTextInputLayout)
            val isValidUsernameOrEmail = validateUsernameOrEmail(usernameOrEmail, usernameTextInputLayout)
            val isValidPassword = validatePassword(password, passwordTextInputLayout)
            val isPasswordMatch = validateRePassword(password, rePassword, rePasswordTextInputLayout)

            if (isValidFName && isValidLName && isValidEmail && isValidTelephone && isValidUsernameOrEmail && isValidPassword && isPasswordMatch) {
                val mainIntent = Intent(this, PageMain::class.java)
                startActivity(mainIntent)

                // Todo está bien, proceder con el registro
            }
        }
    }

    private fun validateName(name: String, textInputLayout: TextInputLayout): Boolean {
        textInputLayout.error = null // Limpia errores previos

        when {
            name.isEmpty() -> {
                textInputLayout.error = "Field cannot be empty"
                return false
            }

            name.length < 2 || name.length > 50 -> {
                textInputLayout.error = "Name must be between 2 and 50 characters"
                return false
            }

            !name.all { it.isLetter() || it == '-' || it == '\'' } -> {
                textInputLayout.error = "Name contains invalid characters"
                return false
            }

        }

        return true
    }

    private fun validateEmail(email: String, textInputLayout: TextInputLayout): Boolean {
        if (email.isEmpty()) {
            textInputLayout.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayout.error = "Enter a valid email address"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    private fun validateTelephone(telephone: String, textInputLayout: TextInputLayout): Boolean {
        if (telephone.isEmpty()) {
            textInputLayout.error = "Telephone number is required"
            return false
        } else if (!telephone.matches(Regex("^[+]?\\d{9,15}$"))) {
            textInputLayout.error = "Enter a valid telephone number"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    private fun validateUsernameOrEmail(input: String, textInputLayout: TextInputLayout): Boolean {
        val isValidEmail = input.contains("@") && Patterns.EMAIL_ADDRESS.matcher(input).matches()
        val isValidUsername = input.matches(Regex("^[a-zA-Z]{2,10}$"))

        if (!isValidEmail && !isValidUsername) {
            textInputLayout.error = "Enter a valid username or email address"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    private fun validatePassword(password: String, textInputLayout: TextInputLayout): Boolean {
        if (password.length < 8) {
            textInputLayout.error = "Password must be at least 8 characters"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    private fun validateRePassword(password: String, rePassword: String, textInputLayout: TextInputLayout): Boolean {
        if (password != rePassword) {
            textInputLayout.error = "Passwords do not match"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }
}