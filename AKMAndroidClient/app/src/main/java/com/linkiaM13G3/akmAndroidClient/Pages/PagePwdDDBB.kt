package com.linkiaM13G3.akmAndroidClient.Pages

import android.content.Context

data class PagePwdDDBB(
    val name: String,
    val emailOrUsername: String,
    val password: String,
    val notes: String
){

    object PreferencesUtils {

        fun saveCredentials(context: Context, pagePwdDDBB: PagePwdDDBB) {
            val sharedPreferences = context.getSharedPreferences("MySecurePrefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("NAME_KEY", pagePwdDDBB.name)
                putString("EMAIL_OR_USERNAME_KEY", pagePwdDDBB.emailOrUsername)
                putString("PASSWORD_KEY", pagePwdDDBB.password)
                putString("NOTES_KEY", pagePwdDDBB.notes)
                apply()
            }
        }

        fun getCredentials(context: Context): PagePwdDDBB {
            val sharedPreferences = context.getSharedPreferences("MySecurePrefs", Context.MODE_PRIVATE)
            val name = sharedPreferences.getString("NAME_KEY", "") ?: ""
            val emailOrUsername = sharedPreferences.getString("EMAIL_OR_USERNAME_KEY", "") ?: ""
            val password = sharedPreferences.getString("PASSWORD_KEY", "") ?: ""
            val notes = sharedPreferences.getString("NOTES_KEY", "") ?: ""
            return PagePwdDDBB(name, emailOrUsername, password, notes)
        }

        fun getAllCredentials(context: Context): List<PagePwdDDBB> {
            val sharedPreferences = context.getSharedPreferences("MySecurePrefs", Context.MODE_PRIVATE)
            val allEntries = sharedPreferences.all
            val credentialsList = mutableListOf<PagePwdDDBB>()

            allEntries.forEach { entry ->
                // Aquí asumimos que cada conjunto de credenciales tiene un prefijo único en sus claves
                if (entry.key.endsWith("_NAME_KEY")) {
                    val prefix = entry.key.removeSuffix("_NAME_KEY")
                    val name = entry.value as? String ?: ""
                    val emailOrUsername = sharedPreferences.getString("${prefix}_EMAIL_OR_USERNAME_KEY", "") ?: ""
                    // Suponemos que no necesitas recuperar la contraseña y las notas aquí
                    credentialsList.add(PagePwdDDBB(name, emailOrUsername, "", ""))
                }
            }

            return credentialsList
        }
    }
}
