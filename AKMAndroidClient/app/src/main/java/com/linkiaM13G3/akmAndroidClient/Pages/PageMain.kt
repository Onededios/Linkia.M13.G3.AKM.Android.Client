package com.linkiaM13G3.akmAndroidClient.Pages

import AppClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class PageMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_main)

        val buttonAddApp = findViewById<Button>(R.id.btn_addApp)
        val buttonSavedKeys=findViewById<Button>(R.id.btn_SavedKeys)

        buttonAddApp.setOnClickListener {
            val intent = Intent(this, PageAppsActivity::class.java)
            startActivity(intent) }

            buttonSavedKeys.setOnClickListener {
                val intentCredential = Intent(this, PageSavedCredentialListActivity::class.java)
                startActivity(intentCredential)
            }
    }

    private suspend fun fetchApps() {
        val appClient = AppClient()
        val apps = withContext(Dispatchers.IO) {appClient.fetchApps()}
        Log.d("Test", apps.toString())
    }
}