package com.linkiaM13G3.akmAndroidClient.DTOs

import java.util.UUID

class PasswordCreationDTO(
        var userId: UUID,
        var alias: String? = null,
        var appId: UUID? = null,
        var username: String? = null,
        var password: String,
        var tags: List<UUID>? = null,
        var description: String? = null
)