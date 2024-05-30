package com.linkiaM13G3.akmAndroidClient.Services

import com.linkiaM13G3.akmAndroidClient.Entities.App
import retrofit2.Call
import retrofit2.http.GET

interface AppService {
    @GET("App/GetApplications")
    suspend fun getApps(): List<App>?
}