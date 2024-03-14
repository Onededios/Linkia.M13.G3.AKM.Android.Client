package com.linkiaM13G3.akmAndroidClient.DTOs

import java.util.UUID

class UserSignInDTO(var username: String?, var password: String?)
class UserSignUpDTO(var username: String?, var password: String?)
class UserModifyDTO(var id: UUID?, var first_name: String?, var last_name: String?, var email: String?, var username: String?, var password: String?, var telephone: String?, var country_code: String?)