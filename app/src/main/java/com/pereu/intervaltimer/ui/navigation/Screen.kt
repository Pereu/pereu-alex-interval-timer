package com.pereu.intervaltimer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pereu.intervaltimer.ui.load.LoadScreen
import com.pereu.intervaltimer.ui.timer.TimerScreen

sealed class Screen(val route: String) {
    object Load : Screen("load")
    object Timer : Screen("timer/{timerId}") {
        fun createRoute(timerId: Int) = "timer/$timerId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Load.route
    ) {
        composable(Screen.Load.route) {
            LoadScreen(
                onTimerLoaded = { timer ->
                    navController.navigate(Screen.Timer.createRoute(timer.id))
                }
            )
        }
        composable(
            route = Screen.Timer.route,
            arguments = listOf(navArgument("timerId") { type = NavType.IntType })
        ) { backStackEntry ->
            TimerScreen(
                timerId = backStackEntry.arguments?.getInt("timerId") ?: 0,
                onBack = { navController.popBackStack() }
            )
        }
    }
}