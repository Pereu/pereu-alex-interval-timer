package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.components.GhostButton
import com.pereu.intervaltimer.ui.components.GhostButtonState
import com.pereu.intervaltimer.ui.components.PrimaryButton
import com.pereu.intervaltimer.ui.components.PrimaryButtonState
import com.pereu.intervaltimer.ui.theme.DisabledText
import com.pereu.intervaltimer.ui.theme.IntervalTimerTheme
import com.pereu.intervaltimer.ui.theme.Secondary
import com.pereu.intervaltimer.ui.theme.Spacing
import com.pereu.intervaltimer.ui.theme.TextPrimary
import com.pereu.intervaltimer.ui.timer.TimerIntent
import com.pereu.intervaltimer.ui.timer.TimerStatus

@Composable
fun TimerBottomButtons(
    status: TimerStatus,
    onIntent: (TimerIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.xl2),
        verticalArrangement = Arrangement.spacedBy(Spacing.s)
    ) {

        when (status) {
            TimerStatus.Idle -> {
                PrimaryButton(
                    state = PrimaryButtonState(
                        titleRes = R.string.timer_btn_start,
                        icon = Icons.Default.PlayArrow
                    ),
                    onClick = { onIntent(TimerIntent.Start) }
                )
            }
            TimerStatus.Running -> {
                PrimaryButton(
                    state = PrimaryButtonState(
                        titleRes = R.string.timer_btn_pause,
                        icon = Icons.Default.Pause
                    ),
                    onClick = { onIntent(TimerIntent.Pause) }
                )
                GhostButton(
                    state = GhostButtonState(titleRes = R.string.timer_btn_reset),
                    onClick = { onIntent(TimerIntent.Reset) }
                )
            }
            TimerStatus.Paused -> {
                PrimaryButton(
                    state = PrimaryButtonState(
                        titleRes = R.string.timer_btn_resume,
                        icon = Icons.Default.PlayArrow
                    ),
                    onClick = { onIntent(TimerIntent.Resume) }
                )
                GhostButton(
                    state = GhostButtonState(titleRes = R.string.timer_btn_reset),
                    onClick = { onIntent(TimerIntent.Reset) }
                )
            }

            TimerStatus.Completed -> {
                PrimaryButton(
                    state = PrimaryButtonState(
                        titleRes = R.string.timer_btn_restart,
                        containerColor = Secondary
                    ),
                    onClick = { onIntent(TimerIntent.Reset) }
                )

                GhostButton(
                    state = GhostButtonState(
                        titleRes = R.string.timer_btn_new,
                        borderColor = DisabledText,
                        textColor = TextPrimary
                    ),
                    onClick = { onIntent(TimerIntent.NewWorkout) }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Idle")
@Composable
private fun TimerBottomButtonsIdlePreview() {
    IntervalTimerTheme {
        TimerBottomButtons(status = TimerStatus.Idle, onIntent = {})
    }
}

@Preview(showBackground = true, name = "Running")
@Composable
private fun TimerBottomButtonsRunningPreview() {
    IntervalTimerTheme {
        TimerBottomButtons(status = TimerStatus.Running, onIntent = {})
    }
}

@Preview(showBackground = true, name = "Paused")
@Composable
private fun TimerBottomButtonsPausedPreview() {
    IntervalTimerTheme {
        TimerBottomButtons(status = TimerStatus.Paused, onIntent = {})
    }
}

@Preview(showBackground = true, name = "Completed")
@Composable
private fun TimerBottomButtonsCompletedPreview() {
    IntervalTimerTheme {
        TimerBottomButtons(status = TimerStatus.Completed, onIntent = {})
    }
}