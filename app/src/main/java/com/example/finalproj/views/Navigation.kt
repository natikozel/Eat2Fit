package com.example.finalproj.views

import androidx.activity.compose.BackHandler
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
import com.example.finalproj.util.Destinations.BARCODE_KEY
import com.example.finalproj.util.Destinations.INFO_PAGE_KEY
import com.example.finalproj.util.Destinations.IS_CORRECT_KEY
import com.example.finalproj.util.Destinations.LANDING
import com.example.finalproj.util.Destinations.PROFILE
import com.example.finalproj.util.Destinations.RECIPE_URI_KEY
import com.example.finalproj.util.Destinations.WELCOME
import com.example.finalproj.util.InformationPageManager
import com.example.finalproj.util.rememberEat2FitNavController


@Composable
fun Navigation() {
    val eat2FitNavController = rememberEat2FitNavController()

    NavHost(
        navController = eat2FitNavController.navController,
        startDestination = LANDING
    ) {
        eat2FitNavGraph(
            popBack = eat2FitNavController::popBack,
            onNavigateToRoute = eat2FitNavController::navigate,
            navigateAndClear = eat2FitNavController::navigateAndClearStack,
        )
    }
}


private fun NavGraphBuilder.eat2FitNavGraph(
    popBack: () -> Boolean,
    onNavigateToRoute: (String) -> Unit,
    navigateAndClear: (String) -> Unit
) {

    navigation(
        route = LANDING,
        startDestination = LandingSections.MAIN.route // landing
    ) {
        addLandingGraph(onNavigateToRoute, popBack, navigateAndClear)
    }

    navigation(
        route = PROFILE,
        startDestination = AppSections.PROFILE.route // profile
//        startDestination = "${AppSections.HOME.route}/quiz/question" // profile
    ) {
        addAppGraph(onNavigateToRoute, popBack, navigateAndClear)
    }

    navigation(
        route = WELCOME,
        startDestination = WelcomeSections.WELCOME.route // welcome
    ) {
        addWelcomeGraph(onNavigateToRoute, popBack)
    }

    composable(
        "${AppSections.BARCODE_SCAN.route}/{${BARCODE_KEY}}",
        arguments = listOf(navArgument(BARCODE_KEY) { type = NavType.StringType })
    ) { backStackEntry ->
        BackHandler {
            navigateToProfile(navigateAndClear)
        }
        val arguments = requireNotNull(backStackEntry.arguments)
        val barcode = arguments.getString(BARCODE_KEY)
        FoodDetailsScreen(barcode!!, navigateAndClear = navigateAndClear)
    }
    composable(
        "${AppSections.SEARCH.route}/{${RECIPE_URI_KEY}}",
        arguments = listOf(navArgument(RECIPE_URI_KEY) { type = NavType.StringType })
    ) { backStackEntry ->
        BackHandler {
            // pass
        }
        val arguments = requireNotNull(backStackEntry.arguments)
        val recipeUri = arguments.getString(RECIPE_URI_KEY)
        RecipeDetail(
            recipeUri!!,
            popBack,
            navigateAndClear
        )
    }
    composable(
        "${AppSections.HOME.route}/{${INFO_PAGE_KEY}}",
        arguments = listOf(navArgument(INFO_PAGE_KEY) { type = NavType.StringType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val infoId = arguments.getString(INFO_PAGE_KEY)
        val page = InformationPageManager.informationPages.first { it.infoPageId == infoId }
        InformationPageScreen(page, popBack)
    }
    composable(
        "${AppSections.HOME.route}/quiz/result/{${IS_CORRECT_KEY}}",
        enterTransition = { slideIntoContainer() },
    ) { backStackEntry ->
        val isCorrect = backStackEntry.arguments?.getString("isCorrect")?.toBoolean() ?: false
        QuizResultScreen(
            isCorrect = isCorrect,
            onPlayAgain = { onNavigateToRoute("${AppSections.HOME.route}/quiz/question") },
            navigateAndClear
        )
    }

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

