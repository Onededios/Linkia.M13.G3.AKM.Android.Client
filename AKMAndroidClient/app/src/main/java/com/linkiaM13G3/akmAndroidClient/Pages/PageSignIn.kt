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
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import com.linkiaM13G3.akmAndroidClient.Helpers.Validators
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class PageSignIn : AppCompatActivity() {
    private var _api = UserClient()
    private lateinit var _btnSignUp: Button
    private lateinit var _btnSignIn: Button
    private lateinit var _user: TextInputLayout
    private lateinit var _pass: TextInputLayout

    private fun initializeView() {
        _btnSignUp = findViewById(R.id.buttonSignUp)
        _btnSignIn = findViewById(R.id.buttonSignIn)
        _user = findViewById(R.id.textInputLayoutUsername)
        _pass = findViewById(R.id.textInputLayoutPassword)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_in)
        initializeView()

        _btnSignIn.setOnClickListener {
            val user = _user.editText?.text.toString().trim()
            val password = _pass.editText?.text.toString().trim()
            if (Validators.validateUser(user)) {
                lifecycleScope.launch {
                    goSignIn(UserSignInDTO(username = user, password = password))
                }
            } else showToast("Your login is not valid")
        }

        _btnSignUp.setOnClickListener {
            val intent = Intent(this, PageSignUp::class.java)
            startActivity(intent)
        }
    }

    private suspend fun goSignIn(dto: UserSignInDTO) {
        try {
            val user = _api.userSignInAsync(dto)
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