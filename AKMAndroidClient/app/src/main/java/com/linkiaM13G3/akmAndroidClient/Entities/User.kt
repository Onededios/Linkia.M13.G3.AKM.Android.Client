package com.linkiaM13G3.akmAndroidClient.Entities

import java.util.UUID

open class User(
        id: UUID,
        var first_name: String = "",
        var last_name: String = "",
        var email: String = "",
        var username: String = "",
        var password: String = "",
        var country_code: String = "",
        var telephone: String = ""

) : Entity(id)