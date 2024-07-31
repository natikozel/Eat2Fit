package com.example.finalproj.util.validation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.finalproj.components.TextFieldState


class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError) {

        private var showPassword: Boolean by mutableStateOf(false)
        init {}

    fun togglePasswordVisibility() {
        showPassword = !showPassword
    }


}

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}


private fun isPasswordValid(password: String): Boolean {
    return password.length >= 6
}

@Suppress("UNUSED_PARAMETER")
private fun passwordValidationError(password: String): String {
    return "Password must be at least 6 characters long"
}

private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}
