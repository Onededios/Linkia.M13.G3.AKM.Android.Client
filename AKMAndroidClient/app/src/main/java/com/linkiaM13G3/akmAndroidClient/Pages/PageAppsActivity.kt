package com.linkiaM13G3.akmAndroidClient.Pages


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.launch

class PageAppsActivity : AppCompatActivity(), AdapterAppList.OnAppClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterAppList
    private var connection = Connection().appService

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
            val newAppsList = connection.getApps()
            val appsList = getAppsList()
            adapter = AdapterAppList(appsList, this@PageAppsActivity)
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
                    adapter.filter(it)
                }
                return true
            }
        })
    }



    private fun getAppsList(): List<AdapterAppList.AppsList> {
        return listOf(
            AdapterAppList.AppsList("Google", R.drawable.logo_google,""),
            AdapterAppList.AppsList("Facebook", R.drawable.logo_facebook,""),
            AdapterAppList.AppsList("Amazon", R.drawable.logo_amazon,""),
            AdapterAppList.AppsList("Apple", R.drawable.logo_apple,""),
            AdapterAppList.AppsList("Netflix", R.drawable.logo_netflix,""),
            AdapterAppList.AppsList("Airbnb", R.drawable.logo_airbnb,""),
            AdapterAppList.AppsList("Uber", R.drawable.logo_uber,""),
            AdapterAppList.AppsList("Spotify", R.drawable.logo_spotify,""),
            AdapterAppList.AppsList("Samsung", R.drawable.logo_samsung,""),
            AdapterAppList.AppsList("Huawei", R.drawable.logo_huawei,""),
            AdapterAppList.AppsList("PlayStation", R.drawable.logo_sony,""),
            AdapterAppList.AppsList("Xbox", R.drawable.logo_xbox,""),
            AdapterAppList.AppsList("TikTok", R.drawable.logo_tiktok,""),
            AdapterAppList.AppsList("YouTube", R.drawable.logo_youtube,""),
            AdapterAppList.AppsList("Instagram", R.drawable.logo_instagram,"")
        )
    }

    override fun onAppClick(appName: String, appId: String) {
        val intent = Intent(this, PagePwdMain::class.java).apply {
            putExtra("APP_NAME_EXTRA", appName)
        }
        startActivity(intent)
    }
}



