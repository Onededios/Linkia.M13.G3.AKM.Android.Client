package com.linkiaM13G3.akmAndroidClient.Services;

import com.linkiaM13G3.akmAndroidClient.DTOs.UserModifyDTO
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignInDTO
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignUpDTO
import com.linkiaM13G3.akmAndroidClient.Entities.User;
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST;
import retrofit2.http.Query

interface UserService {
    @POST("User/SignIn")
    suspend fun userSignIn(@Body signInData: UserSignInDTO): User
    @POST("User/SignUp")
    suspend fun userSignUp(@Body signUpData: UserSignUpDTO): Boolean
    @POST("User/ModifyUser")
    suspend fun userModify(@Body modifyData: UserModifyDTO): Boolean
    @GET("User/UsernameExists")
    suspend fun checkUsername(@Query("username") username: String): Boolean
    @GET("User/MailExists")
    suspend fun checkMail(@Query("mail") mail: String): Boolean
}