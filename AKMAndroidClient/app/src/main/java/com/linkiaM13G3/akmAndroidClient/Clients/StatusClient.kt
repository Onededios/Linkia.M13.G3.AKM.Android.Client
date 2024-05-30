package com.linkiaM13G3.akmAndroidClient.Clients

import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import retrofit2.HttpException

private val connection = Connection().statusService

class StatusClient {
    suspend fun getStatusAsync(): Boolean {
        return try {
            connection.getStatus().isSuccessful
        } catch (e: HttpException) {
            Log.e("Apps", "Error fetching apps: ${e.message}")
            false
        }
    }
}