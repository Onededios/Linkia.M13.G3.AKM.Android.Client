package com.linkiaM13G3.akmAndroidClient.Helpers

class Validators {

    companion object {
        fun validateUser(input: String?): Boolean = validateMail(input).isValid || validateUsername(input).isValid
        fun validateMail(input: String?): FieldCheck {
            val firstCheck = isNotNullEmptyOrBlank(input)
            if (!firstCheck.isValid) return firstCheck

            val isValidMail = input!!.matches(Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

            return if (isValidMail) FieldCheck(true, null)
            else FieldCheck(false, "Please enter a valid email")
        }
        fun validateUsername(input: String?): FieldCheck {
            val firstCheck = isNotNullEmptyOrBlank(input)
            if (!firstCheck.isValid) return firstCheck

            val isWithinLengthLimit = input!!.length <= 20
            val containsValidCharacters = input.matches(Regex("^[a-zA-Z0-9_-]+"))

            return when {
                !isWithinLengthLimit -> FieldCheck(false, "Username must not be greater than 20 characters")
                !containsValidCharacters -> FieldCheck(false, "Username can only contain letters, numbers, underscores, and hyphens")
                else -> FieldCheck(true, null)
            }
        }
        fun validatePassword(input: String?): FieldCheck {
            val firstCheck = isNotNullEmptyOrBlank(input)
            if (!firstCheck.isValid) return firstCheck

            val lengthCheck = input!!.length > 8
            val upperCaseCheck = Regex("[A-Z]").containsMatchIn(input)
            val lowerCaseCheck = Regex("[a-z]").containsMatchIn(input)
            val numberCheck = Regex("\\d").containsMatchIn(input)
            val specialCharCheck = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+").containsMatchIn(input)

            if (!lengthCheck) return FieldCheck(false, "Password must be at least 8 characters long")
            if (!upperCaseCheck) return FieldCheck(false, "Password must contain at least one uppercase letter")
            if (!lowerCaseCheck) return FieldCheck(false, "Password must contain at least one lowercase letter")
            if (!numberCheck) return FieldCheck(false, "Password must contain at least one number")
            if (!specialCharCheck) return FieldCheck(false, "Password must contain at least one special character")

            return FieldCheck(true, null)
        }


        fun validateName(input: String?): FieldCheck {
            val firstCheck = isNotNullEmptyOrBlank(input)
            if (!firstCheck.isValid) return firstCheck

            val lengthCheck = input!!.length in 2..25
            val validChars = input.all { it.isLetter() || it == '-' || it == '\'' }

            if (!lengthCheck) return FieldCheck(false, "A name must be from 2 to 25 letters length")
            if (!validChars) return FieldCheck(false, "Name can only contain letters")

            return FieldCheck(true, null)
        }
        fun validateTelephone(input: String?): FieldCheck {
            val firstCheck = isNotNullEmptyOrBlank(input)
            if (!firstCheck.isValid) return firstCheck

            val lengthCheck = input!!.length in 8..15
            val charCheck = Regex("^[0-9]+\$").matches(input)

            if (!lengthCheck) return FieldCheck(false, "Telephone must be from 8 to 15 numbers length")
            if (!charCheck) return FieldCheck(false, "Telephone can only contain numbers")

            return FieldCheck(true, null)
        }
        fun validateRePassword(password: String, rePassword: String): FieldCheck {
            val match = password == rePassword
            return FieldCheck(match, if(match) null else "Passwords does not match")
        }
        fun validateCountryCode(input: String?, array: Array<String>): FieldCheck {
            val firstCheck = isNotNullEmptyOrBlank(input)
            if (!firstCheck.isValid) return firstCheck

            val isInArray = input in array

            return FieldCheck(isInArray, if (isInArray) null else "C. Code does not match with any of the list")
        }

        private fun isNotNullEmptyOrBlank(input: String?): FieldCheck {
            val itIs = !input.isNullOrEmpty() || input!!.isNotBlank()
            return FieldCheck(itIs, if (!itIs) "Please fill this field" else null)
        }
    }
}