package com.linkiaM13G3.akmAndroidClient.Pages


import androidx.appcompat.app.AppCompatActivity

class PageApps : AppCompatActivity() {
    /*
    private var _api = AppClient()
    private var allApps: List<App>? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonBack: Button
    private lateinit var buttonAddManually: Button
    private fun initComponents() {
        recyclerView = findViewById(R.id.rvOptions)
        buttonBack = findViewById(R.id.btn_backArrowpsw)
        buttonAddManually = findViewById(R.id.btn_addManual)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_apps_list)
        initComponents()
        setupRecyclerView()
        setupButtons()
        setupSearchView()
    }

    private fun setupButtons() {
        buttonBack.setOnClickListener {
            startActivity(Intent(this, PageMain::class.java))
        }
        buttonAddManually.setOnClickListener {
            startActivity(Intent(this, PagePwdMain::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            allApps = fetchApps()
            updateRecyclerView(allApps)
        }
    }


    private fun updateRecyclerView(apps: List<App>?) {
        recyclerView.adapter = AdapterAppList(recyclerView.context, apps) { app ->
            run {
                (this as? DialogSelectApp.OnAppPassedListener)?.onAppPassed(app)
            }
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
                    updateRecyclerView(filterApps(newText))
                }
                return true
            }
        })
    }

    private fun filterApps(name: String): List<App> {
        val filteredApps = allApps?.filter { app ->
            val appName = app.name.lowercase()
            appName.contains(name.lowercase())
        }
        return filteredApps.orEmpty()
    }

    private suspend fun fetchApps(): List<App>? {
        return try {
            _api.fetchApps()
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
    */
}



