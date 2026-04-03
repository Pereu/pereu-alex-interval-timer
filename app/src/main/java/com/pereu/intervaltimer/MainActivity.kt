package com.pereu.intervaltimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.pereu.intervaltimer.ui.theme.IntervalTimerTheme
import com.pereu.intervaltimer.ui.theme.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntervalTimerTheme {
                IntervalTimerTheme {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}