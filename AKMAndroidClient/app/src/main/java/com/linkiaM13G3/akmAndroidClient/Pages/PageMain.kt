package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.linkiaM13G3.akmAndroidClient.R

class PageMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_main)

        val buttonAddApp = findViewById<Button>(R.id.btn_addApp)
        val buttonSavedKeys=findViewById<Button>(R.id.btn_SavedKeys)
        val buttonCreateTag=findViewById<Button>(R.id.btn_addTag)

        buttonAddApp.setOnClickListener {
            val intent = Intent(this, PageAppsActivity::class.java)
            startActivity(intent) }

            buttonSavedKeys.setOnClickListener {
                val intentCredential = Intent(this, PageSavedCredentialListActivity::class.java)
                startActivity(intentCredential)
            }
        buttonCreateTag.setOnClickListener {
            val intentTag = Intent(this, PagePwdMain::class.java)
            startActivity(intentTag)
        }
    }
}