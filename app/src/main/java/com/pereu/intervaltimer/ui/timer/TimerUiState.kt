package com.pereu.intervaltimer.ui.timer

import androidx.annotation.StringRes
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.Orange
import com.pereu.intervaltimer.ui.theme.Primary
import com.pereu.intervaltimer.ui.theme.PrimaryLight
import com.pereu.intervaltimer.ui.theme.Secondary
import com.pereu.intervaltimer.ui.theme.Surface
import com.pereu.intervaltimer.ui.theme.TextPrimary
import com.pereu.intervaltimer.ui.timer.screen.TimerCardState
import com.pereu.intervaltimer.ui.timer.screen.TimerTopBarState

enum class TimerStatus(
    val accentColor: Color,
    val bgColor: Color,
    @get:StringRes val statusRes: Int,
    @param:StringRes val subtitleRes: Int? = null
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
        statusRes = R.string.timer_status_completed,
        subtitleRes = R.string.timer_status_completed_subtitle
    )
}

@Immutable
data class TimerUiState(
    val topBarState: TimerTopBarState = TimerTopBarState(),
    val timerCardState: TimerCardState = TimerCardState(),
    val intervals: List<IntervalUiState> = emptyList(),
    val currentIntervalIndex: Int = 0,
    val remainingIntervalTime: Int = 0,
    val totalTime: Int = 0,
    val elapsedTime: Int = 0,
    val status: TimerStatus = TimerStatus.Idle
)

@Immutable
data class IntervalUiState(
    val title: String,
    val time: Int,
    val timeFormatted: String,
    val progress: Float = 0f,
    val status: IntervalStatus = IntervalStatus.Pending
)

enum class IntervalStatus {
    Pending, Active, Completed
}
