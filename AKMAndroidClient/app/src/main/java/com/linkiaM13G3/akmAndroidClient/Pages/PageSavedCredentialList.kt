package com.linkiaM13G3.akmAndroidClient.Pages

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.linkiaM13G3.akmAndroidClient.R

class PageSavedCredentialListActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper // Mueve la declaración aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pages_pwd_list)
        databaseHelper = DatabaseHelper(this) // Inicializa aquí

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            val intentTag = Intent(this, PageAppsActivity::class.java)
            startActivity(intentTag)
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvListPwd)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterSavedList(getAllCredentials()) { credential ->
            showCustomDialog(credential)
        }
    }

    private fun getAllCredentials(): List<Credential> {
        return databaseHelper.getAllCredentials() // Asegúrate de implementar este método en DatabaseHelper
    }

    private fun showCustomDialog(credential: Credential) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null)
        val usernameTextView = dialogView.findViewById<MaterialTextView>(R.id.etUsername)
        val etPassword = dialogView.findViewById<TextInputEditText>(R.id.etPassword)
        val newPasswordInputLayout = dialogView.findViewById<TextInputLayout>(R.id.newPasswordInputLayout)
        val confNewPasswordInputLayout = dialogView.findViewById<TextInputLayout>(R.id.confNewPasswordInputLayout)
        val etNewPassword = dialogView.findViewById<TextInputEditText>(R.id.etNewPassword)
        val etconNewPassword = dialogView.findViewById<TextInputEditText>(R.id.etconNewPassword)
        val editPwdSwitch = dialogView.findViewById<SwitchMaterial>(R.id.editPwd)

        usernameTextView.text = credential.name
        etPassword.setText(credential.password)
        etPassword.keyListener = null // Disable editing of password field

        fun cambiarVisibilidadCampos(isVisible: Boolean) {
            newPasswordInputLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
            confNewPasswordInputLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
            etNewPassword.visibility = if (isVisible) View.VISIBLE else View.GONE
            etconNewPassword.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        // Set initial visibility
        cambiarVisibilidadCampos(false)

        editPwdSwitch.setOnCheckedChangeListener { _, isChecked ->
            cambiarVisibilidadCampos(isChecked)
        }

        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newPassword = etNewPassword.text.toString()
                val confirmNewPassword = etconNewPassword.text.toString()

                if (newPassword == confirmNewPassword) {
                    databaseHelper.updateCredential(credential.id, newPassword)
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        customDialog.show()
    }
}

