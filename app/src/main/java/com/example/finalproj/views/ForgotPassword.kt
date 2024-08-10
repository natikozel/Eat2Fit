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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Email
import com.example.finalproj.components.NavigateBackArrow
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.util.validation.EmailState

@Composable
fun ForgotPassword(popBack: () -> Boolean) {
    val context = LocalContext.current
    val emailState = remember {
        EmailState()
    }


    fun validateInput(email: String) {
        if (emailState.isValid) {
            context.submitRequest(emailState.text)
        }
    }


    Surface(
        Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .navigationBarsPadding(), color = Color.White
    ) {
        Column(Modifier.padding(top = 80.dp)) {
            NavigateBackArrow(popBack)
            Column(
                Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.forgotPassword_label),
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontSize = 39.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.forgot_password_welcome), fontFamily = FontFamily(Font(R.font.karla_bold)),
                )


                Email(
                    emailState = emailState,
                    onImeAction = {validateInput(emailState.text)},
                    isOutlined = true
                )

                Eat2FitButton(onClick = {validateInput(emailState.text)}, enabled = emailState.isValid) {
                    Text(text = stringResource(R.string.submit))
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

private fun Context.submitRequest(email: String) {
    AuthenticationManager.getAuth().sendPasswordResetEmail(email)
    Toast.makeText(this, getString(R.string.forgot_password_submission), Toast.LENGTH_SHORT).show()
}