package com.linkiaM13G3.akmAndroidClient.Entities

abstract class User : Entity() {
    lateinit var first_name: String
    lateinit var last_name: String
    lateinit var email: String
    lateinit var username: String
    lateinit var password: String
    lateinit var country_code: String
    lateinit var telephone: String
}