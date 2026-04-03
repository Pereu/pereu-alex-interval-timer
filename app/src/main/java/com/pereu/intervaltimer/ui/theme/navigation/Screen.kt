package com.pereu.intervaltimer.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Load : Screen("load")
    object Timer : Screen("timer/{timerId}") {
        fun createRoute(timerId: String) = "timer/$timerId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Load.route
    ) {
        composable(Screen.Load.route) {
            // LoadScreen()
        }
        composable(Screen.Timer.route) {
            // TimerScreen()
        }
    }
}