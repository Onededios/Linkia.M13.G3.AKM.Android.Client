package com.linkiaM13G3.akmAndroidClient.Services;

import com.linkiaM13G3.akmAndroidClient.DTOs.UserModifyDTO
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignInDTO
import com.linkiaM13G3.akmAndroidClient.DTOs.UserSignUpDTO
import com.linkiaM13G3.akmAndroidClient.Entities.User;
import retrofit2.http.Body

import retrofit2.http.POST;

interface UserService {
    @POST("User/SignIn")
    suspend fun userSignIn(@Body signInData: UserSignInDTO): User
    @POST("User/SignUp")
    suspend fun userSignUp(@Body signUpData: UserSignUpDTO): Boolean
    @POST("User/ModifyUser")
    suspend fun userModify(@Body modifyData: UserModifyDTO): Boolean
}