package com.example.finalproj.views

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.finalproj.R
import com.example.finalproj.components.Eat2FitButton
import com.example.finalproj.components.Eat2FitSurface


enum class WelcomeSections(
    @StringRes val title: Int,
    val route: String
) {
    WELCOME(R.string.landing_main, "main"),
    QUESTIONNAIRE(R.string.home_feed, "questionnaire"),

}


fun NavGraphBuilder.addWelcomeGraph(
    onNavigateToRoute: (String) -> Unit,
    popBack: () -> Boolean,
) {
    composable(
        WelcomeSections.WELCOME.route,
        enterTransition = { slideIntoContainer() },
        exitTransition = { fadeOut() }) {

        BackHandler(true) {
            // pass
        }

        WelcomePage(
            onNavigateToRoute = onNavigateToRoute,
            popBack = popBack
        )
    }
    composable(
        WelcomeSections.QUESTIONNAIRE.route,
        enterTransition = { slideIntoContainer() },
        exitTransition = { fadeOut() }) {
        BackHandler(true) {
            // pass
        }

        Questionnaire(popBack = popBack, onNavigateToRoute = onNavigateToRoute)
    }
}


@Composable
fun WelcomePage(
    onNavigateToRoute: (String) -> Unit,
    popBack: () -> Boolean,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Eat2FitSurface(
        elevation = elevation,
        modifier = modifier
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
        ) {

            Image(
                modifier = Modifier
//                    .fillMaxSize()
                    .size(width = 300.dp, height = 300.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Image",
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = "Welcome to your",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1A1B1C),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = "Eat2Fit",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF53B725),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "Dietary Journey",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.karla_bold)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF53B725),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.height(48.dp))

            Eat2FitButton(
                modifier = Modifier
                    .shadow(
                        elevation = 22.dp,
                        spotColor = Color(0x4D95ADFE),
                        ambientColor = Color(0x4D95ADFE)
                    ),
                onClick = { onNavigateToRoute(WelcomeSections.QUESTIONNAIRE.route) }) {
                Column(
                    Modifier.size(width = 315.dp, height = 60.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp, alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        fontSize = 20.sp,
                        text = "Get Started",
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }

        }
    }
}

