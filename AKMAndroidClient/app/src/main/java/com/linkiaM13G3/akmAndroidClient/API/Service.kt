package com.linkiaM13G3.akmAndroidClient.API;

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
        fun <T> buildService(service: Class<T>, url: String): T {
                val logger = HttpLoggingInterceptor()
                logger.setLevel(HttpLoggingInterceptor.Level.BODY)

                val client = OkHttpClient.Builder()
                        .addInterceptor(logger)
                        .build()

                val retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()

                return retrofit.create(service)
        }
}