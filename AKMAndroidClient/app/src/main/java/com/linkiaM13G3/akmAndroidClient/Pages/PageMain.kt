package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Context
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
        val buttonSavedKeys = findViewById<Button>(R.id.btn_SavedKeys)
        val buttonExit = findViewById<Button>(R.id.exit)

        buttonAddApp.setOnClickListener {
            val intent = Intent(this, PageApps::class.java)
            startActivity(intent)
        }

        buttonSavedKeys.setOnClickListener {
            val intentCredential = Intent(this, PageSavedCredentialListActivity::class.java)
            startActivity(intentCredential)
        }

        buttonExit.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {

        val sharedPreferences = getSharedPreferences("miApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("userId")
        editor.apply()


        val intent = Intent(this, PageSignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}