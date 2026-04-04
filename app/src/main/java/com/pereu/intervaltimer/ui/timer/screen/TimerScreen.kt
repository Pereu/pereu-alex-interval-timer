package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.pereu.intervaltimer.ui.timer.toTimeFormatted

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
        state = state,
        onIntent = viewModel::handleIntent,
        onBack = onBack
    )
}

@Composable
private fun TimerScreenContent(
    state: TimerUiState,
    onIntent: (TimerIntent) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Spacing.xl2)
    ) {
        TimerTopBar(
            title = state.title,
            status = state.status,
            elapsedTimeFormatted = state.totalTimeFormatted,
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Spacing.xl2),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            TimerCard(
                state = TimerCardState(
                    status = state.status,
                    intervalTitle = state.intervals.getOrNull(state.currentIntervalIndex)?.title
                        ?: "",
                    remainingTimeFormatted = state.remainingIntervalTime.toTimeFormatted(),
                    elapsedTime = state.elapsedTime,
                    intervalTotalTime = state.intervals.getOrNull(state.currentIntervalIndex)?.time
                        ?: 0,
                    totalTimeFormatted = state.totalTimeFormatted,
                    elapsedTimeFormatted = state.elapsedTime.toTimeFormatted()
                )
            )

            IntervalsList(
                intervals = state.intervals,
                currentIndex = state.currentIntervalIndex,
                timerStatus = state.status
            )
        }

        Spacer(modifier = Modifier.height(Spacing.xl))

        TimerBottomButtons(
            status = state.status,
            onIntent = onIntent
        )
    }
}

private val previewTimerUiState = TimerUiState(
    title = "Тренировка 7",
    totalTime = 900,
    totalTimeFormatted = "15:00",
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
            state = previewTimerUiState,
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
            state = previewTimerUiState.copy(status = TimerStatus.Running),
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
            state = previewTimerUiState.copy(status = TimerStatus.Paused),
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
            state = previewTimerUiState.copy(
                status = TimerStatus.Completed,
                intervals = previewTimerUiState.intervals.map {
                    it.copy(status = IntervalStatus.Completed, progress = 1f)
                }
            ),
            onIntent = {},
            onBack = {}
        )
    }
}

