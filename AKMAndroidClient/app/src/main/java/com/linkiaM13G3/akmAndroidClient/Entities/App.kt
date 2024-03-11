package com.linkiaM13G3.akmAndroidClient.Entities

abstract class App(id: Int, name: String, usernameOrEmail: String, password: String) : Entity() {
    lateinit var name: String
    var icon: String? = null
}