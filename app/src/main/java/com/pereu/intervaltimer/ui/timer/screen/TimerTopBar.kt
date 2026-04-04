package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.components.BackButton
import com.pereu.intervaltimer.ui.theme.*
import com.pereu.intervaltimer.ui.timer.TimerStatus

@Composable
fun TimerTopBar(
    title: String,
    status: TimerStatus,
    elapsedTimeFormatted: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.xl2, vertical = Spacing.l),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BackButton(onClick = onBack)

        Text(
            text = title,
            style = TitleStyle,
            color = TextPrimary,
            modifier = Modifier.weight(1f).padding(horizontal = Spacing.m),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        when (status) {
            TimerStatus.Idle -> Text(
                text = elapsedTimeFormatted,
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
                Text(text = elapsedTimeFormatted, style = MonoStyle, color = Primary)
            }
            TimerStatus.Paused -> Text(
                text = stringResource(R.string.timer_topbar_paused),
                style = StateStyle,
                color = Orange
            )
            TimerStatus.Completed -> Text(
                text = stringResource(R.string.timer_topbar_completed),
                style = StateStyle,
                color = Secondary
            )
        }
    }
}

@Preview(showBackground = true, name = "Running")
@Composable
private fun TimerTopBarRunningPreview() {
    IntervalTimerTheme {
        TimerTopBar(
            title = "Тренировка 7",
            status = TimerStatus.Running,
            elapsedTimeFormatted = "12:18",
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Paused")
@Composable
private fun TimerTopBarPausedPreview() {
    IntervalTimerTheme {
        TimerTopBar(
            title = "Тренировка 7",
            status = TimerStatus.Paused,
            elapsedTimeFormatted = "12:18",
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Completed")
@Composable
private fun TimerTopBarCompletedPreview() {
    IntervalTimerTheme {
        TimerTopBar(
            title = "Тренировка 7",
            status = TimerStatus.Completed,
            elapsedTimeFormatted = "15:00",
            onBack = {}
        )
    }
}