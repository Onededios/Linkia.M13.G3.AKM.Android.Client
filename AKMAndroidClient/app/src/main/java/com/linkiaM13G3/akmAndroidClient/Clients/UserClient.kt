package com.linkiaM13G3.akmAndroidClient.Clients

import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.Entities.User
import java.io.IOException

private val connection = Connection().userService
class UserClient {
    suspend fun userSignInAsync() : User? {
        return try {
            connection.userSignIn()
        }
        catch (e: IOException) {
            Log.e("User", "Error signing in: ${e.message}")
            null
        }
    }

    suspend fun userSignUpAsync() : Boolean {
        return try {
            connection.userSignUp()
        }
        catch (e: IOException) {
            Log.e("User", "Error signing up: ${e.message}")
            false
        }
    }

    suspend fun userModifyAsync() : Boolean {
        return try {
            connection.userModify()
        }
        catch (e: IOException) {
            Log.e("User", "Error modifying user: ${e.message}")
            false
        }
    }

}