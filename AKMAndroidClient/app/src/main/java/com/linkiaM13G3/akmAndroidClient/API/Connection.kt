package com.linkiaM13G3.akmAndroidClient.API

import com.linkiaM13G3.akmAndroidClient.Services.AppService
import com.linkiaM13G3.akmAndroidClient.Services.StatusService
import com.linkiaM13G3.akmAndroidClient.Services.UserService

const val protocol: String = "https:"
const val url: String = "360rbm5k-5085.uks1.devtunnels.ms"
//const val port = "8182"
//val mainUrl = listOf(url, port).joinToString(":")
val mainUrl = url
val fullUrl = listOf(protocol, "", mainUrl, "").joinToString("/")

class Connection {
    var appService = Service.buildService(AppService::class.java, fullUrl)
    var userService = Service.buildService(UserService::class.java, fullUrl)
    var statusService = Service.buildService(StatusService::class.java, fullUrl)
}