package com.example.finalproj.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Email
import com.example.finalproj.components.GenericText
import com.example.finalproj.components.HeadingTextComponent
import com.example.finalproj.components.NavigateBack
import com.example.finalproj.components.PadAllExceptBottom
import com.example.finalproj.components.Password
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseKeys
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.User
import com.example.finalproj.util.validation.ConfirmPasswordState
import com.example.finalproj.util.validation.EmailState
import com.example.finalproj.util.validation.PasswordState
import com.example.finalproj.util.validation.TextState

@Composable
fun Signup(popBack: () -> Boolean) {

    val context = LocalContext.current

    val emailState = remember {
        EmailState()
    }
    val passwordState = remember {
        PasswordState()
    }
    val confirmPasswordState = remember {
        ConfirmPasswordState(passwordState = passwordState)
    }
    val fullNameTextState = remember {
        TextState()
    }

    fun validateInput() {
        if (fullNameTextState.isValid &&
            emailState.isValid &&
            passwordState.isValid &&
            confirmPasswordState.isValid
        ) {
            context.submitRequest(
                emailState.text,
                passwordState.text,
                fullNameTextState.text,
                popBack
            )
        }
    }

    Surface(
        Modifier
            .fillMaxSize(), color = Color.White
    ) {
        Column(
            Modifier
                .padding(top = 65.dp)
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
        ) {

            Row(Modifier.padding(start = 30.dp)) {
                NavigateBack(popBack)
            }

            Column(
                PadAllExceptBottom(20),
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                HeadingTextComponent(heading = "Sign up")

                Email(
                    emailState = emailState,
                    isOutlined = true,
                    icon = Icons.Default.Email,
                )

                GenericText(
                    textState = fullNameTextState,
                    isOutlined = true,
                    label = "Full Name",
                    icon = Icons.Default.Person
                )

                Password(
                    imeAction = ImeAction.Next,
                    passwordState = passwordState,
                    isOutlined = true
                )


                Password(
                    label = "Confirm Password",
                    passwordState = confirmPasswordState,
                    isOutlined = true,
                    onImeAction = {
                        validateInput()
                    }
                )

                Eat2FitButton(
                    onClick = {
                        validateInput()
                    },
                    enabled = fullNameTextState.isValid &&
                            emailState.isValid &&
                            passwordState.isValid &&
                            confirmPasswordState.isValid,
                )
                {
                    Text(
                        fontSize = 20.sp,
                        text = "Sign up",
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}


private fun Context.submitRequest(
    email: String,
    password: String,
    fullName: String,
    popBack: () -> Boolean,
) {
    AuthenticationManager.registerUser(
        email = email,
        password = password,
        onSuccess = {
            DatabaseManager.updateUser(User(
                fullName = fullName
            ))

            Toast.makeText(this, "Successfully signed up", Toast.LENGTH_SHORT).show()
            popBack()

        },
        onFailure = {
            Toast.makeText(this, "Failed to sign up", Toast.LENGTH_SHORT).show()
        }
    )
}