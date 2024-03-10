package com.linkiaM13G3.akmAndroidClient.API

import com.linkiaM13G3.akmAndroidClient.Services.AppService

const val protocol: String = "http:"
const val url: String = "192.168.0.23"
const val port = "8182"
val mainUrl = listOf(url, port).joinToString(":")
val fullUrl = listOf(protocol, "", mainUrl, "").joinToString("/")

class Connection {
    var appService = Service.buildService(AppService::class.java, fullUrl)
}