package com.linkiaM13G3.akmAndroidClient.Services;

import com.linkiaM13G3.akmAndroidClient.Entities.User;

import retrofit2.http.POST;

interface UserService {
    @POST("User/SignIn")
    suspend fun userSignIn(): User
    @POST("User/SignUp")
    suspend fun userSignUp(): Boolean
    @POST("User/ModifyUser")
    suspend fun userModify(): Boolean
}
