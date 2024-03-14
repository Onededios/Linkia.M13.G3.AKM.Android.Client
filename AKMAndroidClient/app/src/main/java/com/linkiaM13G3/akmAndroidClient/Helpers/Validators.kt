package com.linkiaM13G3.akmAndroidClient.Helpers

class Validators {
    companion object {
        fun validateUser(input: String?): Boolean {
            if (validateMail(input) || validateUsername(input)) return true
            return false
        }

        fun validateMail(input: String?): Boolean {
            if (input.isNullOrEmpty() || input.isBlank()) return false
            val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            if (input.matches(emailRegex)) return true
            return false
        }

        fun validateUsername(input: String?): Boolean {
            if (input.isNullOrEmpty() || input.isBlank()) return false
            val nicknameRegex = Regex("^[a-zA-Z0-9_-]{3,16}\$")
            if (input.matches(nicknameRegex)) return true
            return false
        }
        fun isValidPassword(input: String): Boolean {
            val minLength = 8
            val hasUppercase = Regex("[A-Z]").containsMatchIn(input)
            val hasLowercase = Regex("[a-z]").containsMatchIn(input)
            val hasDigit = Regex("\\d").containsMatchIn(input)
            val hasSpecialChar = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+").containsMatchIn(input)

            return input.length >= minLength &&
                    hasUppercase &&
                    hasLowercase &&
                    hasDigit &&
                    hasSpecialChar
        }
    }
}