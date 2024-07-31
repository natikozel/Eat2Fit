package com.example.finalproj.views

import androidx.compose.foundation.layout.Arrangement
import android.content.Context
import android.widget.Toast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Email
import com.example.finalproj.components.MyButton
import com.example.finalproj.components.MyTextField
import com.example.finalproj.components.NavigateBack
import com.example.finalproj.components.PadAllExceptBottom
import com.example.finalproj.components.TextInfoComponent
import com.example.finalproj.ui.theme.Primary
import com.example.finalproj.util.validation.EmailState

@Composable
fun ForgotPassword(popBack: () -> Boolean) {
    val context = LocalContext.current


    Surface(
        PadAllExceptBottom(20)
            .fillMaxSize()
            .navigationBarsPadding(), color = Color.White
    ) {
        Column(Modifier.padding(top = 80.dp)) {
            NavigateBack(popBack)
            Column(
                PadAllExceptBottom(20),
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Forgot Password?",
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontSize = 39.sp,
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
                TextInfoComponent(
                    textVal = "No worries, that also happens.\nPlease enter the email address associated with your account."
                )

                val emailState = remember {
                    EmailState()
                }
                Email(
                    emailState = emailState,
                    onImeAction = {context.submitRequest()},
                    isOutlined = true
                )

                Eat2FitButton(onClick = {context.submitRequest()}) {
                    Text(text = "Submit")
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 15.dp),
                    contentAlignment = Alignment.BottomStart
                ) {

                }

            }
        }
    }
}

private fun Context.submitRequest() {
    Toast.makeText(this, "Password was sent to the email", Toast.LENGTH_SHORT).show()
}