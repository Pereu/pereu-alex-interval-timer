package com.pereu.intervaltimer.ui.timer

import androidx.compose.runtime.Immutable

enum class TimerStatus {
    Idle, Running, Paused, Completed
}

@Immutable
data class TimerUiState(
    val title: String = "",
    val totalTime: Int = 0,
    val totalTimeFormatted: String = "",
    val elapsedTime: Int = 0,
    val status: TimerStatus = TimerStatus.Idle,
    val intervals: List<IntervalUiState> = emptyList(),
    val currentIntervalIndex: Int = 0,
    val remainingIntervalTime: Int = 0
)

@Immutable
data class IntervalUiState(
    val title: String,
    val time: Int,
    val timeFormatted: String,
    val status: IntervalStatus = IntervalStatus.Pending
)

enum class IntervalStatus {
    Pending, Active, Completed
}
