package com.linkiaM13G3.akmAndroidClient.Pages

data class Credential(
    val appName: String,
    val userName: String,
    val userPwd: String
)

object CredentialManager {
    val credentials: MutableList<Credential> = mutableListOf()


}