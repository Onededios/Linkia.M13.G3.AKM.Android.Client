package com.linkiaM13G3.akmAndroidClient.Dialogs

import AdapterAppList
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.R

class DialogSelectApp(private val context: Context, private val appList: List<App?>?) : DialogFragment() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private var onDataPassedListener: OnAppPassedListener? = null

    interface OnAppPassedListener  {
        fun onAppPassed(app: App?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_app_list, container, false)
        val adapter = AdapterAppList(context, appList) { app ->
            run {
                onDataPassedListener?.onAppPassed(app)
            }
            dismiss()
        }

        searchView = view.findViewById(R.id.srchView_apps)
        recyclerView = view.findViewById(R.id.rvAppList)

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

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    fun setOnDataPassedListener(listener: OnAppPassedListener) {
        onDataPassedListener = listener
    }

    private fun updateRecyclerView(apps: List<App?>?) {
        recyclerView.adapter = AdapterAppList(context, apps) { app ->
            run {
                onDataPassedListener?.onAppPassed(app)
            }
            dismiss()
        }
    }
    private fun filterApps(name: String?): List<App?>? {
        if (appList.isNullOrEmpty()) return null
        return appList.filter { app ->
            val appName = app?.name?.lowercase()
            name?.let {
                appName?.contains(it.lowercase()) ?: false
            } ?: false
        }
    }

}
