package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Context
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

    private lateinit var databaseHelper: DatabaseHelper // Referencia a tu ayudante de base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_password)

        databaseHelper = DatabaseHelper.getInstance(this)

        val btnSave = findViewById<Button>(R.id.button)
        val btnBack = findViewById<Button>(R.id.btn_backArrowpsw)
        val sharedPreferences = getSharedPreferences("miApp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1) // Asume -1 como valor predeterminado si no se encuentra

        btnBack.setOnClickListener {
            startActivity(Intent(this, PageAppsActivity::class.java))
        }

        btnSave.setOnClickListener {
            val name = findViewById<TextInputEditText>(R.id.eddName).text.toString().trim()
            val usernameOrEmail = findViewById<TextInputEditText>(R.id.edUserId).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.edUserPassword).text.toString().trim()

            if (name.isNotEmpty() && usernameOrEmail.isNotEmpty() && password.isNotEmpty()) {
                // Asegúrate de tener el userId correcto en este punto
                val result = databaseHelper.insertApp(userId, name, usernameOrEmail, password)
                if (result != -1L) {
                    Toast.makeText(this, "App saved successfully", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad y regresa a la anterior
                } else {
                    Toast.makeText(this, "Error saving the app", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
