package com.linkiaM13G3.akmAndroidClient.Pages



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.R

class PageSignIn : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_in)
        dbHelper = DatabaseHelper(this)

        // Botón para ir a la página de registro
        val btnForgotPwd = findViewById<Button>(R.id.buttonSignUp)

        // Botón de inicio de sesión
        val btnLogin = findViewById<Button>(R.id.buttonSignIn)

        // Referencias a los TextInputLayout usando los IDs correctos
        val usernameOrEmailInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutUsername) // Asegúrate de que este ID esté actualizado en el XML
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout) // Este ID ya está correcto

        btnLogin.setOnClickListener {
            // Extrae el texto de los TextInputEditText que están dentro de los TextInputLayout
            val emailOrUsername = usernameOrEmailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            // Lógica para verificar las credenciales de inicio de sesión
            if (dbHelper.userCanLogIn(emailOrUsername, password)) {
                // Credenciales correctas, navegar a la página principal
                val intent = Intent(this, PageMain::class.java)
                startActivity(intent)
            } else {
                // Credenciales incorrectas, mostrar error
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        btnForgotPwd.setOnClickListener {
            // Navegar a la actividad de registro
            val intent = Intent(this, PageSignUp::class.java)
            startActivity(intent)
        }
    }
}
/*
    private fun validateEmailOrUsername(input: String, inputLayout: TextInputLayout): Boolean {
        return if (input.isEmpty()) {
            inputLayout.error = "Field cannot be empty"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches() && !input.matches(Regex("^[a-zA-Z0-9._-]{3,}$"))) {
            inputLayout.error = "Enter a valid email or username"
            false
        } else {
            inputLayout.error = null
            true
        }
    }

    private fun validatePassword(password: String, inputLayout: TextInputLayout): Boolean {
        return if (password.length < 6) {
            inputLayout.error = "Password must be at least 6 characters"
            false
        } else {
            inputLayout.error = null
            true
        }
    }
    */
