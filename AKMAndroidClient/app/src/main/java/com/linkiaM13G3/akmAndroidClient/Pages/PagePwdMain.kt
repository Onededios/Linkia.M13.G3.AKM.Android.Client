package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.credentials.CredentialManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.linkiaM13G3.akmAndroidClient.R

class PagePwdMain : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_password)

        databaseHelper = DatabaseHelper(this)

        val btnSave = findViewById<Button>(R.id.button)
        val btnBack = findViewById<Button>(R.id.btn_backArrowpsw)

        btnBack.setOnClickListener {
            val intentTag = Intent(this, PageAppsActivity::class.java)
            startActivity(intentTag)
        }

        btnSave.setOnClickListener {
            val name = findViewById<TextInputEditText>(R.id.eddName).text.toString().trim()
            val emailOrUsername = findViewById<TextInputEditText>(R.id.edUserId).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.edUserPassword).text.toString().trim()
            val notes = findViewById<TextInputEditText>(R.id.notes).text.toString().trim()

            if (name.isNotEmpty() && emailOrUsername.isNotEmpty() && password.isNotEmpty()) {

                val result = databaseHelper.insertCredential(name, emailOrUsername, password, notes)
                if (result != -1L) {
                    Toast.makeText(this, "Credentials saved", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error saving credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
