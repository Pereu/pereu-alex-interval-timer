package com.pereu.intervaltimer.ui.load

import com.pereu.intervaltimer.domain.model.TimerModel

interface LoadSideEffect {
    data class NavigateToTimer(val timer: TimerModel) : LoadSideEffect
}