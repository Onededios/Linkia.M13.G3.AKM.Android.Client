package com.linkiaM13G3.akmAndroidClient.Entities

import com.google.type.DateTime
import java.util.UUID

class Password : Entity() {
    lateinit var alias: String
    lateinit var username: String
    lateinit var password: String
    var app: App? = null
    var tags: Array<String>? = null
    var description: String? = null
    lateinit var date_expiration: String
}