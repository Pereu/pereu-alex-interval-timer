package com.pereu.intervaltimer.ui.timer

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.Orange
import com.pereu.intervaltimer.ui.theme.OrangeLight
import com.pereu.intervaltimer.ui.theme.Primary
import com.pereu.intervaltimer.ui.theme.PrimaryLight
import com.pereu.intervaltimer.ui.theme.Secondary
import com.pereu.intervaltimer.ui.theme.SecondaryLight
import com.pereu.intervaltimer.ui.theme.Surface
import com.pereu.intervaltimer.ui.theme.TextPrimary
import com.pereu.intervaltimer.ui.theme.TextSecondary
import com.pereu.intervaltimer.ui.timer.screen.TimerCardState
import com.pereu.intervaltimer.ui.timer.screen.TimerTopBarState

enum class TimerStatus(
    val accentColor: Color,
    val bgColor: Color,
    val titleColor: Color = TextSecondary,
    val subTitleColor: Color = TextPrimary,
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
        titleColor = Primary,
        bgColor = PrimaryLight,
        statusRes = R.string.timer_status_running
    ),
    Paused(
        accentColor = Orange,
        titleColor = Orange,
        bgColor = OrangeLight,
        statusRes = R.string.timer_status_paused
    ),
    Completed(
        accentColor = Secondary,
        titleColor = Secondary,
        subTitleColor = Secondary,
        bgColor = SecondaryLight,
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
