package com.linkiaM13G3.akmAndroidClient.Pages;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.linkiaM13G3.akmAndroidClient.Clients.TagClient
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import com.linkiaM13G3.akmAndroidClient.Helpers.Validators
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PageTagCreation : AppCompatActivity() {
    private var _api = TagClient()
    private lateinit var buttonCreate: Button
    private lateinit var buttonReturn: Button
    private lateinit var tilTag: TextInputLayout

    private fun initView() {
        buttonCreate = findViewById(R.id.buttonCreateNewTag)
        buttonReturn = findViewById(R.id.buttonReturn)
        tilTag = findViewById(R.id.textInputLayoutTag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_tag_creation)

        buttonReturn.setOnClickListener {
            startActivity(Intent(this, PageCredentials::class.java))
        }

        buttonCreate.setOnClickListener {
            val tagname = tilTag.editText?.text.toString().trim()

            val fields = mutableListOf(tilTag)

            val validations = mutableListOf(Validators.validateName(tagname) to tilTag)

            var allValidationsPassed = true
            validations.forEach { (fieldCheck, textInputLayout) ->
                if (!fieldCheck.isValid) {
                    textInputLayout.error = fieldCheck.responseText
                    allValidationsPassed = false
                }
            }
            if (allValidationsPassed) {
                lifecycleScope.launch {
                    _api.createTag(Tag(tagname, UserSingleton.id))
                    delay(2500)
                }
            }
        }
    }
}
