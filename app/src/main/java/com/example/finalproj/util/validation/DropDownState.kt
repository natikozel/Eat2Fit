package com.example.finalproj.util.validation

import com.example.finalproj.components.TextFieldState


class DropDownState(private val input: String? = null) :
    TextFieldState(validator = ::isTextValid, errorFor = ::validationError) {
    init {
        input?.let {
            text = it
        }
    }
}


private fun validationError(input: String): String {
    return "Input can't be empty and numbers must be above 0"
}

private fun isTextValid(text: String): Boolean {
    return text != ""
}

