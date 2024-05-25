package com.linkiaM13G3.akmAndroidClient.Pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.linkiaM13G3.akmAndroidClient.R



class PagePwdMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_credential_creation)

        /*
        val btnSave = findViewById<Button>(R.id.button)
        val btnBack = findViewById<Button>(R.id.btn_backArrowpsw)
        val sharedPreferences = getSharedPreferences("miApp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1)

        btnBack.setOnClickListener {
            startActivity(Intent(this, PageApps::class.java))
        }

        btnSave.setOnClickListener {
            val name = findViewById<TextInputEditText>(R.id.eddName).text.toString().trim()
            val usernameOrEmail = findViewById<TextInputEditText>(R.id.edUserId).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.edUserPassword).text.toString().trim()

            if (name.isNotEmpty() && usernameOrEmail.isNotEmpty() && password.isNotEmpty()) {

                //val result = databaseHelper.insertApp(userId, name, usernameOrEmail, password)
                val result = null
                if (result != -1L) {
                    Toast.makeText(this, "App saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error saving the app", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show()
            }
        }
                    */
    }
}