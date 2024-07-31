package com.example.finalproj.util.validation

import com.example.finalproj.components.TextFieldState
import com.example.finalproj.components.textFieldStateSaver
import java.util.regex.Pattern


class TextState(private val input: String? = null) :
    TextFieldState(validator = ::isTextValid, errorFor = ::validationError) {
    init {
        input?.let {
            text = it
        }
    }
}


private fun validationError(input: String): String {
    return "Input can't be empty"
}

private fun isTextValid(text: String): Boolean {
    return text != ""
}
