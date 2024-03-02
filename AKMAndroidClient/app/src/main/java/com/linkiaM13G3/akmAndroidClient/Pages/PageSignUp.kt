package com.linkiaM13G3.akmAndroidClient.Pages

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.linkiaM13G3.akmAndroidClient.R

class PageSignUp : AppCompatActivity() {
    private lateinit var spinnerCountryCode : Spinner

    private fun initializeComponents() {
        spinnerCountryCode = findViewById(R.id.spinnerCountryCode)
        spinnerCountryCode.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.country_codes)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_sign_up)
        initializeComponents()
    }
}