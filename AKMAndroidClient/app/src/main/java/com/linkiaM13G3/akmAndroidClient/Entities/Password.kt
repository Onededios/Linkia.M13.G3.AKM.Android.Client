package com.linkiaM13G3.akmAndroidClient.Entities

import com.google.type.DateTime
import java.util.UUID

abstract class Password : Entity() {
    lateinit var id_user: UUID
    lateinit var password: String
    var description: String? = null
    var date_expiration: DateTime? = null
}