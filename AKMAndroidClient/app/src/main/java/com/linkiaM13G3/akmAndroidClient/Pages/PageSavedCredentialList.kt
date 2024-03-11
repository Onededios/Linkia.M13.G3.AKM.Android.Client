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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
// Asegúrate de usar el método correcto y pasar el userId
        recyclerView.adapter = AdapterApps(databaseHelper.getAllAppsByUserId(userId)) { app ->
            showCustomDialog(app)
        }
    }

    private fun showCustomDialog(app: App) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null)

        // Se asume que "notes" es donde quieres mostrar el nombre de la app.
        val appNameTextView = dialogView.findViewById<TextView>(R.id.notes) // Asumiendo que 'notes' es un TextView.
        val appUsernameEmailTextView = dialogView.findViewById<MaterialTextView>(R.id.etUsername) // Asumiendo que es un MaterialTextView o cambia a TextView si es necesario.
        val appPasswordEditText = dialogView.findViewById<TextInputEditText>(R.id.etPassword) // Asumiendo que 'etPassword' está dentro de 'passwordInputLayout' y es editable.



        // Configurar los campos del diálogo con los detalles de la app.
        appNameTextView.text = app.name
        appUsernameEmailTextView.text = app.usernameOrEmail
        appPasswordEditText.setText(app.password) // Establecer texto directamente.

        val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Close", null) // Solo necesitas un botón para cerrar el diálogo.
                .create()

        customDialog.show()
    }
    inner class AdapterApps(private val appsList: List<App>, private val onAppClick: (App) -> Unit) : RecyclerView.Adapter<AdapterApps.AppViewHolder>() {

        inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val appNameTextView: TextView = view.findViewById(R.id.tvAppname) // Asegúrate de que este ID esté correcto según tu item_apps.xml
            // Si en tu item_apps.xml tienes definidos TextViews para username/email y password, debes agregarlos aquí.
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apps, parent, false)
            return AppViewHolder(view)
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            val app = appsList[position]
            holder.appNameTextView.text = app.name
            // Configura aquí el resto de los datos de la app, como el icono, si es necesario y tienes esa información disponible.
            holder.itemView.setOnClickListener { onAppClick(app) }
        }

        override fun getItemCount(): Int = appsList.size
    }
}
