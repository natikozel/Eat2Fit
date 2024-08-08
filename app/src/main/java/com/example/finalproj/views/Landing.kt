package com.example.finalproj.views

import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitSurface
import com.example.finalproj.components.Email
import com.example.finalproj.components.Password
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseKeys
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.models.User
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.Destinations
import com.example.finalproj.util.validation.EmailState
import com.example.finalproj.util.validation.PasswordState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


fun NavGraphBuilder.addLandingGraph(
    onNavigateToRoute: (String) -> Unit,
    popBack: () -> Boolean,
    navigateAndClear: (String) -> Unit
) {
    composable(
        LandingSections.MAIN.route,
        enterTransition = { fadeIn() },
        exitTransition = { slideOutOfContainer() }) {

        BackHandler(true) {
            // pass
        }

        CheckIfUserIsLoggedIn(
            onSuccess = {
                DatabaseManager.readUser().addOnSuccessListener { dataSnapshot ->
                    AuthenticationManager.setUser(dataSnapshot.getValue(User::class.java)!!)
                    if (AuthenticationManager.getUser().hasLoggedInOnce!!) {
                        DatabaseManager.attachUserListener()
                        navigateAndClear(Destinations.PROFILE)
                    } else {
                        onNavigateToRoute(Destinations.WELCOME)
                    }
                }
            },
            onFailure = {
                Landing(onNavigateToRoute)
            }
        )
    }
    composable(
        LandingSections.SIGNUP.route,
        enterTransition = { slideIntoContainer() },
        exitTransition = { fadeOut() }) {
        BackHandler(true) {
            // pass
        }

        Signup(popBack)
    }
    composable(
        LandingSections.FORGOT_PASSWORD.route,
        enterTransition = { slideIntoContainer() },
        exitTransition = { fadeOut() }) {
        BackHandler(true) {
            // pass
        }

        ForgotPassword(popBack)
    }
}


enum class LandingSections(
    val route: String
) {
    MAIN("main"), // landing/main
    SIGNUP("signup"), // landing/signup
    FORGOT_PASSWORD("forgot_password"), // landing_forgot_password

}

@Composable
fun Landing(onNavigateToRoute: (String) -> Unit) {
    Eat2FitSurface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary,
        content = { Login(onNavigateToRoute) }
    )
}

private fun Context.buildExoPlayer() =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(
            MediaItem.fromUri(
                Uri.Builder().scheme("android.resource")
                    .authority(packageName)
                    .appendPath("raw")
                    .appendPath("landing_video")
                    .build().toString()
            )
        )
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        useController = false
    }

private fun Context.doLogin(
    onNavigateToRoute: (String) -> Unit,
    email: String,
    password: String,
    onError: (String) -> Unit
) {
    AuthenticationManager.loginUser(
        email = email,
        password = password,
        onSuccess = {
            DatabaseManager.attachUserListener()
            onNavigateToRoute(Destinations.PROFILE)
        },
        onFailure = { exception ->
            onError(exception.message ?: "Unknown error")
        }
    )
}


@Composable
private fun CheckIfUserIsLoggedIn(
    onSuccess: () -> Unit,
    onFailure: @Composable () -> Unit,
) {
    val currentUser = Firebase.auth.currentUser
    if (currentUser != null) {
        onSuccess()
    } else {
        onFailure()
    }
}

@Composable
fun Login(onNavigateToRoute: (String) -> Unit) {

    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val exoPlayer = remember { context.buildExoPlayer() }
    val emailState = remember { EmailState() }
    val passwordState = remember { PasswordState() }
    val loginErrorMessage = remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            Modifier
                .navigationBarsPadding()
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                Modifier.size(150.dp),
                tint = Color.White
            )
            Email(
                emailState = emailState,
                onImeAction = { passwordFocusRequester.requestFocus() }
            )
            Password(
                passwordState = passwordState,
                modifier = Modifier.focusRequester(passwordFocusRequester),
                onImeAction = {
                    if (emailState.isValid && passwordState.isValid)
                        context.doLogin(
                            onNavigateToRoute,
                            emailState.text,
                            passwordState.text,
                            onError = { loginErrorMessage.value = it }
                        )
                }
            )

            loginErrorMessage.value?.let {
                Text(
                    it,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                )
            }



            TextButton(onClick = {
                onNavigateToRoute(LandingSections.FORGOT_PASSWORD.route)
            }) {
                Text("Forgot Password?", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))

            Button(
                enabled = emailState.isValid && passwordState.isValid,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    context.doLogin(
                        onNavigateToRoute,
                        emailState.text,
                        passwordState.text,
                        onError = { loginErrorMessage.value = it }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black,
                    disabledContainerColor = Eat2FitTheme.colors.emptyGreen
                ),
                content = { Text("SIGN IN", Modifier.padding(vertical = 8.dp)) }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, alignment = Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("Don't have an account?", color = Color.White)
                    TextButton(onClick = {
                        onNavigateToRoute(LandingSections.SIGNUP.route)
                    }) {
                        Text("SIGN UP", color = Color.White)
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose { exoPlayer.release() }
        }
    }
}
