package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pereu.intervaltimer.ui.SharedViewModel
import com.pereu.intervaltimer.ui.theme.IntervalTimerTheme
import com.pereu.intervaltimer.ui.theme.Spacing
import com.pereu.intervaltimer.ui.timer.IntervalStatus
import com.pereu.intervaltimer.ui.timer.IntervalUiState
import com.pereu.intervaltimer.ui.timer.TimerIntent
import com.pereu.intervaltimer.ui.timer.TimerSideEffect
import com.pereu.intervaltimer.ui.timer.TimerStatus
import com.pereu.intervaltimer.ui.timer.TimerUiState
import com.pereu.intervaltimer.ui.timer.TimerViewModel

@Composable
fun TimerScreen(
    sharedViewModel: SharedViewModel,
    onBack: () -> Unit,
    onNewWorkout: () -> Unit,
    viewModel: TimerViewModel = hiltViewModel()
) {
    val timer by sharedViewModel.timer.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(timer) {
        timer?.let { viewModel.init(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is TimerSideEffect.NavigateBack -> onNewWorkout()
            }
        }
    }
    TimerScreenContent(
        uiState = state,
        onIntent = viewModel::handleIntent,
        onBack = onBack
    )
}

@Composable
private fun TimerScreenContent(
    uiState: TimerUiState,
    onIntent: (TimerIntent) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Spacing.xl2)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        TimerTopBar(
            state = uiState.topBarState,
            modifier = Modifier.statusBarsPadding(),
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Spacing.xl2),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            TimerCard(
                state = uiState.timerCardState
            )

            AnimatedVisibility(visible = uiState.status == TimerStatus.Completed) {
                TimerCompletedStats(
                    totalTimeFormatted = uiState.timerCardState.totalTimeFormatted,
                    intervalsCount = uiState.intervals.size
                )
            }

            IntervalsList(
                intervals = uiState.intervals,
                currentIndex = uiState.currentIntervalIndex,
                timerStatus = uiState.status
            )
        }

        Spacer(modifier = Modifier.height(Spacing.xl))

        TimerBottomButtons(
            status = uiState.status,
            onIntent = onIntent
        )
    }
}

private val previewTimerTopBarState =
    TimerTopBarState(title = "Тренировка 7", elapsedTimeFormatted = "15:00")

private val previewTimerCardState = TimerCardState(
    intervalTitle = "Ходьба в среднем темпе",
    remainingTimeFormatted = "15:00",
    elapsedTime = 0,
    intervalTotalTime = 300,
    totalTimeFormatted = "15:00",
    elapsedTimeFormatted = "0:00",
    progress = 0f
)

private val previewTimerUiState = TimerUiState(
    topBarState = previewTimerTopBarState,
    timerCardState = previewTimerCardState,
    totalTime = 900,
    elapsedTime = 0,
    status = TimerStatus.Idle,
    currentIntervalIndex = 0,
    remainingIntervalTime = 300,
    intervals = listOf(
        IntervalUiState(
            title = "Ходьба в среднем темпе",
            time = 300,
            timeFormatted = "5:00",
            status = IntervalStatus.Active
        ),
        IntervalUiState(
            title = "Ходьба в интенсивном темпе",
            time = 300,
            timeFormatted = "5:00",
            status = IntervalStatus.Pending
        ),
        IntervalUiState(
            title = "Ходьба в среднем темпе",
            time = 120,
            timeFormatted = "2:00",
            status = IntervalStatus.Pending
        ),
        IntervalUiState(
            title = "Медленный бег",
            time = 30,
            timeFormatted = "0:30",
            status = IntervalStatus.Pending
        )
    )
)

@Preview(showBackground = true, name = "Idle")
@Composable
private fun TimerScreenIdlePreview() {
    IntervalTimerTheme {
        TimerScreenContent(
            uiState = previewTimerUiState,
            onIntent = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Running")
@Composable
private fun TimerScreenRunningPreview() {
    IntervalTimerTheme {
        TimerScreenContent(
            uiState = previewTimerUiState.copy(
                status = TimerStatus.Running,
                topBarState = previewTimerTopBarState.copy(status = TimerStatus.Running),
                timerCardState = previewTimerCardState.copy(
                    status = TimerStatus.Running,
                    intervalTitle = "Медленный бег",
                    remainingTimeFormatted = "0:18",
                    elapsedTime = 12,
                    intervalTotalTime = 30,
                    elapsedTimeFormatted = "12:18",
                    progress = 0.4f
                )
            ),
            onIntent = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Paused")
@Composable
private fun TimerScreenPausedPreview() {
    IntervalTimerTheme {
        TimerScreenContent(
            uiState = previewTimerUiState.copy(
                status = TimerStatus.Paused,
                topBarState = previewTimerTopBarState.copy(status = TimerStatus.Paused),
                timerCardState = previewTimerCardState.copy(
                    status = TimerStatus.Paused,
                    intervalTitle = "Медленный бег",
                    remainingTimeFormatted = "0:18",
                    elapsedTime = 12,
                    intervalTotalTime = 30,
                    elapsedTimeFormatted = "12:18",
                    progress = 0.4f
                )
            ),
            onIntent = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Completed")
@Composable
private fun TimerScreenCompletedPreview() {
    IntervalTimerTheme {
        TimerScreenContent(
            uiState = previewTimerUiState.copy(
                status = TimerStatus.Completed,
                topBarState = previewTimerTopBarState.copy(status = TimerStatus.Completed),
                timerCardState = previewTimerCardState.copy(
                    status = TimerStatus.Completed,
                    remainingTimeFormatted = "0:00",
                    elapsedTime = 900,
                    intervalTotalTime = 300,
                    elapsedTimeFormatted = "15:00",
                    progress = 1f
                ),
                intervals = previewTimerUiState.intervals.map {
                    it.copy(status = IntervalStatus.Completed, progress = 1f)
                }
            ),
            onIntent = {},
            onBack = {}
        )
    }
}

