package com.linkiaM13G3.akmAndroidClient.Services

import retrofit2.Response
import retrofit2.http.GET

interface StatusService {
    @GET("Status/GetStatus")
    suspend fun getStatus(): Response<Void>
}