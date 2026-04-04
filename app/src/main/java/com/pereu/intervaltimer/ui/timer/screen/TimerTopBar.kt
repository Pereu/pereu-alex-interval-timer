package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.components.BackButton
import com.pereu.intervaltimer.ui.theme.*
import com.pereu.intervaltimer.ui.timer.TimerStatus

@Immutable
data class TimerTopBarState(
    val title: String = "",
    val status: TimerStatus = TimerStatus.Idle,
    val elapsedTimeFormatted: String = ""
)

@Composable
fun TimerTopBar(
    modifier: Modifier = Modifier,
    state: TimerTopBarState,
    onBack: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.xl2, vertical = Spacing.l),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BackButton(onClick = onBack)

        Text(
            text = state.title,
            style = TitleStyle,
            color = TextPrimary,
            modifier = Modifier.weight(1f).padding(horizontal = Spacing.m),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        when (state.status) {
            TimerStatus.Idle -> Text(
                text = state.elapsedTimeFormatted,
                style = MonoStyle,
                color = TextSecondary
            )
            TimerStatus.Running -> Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Box(
                    modifier = Modifier
                        .size(Spacing.s)
                        .background(Primary, shape = androidx.compose.foundation.shape.CircleShape)
                )
                Text(text = state.elapsedTimeFormatted, style = MonoStyle, color = Primary)
            }
            TimerStatus.Paused -> Text(
                text = stringResource(R.string.timer_topbar_paused),
                style = StateStyle,
                color = state.status.accentColor
            )
            TimerStatus.Completed -> Text(
                text = stringResource(R.string.timer_topbar_completed),
                style = StateStyle,
                color = state.status.accentColor
            )
        }
    }
}

@Preview(showBackground = true, name = "Running")
@Composable
private fun TimerTopBarRunningPreview() {
    IntervalTimerTheme {
        TimerTopBar(
            state = TimerTopBarState(
                title = "Тренировка 7",
                status = TimerStatus.Running,
                elapsedTimeFormatted = "12:18",
            ),
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Paused")
@Composable
private fun TimerTopBarPausedPreview() {
    IntervalTimerTheme {
        TimerTopBar(
            state = TimerTopBarState(
                title = "Тренировка 7",
                status = TimerStatus.Paused,
                elapsedTimeFormatted = "12:18",
            ),
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Completed")
@Composable
private fun TimerTopBarCompletedPreview() {
    IntervalTimerTheme {
        TimerTopBar(
            state = TimerTopBarState(
                title = "Тренировка 7",
                status = TimerStatus.Completed,
                elapsedTimeFormatted = "15:00",
            ),
            onBack = {}
        )
    }
}