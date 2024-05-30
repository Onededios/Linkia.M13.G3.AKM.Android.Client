package com.linkiaM13G3.akmAndroidClient.Services

import com.linkiaM13G3.akmAndroidClient.DTOs.PasswordCreationDTO
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.UUID

interface PasswordService {
    @GET("Password/GetPassword")
    suspend fun getPassword(@Query("id") id: UUID): Password
    @GET("Password/GetUserPasswords")
    suspend fun getUserPasswords(@Query("id_user") idUser: UUID): List<Password>?
    @PUT("Password/ModifyPassword")
    suspend fun updatePassword(@Body pass: Password) : Boolean
    @DELETE("Password/DeletePassword")
    suspend fun deletePassword(@Query("id") id: UUID) : Boolean
    @POST("Password/CreatePassword")
    suspend fun createPassword(@Body pass: PasswordCreationDTO) : Boolean
}