package com.pereu.intervaltimer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pereu.intervaltimer.ui.SharedViewModel
import com.pereu.intervaltimer.ui.load.LoadScreen
import com.pereu.intervaltimer.ui.timer.TimerScreen

sealed class Screen(val route: String) {
    object Load : Screen("load")
    object Timer : Screen("timer")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Load.route
    ) {
        composable(Screen.Load.route) {
            LoadScreen(
                onTimerLoaded = { timer ->
                    sharedViewModel.setTimer(timer)
                    navController.navigate(Screen.Timer.route)
                }
            )
        }
        composable(route = Screen.Timer.route) {
            TimerScreen(
                sharedViewModel = sharedViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}