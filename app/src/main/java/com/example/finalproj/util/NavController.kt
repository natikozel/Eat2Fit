package com.example.finalproj.util

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
    const val LANDING = "landing"
    const val WELCOME = "welcome"
    const val PROFILE = "app"
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

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

//    fun navigateToMealDetail(mealId: Long, from: NavBackStackEntry) {
//        // In order to discard duplicated navigation events, we check the Lifecycle
//        if (from.lifecycleIsResumed()) {
//            navController.navigate("${Destinations.MEAL_DETAIL_ROUTE}/$mealId")
//        }
//    }
}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
