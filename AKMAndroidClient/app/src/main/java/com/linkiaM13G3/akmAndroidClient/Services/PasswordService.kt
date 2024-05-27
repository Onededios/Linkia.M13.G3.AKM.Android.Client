package com.linkiaM13G3.akmAndroidClient.Services

import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.UUID

interface PasswordService {
    @GET("Password/GetPassword")
    suspend fun getPassword(@Query("id") id: UUID): Password
    @GET("Password/GetUserPasswords")
    suspend fun getUserPasswords(@Query("id_user") idUser: UUID): List<Password>?
}