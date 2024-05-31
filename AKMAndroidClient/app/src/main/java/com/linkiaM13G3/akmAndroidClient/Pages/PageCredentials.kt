package com.linkiaM13G3.akmAndroidClient.Pages;

import AppClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.Adapters.AdapterCredentialList
import com.linkiaM13G3.akmAndroidClient.Clients.PasswordClient
import com.linkiaM13G3.akmAndroidClient.Clients.TagClient
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch
import java.util.UUID

class PageCredentials: AppCompatActivity() {
    private val noApp = listOf(App(UUID.randomUUID(), "None", "None"))
    private var _allApps: List<App> = noApp
    private var _allTags: List<Tag>? = null
    private var _apiTags = TagClient()
    private var _apiApp = AppClient()
    private var _apiPassword = PasswordClient()
    private var credentials: List<Password>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonBack: Button
    private lateinit var buttonCreatePass: Button
    private lateinit var buttonCreateTag: Button
    private fun initComponents() {
        recyclerView = findViewById(R.id.credential_list)
        buttonBack = findViewById(R.id.btnReturnFromCredentialList)
        buttonCreatePass = findViewById(R.id.buttonCreateNewCredential)
        buttonCreateTag = findViewById(R.id.buttonCreateNewTag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.page_credential_list)
        initComponents()
        setupRecyclerView()
        setupButtons()
    }

    private fun setupButtons() {
        buttonBack.setOnClickListener {
            startActivity(Intent(this, PageSignIn::class.java))
        }
        buttonCreatePass.setOnClickListener {
            startActivity(Intent(this, PageCredentialCreation::class.java))
        }
        buttonCreateTag.setOnClickListener {
            startActivity(Intent(this, PageTagCreation::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val fetchedApps = fetchApps()
            _allApps = if (fetchedApps != null) {
                noApp + fetchedApps
            } else {
                noApp
            }
            Toast.makeText(baseContext, "Retreived ${_allApps?.size} apps", Toast.LENGTH_SHORT).show()

            _allTags = fetchTags()
            Toast.makeText(baseContext, "Retreived ${_allTags?.size} tags", Toast.LENGTH_SHORT).show()

            credentials = _apiPassword.fetchUserPasswords()
            Toast.makeText(baseContext, "Retreived ${credentials?.size} credentials", Toast.LENGTH_SHORT).show()
            updateRecyclerView(credentials)
        }
    }

    private fun updateRecyclerView(passwords: List<Password>?) {
        recyclerView.adapter = AdapterCredentialList(recyclerView.context, passwords, _allApps, _allTags, _apiPassword)
    }

    private suspend fun fetchApps(): List<App>? {
        return try {
            _apiApp.fetchApps()
        } catch (e: Exception) {
            Log.e("PageCredentials", e.message.toString())
            null
        }
    }

    private suspend fun fetchTags(): List<Tag>? {
        return try {
            _apiTags.getTags()
        } catch (e: Exception) {
            Log.e("PageCredentials", e.message.toString())
            null
        }
    }
}
