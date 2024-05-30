package com.linkiaM13G3.akmAndroidClient.Clients

import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.DTOs.UserModifyDTO
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignInDTO
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignUpDTO
import com.linkiaM13G3.akmAndroidClient.Entities.User
import retrofit2.HttpException

private val connection = Connection().userService
class UserClient {
    suspend fun userSignInAsync(dto: UserSignInDTO) : User? {
        return try {
            connection.userSignIn(dto)
        }
        catch (e: HttpException) {
            Log.e("User", "Error signing in: ${e.message}")
            null
        }
    }

    suspend fun userSignUpAsync(dto: UserSignUpDTO) : Boolean {
        return try {
            connection.userSignUp(dto)
        }
        catch (e: HttpException) {
            Log.e("User", "Error signing up: ${e.message}")
            false
        }
    }

    suspend fun userModifyAsync(dto: UserModifyDTO) : Boolean {
        return try {
            connection.userModify(dto)
        }
        catch (e: HttpException) {
            Log.e("User", "Error modifying user: ${e.message}")
            false
        }
    }

    suspend fun checkUsername(username: String) : Boolean {
        return try {
            connection.checkUsername(username = username)
        } catch (e: HttpException) {
            Log.e("User", "Error checking username: ${e.message}")
            true
        }
    }

    suspend fun checkMail(mail: String) : Boolean {
        return try {
            connection.checkMail(mail = mail)
        } catch (e: HttpException) {
            Log.e("User", "Error checking mail: ${e.message}")
            true
        }
    }

}