package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.linkiaM13G3.akmAndroidClient.R

class PagePwdMain : AppCompatActivity(){

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_password)

        val btnSave = findViewById<Button>(R.id.button)
        val btnBack= findViewById<Button>(R.id.btn_backArrowpsw)

        btnBack.setOnClickListener {
            val intentTag = Intent(this, PageAppsActivity::class.java)
            startActivity(intentTag)
        }

        btnSave.setOnClickListener {
            val name = findViewById<TextInputEditText>(R.id.eddName).text.toString()
            val emailOrUsername = findViewById<TextInputEditText>(R.id.edUserId).text.toString()
            val password = findViewById<TextInputEditText>(R.id.edUserPassword).text.toString()

            val newCredential = Credential(name, emailOrUsername, password)
            CredentialManager.credentials.add(newCredential)


            Toast.makeText(this, "Credentials saved", Toast.LENGTH_SHORT).show()

            finish()
        }




    }
}