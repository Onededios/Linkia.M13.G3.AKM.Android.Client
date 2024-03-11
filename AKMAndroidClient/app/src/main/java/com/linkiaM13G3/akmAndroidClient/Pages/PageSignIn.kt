package com.linkiaM13G3.akmAndroidClient.Pages



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.R

class PageSignIn : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_in)
        databaseHelper = DatabaseHelper.getInstance(this)

        val btnForgotPwd = findViewById<Button>(R.id.buttonSignUp)
        val btnLogin = findViewById<Button>(R.id.buttonSignIn)

        // Asegúrate de que estos ID sean correctos según tu layout page_sign_in.xml
        val usernameOrEmailInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutUsername)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout) // Actualizado para reflejar un nombre de ID más descriptivo

        btnLogin.setOnClickListener {
            val emailOrUsername = usernameOrEmailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            val userId = databaseHelper.getUserIdForLogin(emailOrUsername, password)
            if (userId != -1) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                // Almacenar el userId en las preferencias compartidas
                val sharedPreferences = getSharedPreferences("miApp", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("userId", userId)
                editor.apply()

                // Navegar a la actividad principal
                startActivity(Intent(this, PageMain::class.java))
            } else {
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
