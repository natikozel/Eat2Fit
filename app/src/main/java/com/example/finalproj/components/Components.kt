package com.example.finalproj.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.finalproj.R
import com.example.finalproj.ui.theme.BorderColor
import com.example.finalproj.ui.theme.BrandColor
import com.example.finalproj.ui.theme.Primary
import com.example.finalproj.ui.theme.Tertirary


fun PadAllExceptBottom(by: Int) = Modifier.padding(top = by.dp, start = by.dp, end = by.dp)

@Composable
fun ImageComponent(image: Int) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .size(250.dp)
    )
}

@Composable
fun HeadingTextComponent(heading: String) {
    Text(
        text = heading,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 39.sp,
        color = Primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ForgotPasswordHeadingTextComponent(action: String) {
    Column {
        Text(
            text = action,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 39.sp,
            color = Primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Password?",
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-18).dp),
            fontSize = 39.sp,
            color = Primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    labelVal: String,
    icon: ImageVector,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    value: String? = null,
    readOnly: Boolean? = false,
    enabled: Boolean? = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    var textVal by remember {
        mutableStateOf("")
    }

    val typeOfKeyboard: KeyboardType = when (labelVal) {
        "Email" -> KeyboardType.Email
        "Full Name" -> KeyboardType.Text
        else -> KeyboardType.Text
    }


    OutlinedTextField(
        interactionSource = interactionSource,
        enabled = (enabled ?: true),
        value = (value ?: textVal),
        readOnly = (readOnly ?: false),
        onValueChange = {
            textVal = it
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BrandColor,
            unfocusedBorderColor = BorderColor,
//            textColor = Color.Black,
            focusedLeadingIconColor = BrandColor,
            unfocusedLeadingIconColor = Tertirary
        ),
        shape = MaterialTheme.shapes.small,
        placeholder = {
            Text(text = labelVal, color = Tertirary)
        },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)

        },
        trailingIcon = trailingIcon,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation = VisualTransformation.None

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputComponent(
    labelVal: String,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester? = null
) {
    var password by remember {
        mutableStateOf("")
    }
    var isShowPassword by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(
                focusRequester ?: FocusRequester()
            ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BrandColor,
            unfocusedBorderColor = BorderColor,
//            textColor = Color.Black
        ),
        shape = MaterialTheme.shapes.small,
        placeholder = {
            Text(text = labelVal, color = Tertirary)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            val description = if (isShowPassword) "Show Password" else "Hide Password"
            val iconImage =
                if (isShowPassword) R.drawable.eye_closed_fill else R.drawable.eye_closed
            IconButton(onClick = {
                isShowPassword = !isShowPassword
            }) {
                Icon(
                    painter = painterResource(id = iconImage),
                    contentDescription = description,
                    tint = Tertirary,
                )
            }
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun NavigateBack(popBack: () -> Boolean) {
    Row(

    ) {
        IconButton(onClick = {
            popBack()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                Modifier.size(80.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ForgotPasswordTextComponent(navController: NavHostController) {
    Text(
        text = "Forgot Password?",
        color = BrandColor,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.clickable {
            navController.navigate("ForgotPassword")
        }
    )
}

@Composable
fun MyButton(labelVal: String, navController: NavHostController, onClickAction: () -> Unit? = {}) {
    Button(
        onClick = { onClickAction() },
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Text(
            text = labelVal,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.clickable {
                if (labelVal == "Submit") {
                    navController.navigate("ResetPassword")
                }
            }
        )
    }
}

//@Composable
//fun BottomComponent(navController: NavHostController) {
//    Column {
//        MyButton(labelVal = "Continue", navController = navController)
//        Spacer(modifier = Modifier.height(10.dp))
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Divider(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f),
//                thickness = 1.dp,
//                color = Tertirary
//            )
//            Text(
//                text = "OR",
//                modifier = Modifier.padding(10.dp),
//                color = Tertirary,
//                fontSize = 20.sp
//            )
//            Divider(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f),
//                thickness = 1.dp,
//                color = Tertirary
//            )
//        }
//        Spacer(modifier = Modifier.height(5.dp))
//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier
//                .fillMaxWidth(),
//            colors = ButtonDefaults.outlinedButtonColors(
//                containerColor = BgSocial
//            )
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//
//                Text(
//                    text = "Login With Google",
//                    fontSize = 18.sp,
//                    color = Tertirary,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        }
//    }
//}

@Composable
fun BottomLoginTextComponent(
    initialText: String,
    action: String,
    navController: NavHostController
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BrandColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = action, annotation = action)
            append(action)
        }
    }

    ClickableText(text = annotatedString, onClick = {
        annotatedString.getStringAnnotations(it, it)
            .firstOrNull()?.also { span ->
                Log.d("BottomLoginTextComponent", "${span.item} is Clicked")
                if (span.item == "Join our coven!") {
                    navController.navigate("SignupScreen")
                }
            }
    })
}

@Composable
fun SignupTermsAndPrivacyText() {
    val initialText = "Join our coven and accept our "
    val termsNConditionText = "Terms & Conditions"
    val andText = " and "
    val privacyPolicyText = "Privacy Policy."
    val lastText = " Don't be afraid, we don't bite!"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BrandColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = termsNConditionText, annotation = termsNConditionText)
            append(termsNConditionText)
        }
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(andText)
        }
        withStyle(style = SpanStyle(color = BrandColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(lastText)
        }
    }

    ClickableText(text = annotatedString, onClick = {
        annotatedString.getStringAnnotations(it, it)
            .firstOrNull()?.also { span ->
                Log.d("SignupTermsAndPrivacyText", span.item)
            }
    })
}

@Composable
fun BottomSignupTextComponent(navController: NavHostController) {
    val initialText = "Already signed up?"
    val loginText = " Log In "
    val lastText = "and continue your dietary journey!"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BrandColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(lastText)
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ClickableText(text = annotatedString, onClick = {
            annotatedString.getStringAnnotations(it, it)
                .firstOrNull()?.also { span ->
                    if (span.item == "Log In") {
                        navController.navigate("LoginScreen")
                    }
                }
        })

    }

}

@Composable
fun TextInfoComponent(textVal: String) {
    Text(
        text = textVal, color = Tertirary, fontFamily = FontFamily(Font(R.font.karla_bold)),
    )
}