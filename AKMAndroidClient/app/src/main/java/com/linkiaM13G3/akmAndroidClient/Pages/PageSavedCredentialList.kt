package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.Pages.PagePwdDDBB.PreferencesUtils.getAllCredentials
import com.linkiaM13G3.akmAndroidClient.R


class PageSavedCredentialListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_apps_list)

        val recyclerView: RecyclerView = findViewById(R.id.rvOptions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterSavedList(getSavedCredentials(this))
    }
    fun getSavedCredentials(context: Context): List<SavedCredential> {
        // Obtiene las credenciales guardadas, este código depende de cómo guardes los datos
        val sharedPreferences = context.getSharedPreferences("MySecurePrefs", Context.MODE_PRIVATE)
        val allEntries = sharedPreferences.all
        val savedCredentialsList = mutableListOf<SavedCredential>()

        allEntries.forEach { entry ->
            // Aquí asumimos que tienes un formato específico para las claves y los valores en SharedPreferences
            if (entry.key.contains("NAME_KEY") && entry.value is String) {
                val appName = entry.value as String
                val userNameKey = entry.key.replace("NAME_KEY", "EMAIL_OR_USERNAME_KEY")
                val userName = sharedPreferences.getString(userNameKey, "") ?: ""
                savedCredentialsList.add(SavedCredential(icon = androidx.appcompat.R.drawable.abc_cab_background_top_mtrl_alpha, userName = userName, appName = appName))
            }
        }
        return savedCredentialsList
    }
}
