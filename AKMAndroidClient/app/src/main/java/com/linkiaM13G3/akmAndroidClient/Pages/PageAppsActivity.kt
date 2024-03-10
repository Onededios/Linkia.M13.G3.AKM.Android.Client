package com.linkiaM13G3.akmAndroidClient.Pages


import AppClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class PageAppsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterAppList
    private var api = AppClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_apps_list)
        setupButtons()
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupButtons() {
        val buttonBack = findViewById<Button>(R.id.btn_backArrowpsw)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, PageMain::class.java))
        }

        val buttonAddManually = findViewById<Button>(R.id.btn_addManual)
        buttonAddManually.setOnClickListener {
            startActivity(Intent(this, PagePwdMain::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvOptions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            adapter = AdapterAppList(fetchApps(), this@PageAppsActivity)
            recyclerView.adapter = adapter
        }
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.srchView_apps)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter?.filter(it)
                }
                return true
            }
        })
    }

    private suspend fun fetchApps(): List<App>? {
        return try {
            api.fetchApps()
        } catch (e: Exception) {
            Log.e("AppsPage", e.message.toString())
            null
        }
    }
    fun onAppClick(appName: String?) {
        val intent = Intent(this, PagePwdMain::class.java).apply {
            putExtra("APP_NAME_EXTRA", appName)
        }
        startActivity(intent)
    }
}



