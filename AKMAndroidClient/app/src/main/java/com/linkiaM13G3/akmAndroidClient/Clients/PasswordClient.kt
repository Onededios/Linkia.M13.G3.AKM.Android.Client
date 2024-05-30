package com.linkiaM13G3.akmAndroidClient.Clients

import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.DTOs.PasswordCreationDTO
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import retrofit2.HttpException
import java.util.UUID

private val connection = Connection().passwordService

class PasswordClient {
    suspend fun fetchUserPasswords() : List<Password>? {
        return try {
            connection.getUserPasswords(UserSingleton.id)
        } catch (e: HttpException) {
            Log.e("Passwords", "Error fetching apps: ${e.message}")
            emptyList()
        }
    }

    suspend fun updatePassword(pass: Password) : Boolean {
        return try {
            connection.updatePassword(pass)
        } catch (e: HttpException) {
            Log.e("Passwords", "Error updating password: ${e.message}")
            false
        }
    }

    suspend fun deletePassword(id: UUID) : Boolean {
        return try {
            connection.deletePassword(id)
        } catch (e: HttpException) {
            Log.e("Passwords", "Error deleting password: ${e.message()}")
            false
        }
    }

    suspend fun createPassword(pass: PasswordCreationDTO) : Boolean {
        return try {
            connection.createPassword(pass)
        } catch (e: HttpException) {
            Log.e("Passwords", "Error creating password: ${e.message()}")
            false
        }
    }
}