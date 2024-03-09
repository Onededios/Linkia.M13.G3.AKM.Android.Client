package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.R

class PageSignIn : AppCompatActivity() {

    private lateinit var usernameOrEmailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_in)

        // Inicializa las vistas
        usernameOrEmailInputLayout = findViewById(R.id.editText)
        passwordInputLayout = findViewById(R.id.textInputLayout)

        val signInButton: Button = findViewById(R.id.buttonSignIn)
        val signUpButton: Button = findViewById(R.id.buttonSignUp)

        // Configura el listener para el botón de inicio de sesión
        signInButton.setOnClickListener {
            performSignIn()
        }

        // Configura el listener para el botón de registro
        signUpButton.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun performSignIn() {
        val usernameOrEmail = usernameOrEmailInputLayout.editText?.text.toString().trim()
        val password = passwordInputLayout.editText?.text.toString().trim()

        // Valida el nombre de usuario o correo y la contraseña
        val isUsernameOrEmailValid = validateUsernameOrEmail(usernameOrEmail, usernameOrEmailInputLayout)
        val isPasswordValid = validatePassword(password, passwordInputLayout)

        // Si ambos son válidos, procede con la lógica de inicio de sesión
        if (isUsernameOrEmailValid && isPasswordValid) {
            // Lógica de inicio de sesión
        }
    }

    private fun navigateToSignUp() {
        // Navegar a la actividad de registro
        val signUpIntent = Intent(this, PageSignUp::class.java)
        startActivity(signUpIntent)
    }

    private fun validateUsernameOrEmail(input: String, textInputLayout: TextInputLayout): Boolean {
        val isValidEmail = input.contains("@") && Patterns.EMAIL_ADDRESS.matcher(input).matches()
        val isValidUsername = input.matches(Regex("^[a-zA-Z0-9]{2,10}$"))

        if (!isValidEmail && !isValidUsername) {
            textInputLayout.error = "Enter a valid username or email address"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }

    private fun validatePassword(password: String, textInputLayout: TextInputLayout): Boolean {
        val hasUppercase = password.any { it.isUpperCase() }
        val hasNumber = password.any { it.isDigit() }
        if (password.length < 8) {
            textInputLayout.error = "Password must be at least 8 characters"
            return false
        } else if (!hasUppercase) {
            textInputLayout.error = "Password must have at least one uppercase letter"
            return false
        } else if (!hasNumber) {
            textInputLayout.error = "Password must have at least one number"
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }
}
