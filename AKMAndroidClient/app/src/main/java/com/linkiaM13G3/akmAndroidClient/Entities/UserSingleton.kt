package com.linkiaM13G3.akmAndroidClient.Entities

object UserSingleton : User() {
    fun initializeWithUser(user: User) {
        first_name = user.first_name
        last_name = user.last_name
        email = user.email
        username = user.username
        password = user.password
        country_code = user.country_code
        telephone = user.telephone
    }
}