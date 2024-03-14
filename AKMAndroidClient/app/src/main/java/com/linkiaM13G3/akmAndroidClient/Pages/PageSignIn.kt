package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.Clients.UserClient
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignInDTO
import com.linkiaM13G3.akmAndroidClient.Entities.User
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import com.linkiaM13G3.akmAndroidClient.Helpers.Validators
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class PageSignIn : AppCompatActivity() {
    private var api = UserClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_in)

        val btnForgotPwd = findViewById<Button>(R.id.buttonSignUp)
        val btnLogin = findViewById<Button>(R.id.buttonSignIn)

        val usernameOrEmailInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutUsername)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)

        btnLogin.setOnClickListener {
            val emailOrUsername = usernameOrEmailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()
            if (Validators.validateUser(emailOrUsername)) {
                lifecycleScope.launch {
                    goSignIn(UserSignInDTO(username = emailOrUsername, password = password))
                }
            } else showToast("Your login is not valid")
        }

        btnForgotPwd.setOnClickListener {

            val intent = Intent(this, PageSignUp::class.java)
            startActivity(intent)
        }
    }

    private suspend fun goSignIn(dto: UserSignInDTO) {
        try {
            val user = api.userSignInAsync(dto)
            user?.let {
                UserSingleton.initializeWithUser(it)
                startActivity(Intent(this@PageSignIn, PageMain::class.java))
                showToast("Login successful")
            } ?: showToast("Invalid username or password")
        } catch (e: Exception) {
            Log.e("SignIn", "Sign in error", e)
            showToast("Sign in error: ${e.localizedMessage}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}