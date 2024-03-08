package com.linkiaM13G3.akmAndroidClient.Pages

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        btnSave.setOnClickListener {
            val name = findViewById<TextInputEditText>(R.id.eddName).text.toString()
            val emailOrUsername = findViewById<TextInputEditText>(R.id.edUserId).text.toString()
            val password = findViewById<TextInputEditText>(R.id.edUserPassword).text.toString()
            val notes = findViewById<EditText>(R.id.notes).text.toString()

            val pagePwdDDBB = PagePwdDDBB(name, emailOrUsername, password, notes)
            PagePwdDDBB.PreferencesUtils.saveCredentials(this, pagePwdDDBB)


        val recyclerView = findViewById<RecyclerView>(R.id.rvOptions)
        recyclerView.layoutManager = LinearLayoutManager(this)

            val appName = intent.getStringExtra("APP_NAME_EXTRA")


    }
}}