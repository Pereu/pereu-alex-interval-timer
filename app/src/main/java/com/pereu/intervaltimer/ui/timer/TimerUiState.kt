package com.pereu.intervaltimer.ui.timer

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.Orange
import com.pereu.intervaltimer.ui.theme.Primary
import com.pereu.intervaltimer.ui.theme.PrimaryLight
import com.pereu.intervaltimer.ui.theme.Secondary
import com.pereu.intervaltimer.ui.theme.Surface
import com.pereu.intervaltimer.ui.theme.TextPrimary

enum class TimerStatus(
    val accentColor: Color,
    val bgColor: Color,
    @get:StringRes val statusRes: Int
) {
    Idle(
        accentColor = TextPrimary,
        bgColor = Surface,
        statusRes = R.string.timer_status_ready
    ),
    Running(
        accentColor = Primary,
        bgColor = PrimaryLight,
        statusRes = R.string.timer_status_running
    ),
    Paused(
        accentColor = Orange,
        bgColor = Color(0x14E67E22),
        statusRes = R.string.timer_status_paused
    ),
    Completed(
        accentColor = Secondary,
        bgColor = Color(0x143B82F6),
        statusRes = R.string.timer_status_completed
    )
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
