package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.*
import com.pereu.intervaltimer.ui.timer.TimerStatus

@Immutable
data class TimerCardState(
    val status: TimerStatus = TimerStatus.Idle,
    val intervalTitle: String = "",
    val remainingTimeFormatted: String = "0:00",
    val elapsedTime: Int = 0,
    val intervalTotalTime: Int = 0,
    val totalTimeFormatted: String = "0:00",
    val elapsedTimeFormatted: String = "0:00",
    val progress: Float = 0f
)

@Composable
fun TimerCard(state: TimerCardState) {

//    val progress = when (state.status) {
//        TimerStatus.Idle -> 0f
//        TimerStatus.Completed -> 1f
//        else -> if (state.intervalTotalTime > 0)
//            (state.intervalTotalTime - state.elapsedTime).toFloat() / state.intervalTotalTime
//        else 0f
//    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.timerCard))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(state.status.bgColor, Surface)
                ),
                shape = RoundedCornerShape(Radius.timerCard)
            )
            .border(
                1.5.dp,
                state.status.accentColor.copy(alpha = 0.3f),
                RoundedCornerShape(Radius.timerCard)
            )
            .padding(Spacing.xl2)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(state.status.statusRes),
                style = StateStyle,
                color = state.status.accentColor
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            Text(
                text = if (state.status.subtitleRes != null)
                    stringResource(state.status.subtitleRes)
                else
                    state.intervalTitle,
                style = BodyStyle,
                color = state.status.accentColor
            )

            Spacer(modifier = Modifier.height(Spacing.l))

            Text(
                text = state.remainingTimeFormatted,
                style = TimerDisplayStyle,
                color = state.status.accentColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.s))

            Text(
                text = stringResource(
                    R.string.timer_progress_label,
                    state.elapsedTimeFormatted,
                    state.totalTimeFormatted
                ),
                style = CaptionStyle,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(Spacing.m))

            LinearProgressIndicator(
                progress = { state.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(Radius.progressBar)),
                color = state.status.accentColor,
                trackColor = Border
            )
        }
    }
}

private val baseCardState = TimerCardState(
    status = TimerStatus.Idle,
    intervalTitle = "Ходьба в среднем темпе",
    remainingTimeFormatted = "15:00",
    elapsedTime = 0,
    intervalTotalTime = 300,
    totalTimeFormatted = "15:00",
    elapsedTimeFormatted = "0:00",
    progress = 0f
)

@Preview(showBackground = true, name = "Idle")
@Composable
private fun TimerCardIdlePreview() {
    IntervalTimerTheme { TimerCard(state = baseCardState) }
}

@Preview(showBackground = true, name = "Running")
@Composable
private fun TimerCardRunningPreview() {
    IntervalTimerTheme {
        TimerCard(
            state = baseCardState.copy(
                status = TimerStatus.Running,
                remainingTimeFormatted = "0:18",
                elapsedTime = 12,
                intervalTotalTime = 30,
                elapsedTimeFormatted = "12:18",
                progress = 0.4f
            )
        )
    }
}

@Preview(showBackground = true, name = "Paused")
@Composable
private fun TimerCardPausedPreview() {
    IntervalTimerTheme {
        TimerCard(
            state = baseCardState.copy(
                status = TimerStatus.Paused,
                remainingTimeFormatted = "0:18",
                elapsedTime = 12,
                intervalTotalTime = 30,
                elapsedTimeFormatted = "12:18",
                progress = 0.4f
            )
        )
    }
}
@Preview(showBackground = true, name = "Completed")
@Composable
private fun TimerCardCompletedPreview() {
    IntervalTimerTheme {
        TimerCard(
            state = baseCardState.copy(
                status = TimerStatus.Completed,
                remainingTimeFormatted = "0:00",
                elapsedTime = 900,
                intervalTotalTime = 300,
                elapsedTimeFormatted = "15:00",
                progress = 1f
            )
        )
    }
}
