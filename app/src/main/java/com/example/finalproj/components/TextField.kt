package com.example.finalproj.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.finalproj.R
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.ui.theme.Shapes
import com.example.finalproj.ui.theme.Tertirary
import com.example.finalproj.util.validation.EmailState
import com.example.finalproj.util.validation.PasswordState
import com.example.finalproj.util.validation.TextState


open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String) -> String = { "" },
) {
    var text: String by mutableStateOf("")

    // was the TextField ever focused
    var isFocusedDirty: Boolean by mutableStateOf(false)
    private var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text)
        } else {
            // pass
            null
        }
    }
}

fun textFieldStateSaver(state: TextFieldState) = listSaver<TextFieldState, Any>(
    save = { listOf(it.text, it.isFocusedDirty) },
    restore = {
        state.apply {
            text = it[0] as String
            isFocusedDirty = it[1] as Boolean
        }
    }
)


@Composable
fun GenericText(
    label: String,
    modifier: Modifier = Modifier,
    textState: TextFieldState = remember { TextState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    isOutlined: Boolean = false,
    icon: ImageVector
) {

    if (isOutlined) {
        OutlinedTextField(
            value = textState.text,
            placeholder = { Text(label) },
            onValueChange = { textState.text = it },
            leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
            shape = Shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    textState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        textState.enableShowErrors()
                    }
                },
            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = textState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            supportingText = {
                textState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true
        )
    } else {


        TextField(
            value = textState.text,
            placeholder = { Text("Email") },
            onValueChange = { textState.text = it },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            shape = Shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    textState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        textState.enableShowErrors()
                    }
                },
            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = textState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            supportingText = {
                textState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true
        )
    }
}

@Composable
fun Email(
    modifier: Modifier = Modifier,
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorLabelColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
    ),
    isOutlined: Boolean = false,
    icon: ImageVector = Icons.Default.Person
) {

    if (isOutlined) {
        OutlinedTextField(
            value = emailState.text,
            placeholder = { Text("Email") },
            onValueChange = { emailState.text = it },
//        label = {
//            Text(
//                text = stringResource(id = R.string.email),
//                style = MaterialTheme.typography.bodyMedium,
//            )
//        },
            leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
            shape = Shapes.small,
//            colors = colors,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    emailState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        emailState.enableShowErrors()
                    }
                },
            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = emailState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            supportingText = {
                emailState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true
        )
    } else {


        TextField(
            value = emailState.text,
            placeholder = { Text("Email") },
            onValueChange = { emailState.text = it },
//        label = {
//            Text(
//                text = stringResource(id = R.string.email),
//                style = MaterialTheme.typography.bodyMedium,
//            )
//        },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            shape = Shapes.small,
            colors = colors,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    emailState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        emailState.enableShowErrors()
                    }
                },
            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = emailState.showErrors(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            supportingText = {
                emailState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true
        )
    }
}


@Composable
fun Password(
    modifier: Modifier = Modifier,
    label: String = "Password",
    passwordState: TextFieldState = remember { PasswordState() },
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    isOutlined: Boolean = false,
) {

    var showPassword by remember {
        mutableStateOf(false)
    }

    if (isOutlined) {
        OutlinedTextField(
            value = passwordState.text,
            placeholder = { Text(label) },
            onValueChange = {
                passwordState.text = it
                passwordState.enableShowErrors()
            },
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    passwordState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        passwordState.enableShowErrors()
                    }
                },
            shape = Shapes.small,
            textStyle = MaterialTheme.typography.bodyMedium,
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                val description = if (showPassword) "Show Password" else "Hide Password"
                val iconImage =
                    if (showPassword) R.drawable.eye_closed_fill else R.drawable.eye_closed
                IconButton(onClick = {
                    showPassword = !showPassword
                }) {
                    Icon(
                        painter = painterResource(id = iconImage),
                        contentDescription = description,
                        tint = Tertirary,
                    )
                }
            },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = passwordState.showErrors(),
            supportingText = {
                passwordState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true,
        )
    } else {
        TextField(
            value = passwordState.text,
            placeholder = { Text(label) },
            onValueChange = {
                passwordState.text = it
                passwordState.enableShowErrors()
            },
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    passwordState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        passwordState.enableShowErrors()
                    }
                },
            shape = Shapes.small,
            textStyle = MaterialTheme.typography.bodyMedium,
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                val description = if (showPassword) "Show Password" else "Hide Password"
                val iconImage =
                    if (showPassword) R.drawable.eye_closed_fill else R.drawable.eye_closed
                IconButton(onClick = {
                    showPassword = !showPassword
                }) {
                    Icon(
                        painter = painterResource(id = iconImage),
                        contentDescription = description,
                        tint = Tertirary,
                    )
                }
            },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = passwordState.showErrors(),
            supportingText = {
                passwordState.getError()?.let { error -> TextFieldError(textError = error) }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorLabelColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
        )
    }
}


@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = Eat2FitTheme.colors.red
        )
    }
}