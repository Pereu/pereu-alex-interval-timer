package com.pereu.intervaltimer.ui.timer

sealed interface TimerSideEffect {
    object NavigateBack : TimerSideEffect
}