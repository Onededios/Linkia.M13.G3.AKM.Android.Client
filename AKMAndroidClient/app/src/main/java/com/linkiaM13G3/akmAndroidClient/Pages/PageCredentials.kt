package com.linkiaM13G3.akmAndroidClient.Pages;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView;
import com.linkiaM13G3.akmAndroidClient.Adapters.AdapterCredentialList
import com.linkiaM13G3.akmAndroidClient.Clients.PasswordClient
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.User
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch
import java.util.UUID

class PageCredentials: AppCompatActivity() {
    private var _api = PasswordClient()
    private var credentials: List<Password>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonBack: Button
    private fun initComponents() {
        recyclerView = findViewById(R.id.credential_list)
        buttonBack = findViewById(R.id.btnReturnFromCredentialList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delete this
        val user = User()
        user.id = UUID.fromString("bda7a2b2-152a-4eb8-af92-54b82e9d8ec3")
        UserSingleton.initializeWithUser(user)
        // Delete this

        setContentView(R.layout.page_credential_list)
        initComponents()
        setupRecyclerView()
        setupButtons()
    }

    private fun setupButtons() {
        buttonBack.setOnClickListener {
            startActivity(Intent(this, PageMain::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            credentials = _api.fetchUserPasswords()
            updateRecyclerView(credentials)
        }
    }

    private fun updateRecyclerView(passwords: List<Password>?) {
        recyclerView.adapter = AdapterCredentialList(recyclerView.context, passwords)
    }
}
