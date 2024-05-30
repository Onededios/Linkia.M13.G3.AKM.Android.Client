package com.linkiaM13G3.akmAndroidClient.Entities

import java.util.UUID

class Password(
        id: UUID,
        var alias: String? = null,
        var username: String? = null,
        var password: String,
        var app: App? = null,
        var tags: Array<Tag>? = null,
        var description: String? = null,
        var date_expiration: String
) : Entity(id) {
    fun copy(pass: Password): Password {
        return Password(
                pass.id,
                pass.alias,
                pass.username,
                pass.password,
                pass.app,
                pass.tags,
                pass.description,
                pass.date_expiration
        )
    }
}