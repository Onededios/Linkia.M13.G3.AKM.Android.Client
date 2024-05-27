package com.linkiaM13G3.akmAndroidClient.API

import com.linkiaM13G3.akmAndroidClient.Services.AppService
import com.linkiaM13G3.akmAndroidClient.Services.PasswordService
import com.linkiaM13G3.akmAndroidClient.Services.StatusService
import com.linkiaM13G3.akmAndroidClient.Services.UserService

const val protocol: String = "https:"
const val url: String = "x79411h5-7189.uks1.devtunnels.ms"
//const val port = "8182"
//val mainUrl = listOf(url, port).joinToString(":")
val mainUrl = url
val fullUrl = listOf(protocol, "", mainUrl, "").joinToString("/")

class Connection {
    var appService = Service.buildService(AppService::class.java, fullUrl)
    var userService = Service.buildService(UserService::class.java, fullUrl)
    var statusService = Service.buildService(StatusService::class.java, fullUrl)
    var passwordService = Service.buildService(PasswordService::class.java, fullUrl)
}