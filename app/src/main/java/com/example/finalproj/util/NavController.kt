package com.example.finalproj.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object Destinations {
    const val LANDING = "landing" // landing
    const val WELCOME = "welcome" // welcome
    const val PROFILE = "profile" // profile
    const val BARCODE_KEY = "barcode"
    const val RECIPE_URI_KEY = "recipeUri"
    const val INFO_PAGE_KEY = "infoPageId"
    const val IS_CORRECT_KEY = "isCorrect"
}

@Composable
fun rememberEat2FitNavController(
    navController: NavHostController = rememberNavController()
): Eat2FitNavController = remember(navController) {
    Eat2FitNavController(navController)
}

@Stable
class Eat2FitNavController(
    val navController: NavHostController,
) {
    private val currentRoute: String?
        get() = navController.currentDestination?.route


    @SuppressLint("ResourceType")
    fun navigateAndClearStack(route: String) {
        if (route != currentRoute) {

            navController.navigate(route) {
                navController.currentBackStackEntry?.destination?.route?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        }
    }


    fun navigate(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
            }
        }
    }


    fun popBack(): Boolean {
        return if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            navController.navigate(Destinations.PROFILE) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
            false
        }
    }
}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
