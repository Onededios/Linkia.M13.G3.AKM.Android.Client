package com.linkiaM13G3.akmAndroidClient.Clients

import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.User
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import java.io.IOException
import java.util.UUID

private val connection = Connection().passwordService

class PasswordClient {
    suspend fun fetchUserPasswords() : List<Password>? {
        return try {
            connection.getUserPasswords(UserSingleton.id)
        } catch (e: IOException) {
            Log.e("Passwords", "Error fetching apps: ${e.message}")
            emptyList()
        }
    }
}