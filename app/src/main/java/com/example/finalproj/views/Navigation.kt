package com.example.finalproj.views

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.finalproj.util.Destinations.LANDING
import com.example.finalproj.util.Destinations.PROFILE
import com.example.finalproj.util.Destinations.WELCOME
import com.example.finalproj.util.rememberEat2FitNavController


@Composable
fun Navigation() {
    val eat2FitNavController = rememberEat2FitNavController()

    NavHost(
        navController = eat2FitNavController.navController,
        startDestination = LANDING
    ) {
        eat2FitNavGraph(
            popBack = { eat2FitNavController.navController.popBackStack() },
//            onMealSelected = eat2FitNavController::navigateToMealDetail,
            upPress = eat2FitNavController::upPress,
            onNavigateToRoute = eat2FitNavController::navigateToBottomBarRoute
        )
    }
}


private fun NavGraphBuilder.eat2FitNavGraph(
    popBack: () -> Boolean,
//    onMealSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit
) {

    navigation(
        route = LANDING,
        startDestination = LandingSections.LANDING.route
    ) {
        addLandingGraph(onNavigateToRoute, popBack)
    }

    navigation(
        route = PROFILE,
        startDestination = AppSections.PROFILE.route
    ) {
        addAppGraph(onNavigateToRoute, popBack)
    }

    navigation(
        route = WELCOME,
        startDestination = WelcomeSections.WELCOME.route
    ) {
        addWelcomeGraph(onNavigateToRoute, popBack)
    }

//    composable(
//        "${MEAL_DETAIL_ROUTE}/{${MEAL_ID_KEY}}",
//        arguments = listOf(navArgument(MEAL_ID_KEY) { type = NavType.LongType })
//    ) { backStackEntry ->
//        val arguments = requireNotNull(backStackEntry.arguments)
//        val mealId = arguments.getLong(MEAL_ID_KEY)
////        MealDetails(mealId, onNavigateToRoute)
//    }
//    composable(
//        route = "ForgotPassword",
//        enterTransition = { slideIntoContainer() },
//        exitTransition = { FadeOut() })
//    { ForgotPassword(navController) }
//    composable(
//        route = "ResetPassword",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = {FadeOut() }) { ResetPasswordScreen(navController) }
//    composable(
//        route = "Signup",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = { FadeOut() }) { Signup(navController) }
//    composable(
//        route = "Homepage",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = { FadeOut() }) { ProfilePage(navController) }
//    composable(
//        route = "Welcome",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = { FadeOut() }) { WelcomePage(navController) }
//    composable(
//        route = "Questionnaire",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = { FadeOut() }) { Questionnaire(navController) }
//    composable(
//        route = "Homepage/Edit",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = { FadeOut() }) { EditProfile(navController) }
//    composable(
//        route = "Home",
//        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//        exitTransition = { FadeOut() }) { ProfilePage(navController) }

}

fun fadeIn() = fadeIn(tween(700))

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutOfContainer() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Start,
        tween(700)
    )

fun fadeOut() = fadeOut(tween(700))

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideIntoContainer() =
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))

