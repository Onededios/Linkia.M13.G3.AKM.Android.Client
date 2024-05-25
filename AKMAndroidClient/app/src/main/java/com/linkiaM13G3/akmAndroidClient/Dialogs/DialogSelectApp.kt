package com.linkiaM13G3.akmAndroidClient.Dialogs

import AdapterAppList
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.R
import androidx.appcompat.widget.SearchView

class DialogSelectApp(private val context: Context, private val appList: List<App>) : DialogFragment() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_app_list, null)
        searchView = contentView.findViewById(R.id.srchView_apps)
        recyclerView = contentView.findViewById(R.id.rvAppList)

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
        updateRecyclerView(appList)

        return MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
            .setTitle(R.string.credential_hint_app)
            .setView(contentView)
            .setPositiveButton("OK") { dialog, which ->
                // Handle OK button click
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Handle Cancel button click
            }
            .create()
    }
    private fun filterApps(name: String): List<App> {
        return appList.filter { app ->
            val appName = app.name.lowercase()
            appName.contains(name.lowercase())
        }
    }

    private fun updateRecyclerView(apps: List<App>?) {
        recyclerView.adapter = AdapterAppList(context, apps)
    }
}
