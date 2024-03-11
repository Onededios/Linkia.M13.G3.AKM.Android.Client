package com.linkiaM13G3.akmAndroidClient.API;

import com.linkiaM13G3.akmAndroidClient.Services.AppService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
        fun <T> buildService(service: Class<T>, url: String): T {
                val client = OkHttpClient.Builder().build()
                val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(client).build()
                return retrofit.create(service)
        }
}