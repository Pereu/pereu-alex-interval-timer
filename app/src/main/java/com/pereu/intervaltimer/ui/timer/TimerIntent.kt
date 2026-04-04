package com.pereu.intervaltimer.ui.timer

sealed interface TimerIntent {
    object Start : TimerIntent
    object Pause : TimerIntent
    object Resume : TimerIntent
    object Reset : TimerIntent
    object NewWorkout : TimerIntent
}