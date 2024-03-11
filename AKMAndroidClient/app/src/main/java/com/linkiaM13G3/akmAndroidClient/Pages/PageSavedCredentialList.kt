package com.linkiaM13G3.akmAndroidClient.Pages

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.linkiaM13G3.akmAndroidClient.R
import com.linkiaM13G3.akmAndroidClient.Pages.DatabaseHelper.App
class PageSavedCredentialListActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pages_pwd_list)
        databaseHelper = DatabaseHelper.getInstance(this)

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            startActivity(Intent(this, PageAppsActivity::class.java))
        }
        val sharedPreferences = getSharedPreferences("miApp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1) // Utiliza un valor predeterminado que indique "no encontrado"

        val recyclerView: RecyclerView = findViewById(R.id.rvListPwd)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterApps(databaseHelper.getAllAppsByUserId(userId)) { app ->
            showCustomDialog(app)
        }
    }

    private fun showCustomDialog(app: App) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null)


        val appNameTextView = dialogView.findViewById<TextView>(R.id.notes)
        val appUsernameEmailTextView = dialogView.findViewById<MaterialTextView>(R.id.etUsername)
        val appPasswordEditText = dialogView.findViewById<TextInputEditText>(R.id.etPassword)




        appNameTextView.text = app.name
        appUsernameEmailTextView.text = app.usernameOrEmail
        appPasswordEditText.setText(app.password)

        val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .create()

        customDialog.show()
    }
    inner class AdapterApps(private val appsList: List<App>, private val onAppClick: (App) -> Unit) : RecyclerView.Adapter<AdapterApps.AppViewHolder>() {

        inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val appNameTextView: TextView = view.findViewById(R.id.tvAppname)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apps, parent, false)
            return AppViewHolder(view)
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            val app = appsList[position]
            holder.appNameTextView.text = app.name

            holder.itemView.setOnClickListener { onAppClick(app) }
        }

        override fun getItemCount(): Int = appsList.size
    }
}
