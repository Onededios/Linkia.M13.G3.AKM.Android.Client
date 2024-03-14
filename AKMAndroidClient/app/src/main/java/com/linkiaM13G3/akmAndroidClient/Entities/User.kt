package com.linkiaM13G3.akmAndroidClient.Entities

open class User : Entity() {
    var first_name: String = ""
        get() = field
        set(value) { field = value }
    var last_name: String = ""
        get() = field
        set(value) { field = value }
    var email: String = ""
        get() = field
        set(value) { field = value }
    var username: String = ""
        get() = field
        set(value) { field = value }
    var password: String = ""
        get() = field
        set(value) { field = value }
    var country_code: String = ""
        get() = field
        set(value) { field = value }
    var telephone: String = ""
        get() = field
        set(value) { field = value }
}