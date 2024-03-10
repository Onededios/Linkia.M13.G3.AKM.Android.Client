package com.linkiaM13G3.akmAndroidClient.Pages

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.linkiaM13G3.akmAndroidClient.R

class PageSavedCredentialListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pages_pwd_list)

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            val intentTag = Intent(this, PageAppsActivity::class.java)
            startActivity(intentTag)
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvListPwd)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterSavedList(CredentialManager.credentials) { credential ->
            showCustomDialog(credential)
        }
    }

    private fun showCustomDialog(credential: Credential) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null)
        val usernameTextView = dialogView.findViewById<MaterialTextView>(R.id.etUsername)
        val passwordInputLayout = dialogView.findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val etPassword = dialogView.findViewById<TextInputEditText>(R.id.etPassword)
        val newPasswordInputLayout = dialogView.findViewById<TextInputLayout>(R.id.newPasswordInputLayout)
        val confNewPasswordInputLayout = dialogView.findViewById<TextInputLayout>(R.id.confNewPasswordInputLayout)
        val etNewPassword = dialogView.findViewById<TextInputEditText>(R.id.etNewPassword)
        val etconNewPassword = dialogView.findViewById<TextInputEditText>(R.id.etconNewPassword)
        val editPwdSwitch = dialogView.findViewById<SwitchMaterial>(R.id.editPwd)


        usernameTextView.text = credential.userName

        etPassword.setText(credential.userPwd)
        etPassword.keyListener = null

        fun cambiarVisibilidadCampos(isVisible: Boolean) {
            newPasswordInputLayout.visibility = if (isVisible) TextView.VISIBLE else TextView.GONE
            confNewPasswordInputLayout.visibility = if (isVisible) TextView.VISIBLE else TextView.GONE
            etNewPassword.visibility = if (isVisible) TextView.VISIBLE else TextView.GONE
            etconNewPassword.visibility = if (isVisible) TextView.VISIBLE else TextView.GONE
        }

        cambiarVisibilidadCampos(false)

        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            customDialog.dismiss()
        }

        editPwdSwitch.setOnCheckedChangeListener { _, isChecked ->
            cambiarVisibilidadCampos(isChecked)
        }

        customDialog.show()
    }
}



