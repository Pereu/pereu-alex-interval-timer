package com.pereu.intervaltimer.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereu.intervaltimer.domain.model.TimerModel
import com.pereu.intervaltimer.ui.SharedViewModel
import com.pereu.intervaltimer.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val mapper: TimerUiStateMapper,
    private val soundManager: SoundManager
) : ViewModel() {

    private val _state = MutableStateFlow(TimerUiState())
    val state: StateFlow<TimerUiState> = _state

    private val _sideEffect = MutableSharedFlow<TimerSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var timerJob: Job? = null
    private var timer: TimerModel? = null

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }

    fun init(timer: TimerModel) {
        if (this.timer == null) {
            this.timer = timer
            _state.update { mapper.toInitialUiState(timer) }
        }
    }

    fun handleIntent(intent: TimerIntent) {
        when (intent) {
            is TimerIntent.Start -> start()
            is TimerIntent.Pause -> pause()
            is TimerIntent.Resume -> start()
            is TimerIntent.Reset -> reset()
            is TimerIntent.NewWorkout -> navigateBack()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _sideEffect.emit(TimerSideEffect.NavigateBack)
        }
    }

    private fun start() {
        timerJob?.cancel()

        val isFirstStart = _state.value.status == TimerStatus.Idle

        _state.update {
            it.copy(
                status = TimerStatus.Running,
                topBarState = it.topBarState.copy(status = TimerStatus.Running),
                timerCardState = it.timerCardState.copy(status = TimerStatus.Running)
            )
        }

        if (isFirstStart) soundManager.playStart()

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                tick()
            }
        }
    }

    private fun pause() {
        timerJob?.cancel()
        _state.update {
            it.copy(
                status = TimerStatus.Paused,
                topBarState = it.topBarState.copy(status = TimerStatus.Paused),
                timerCardState = it.timerCardState.copy(status = TimerStatus.Paused)
            )
        }
    }

    private fun reset() {
        timerJob?.cancel()
        val timer = timer ?: return
        _state.update {
            mapper.toInitialUiState(timer)
        }
    }

    private fun tick() {
        val current = _state.value
        val newRemaining = current.remainingIntervalTime - 1
        val newElapsed = current.elapsedTime + 1

        if (newRemaining <= 0) {
            moveToNextInterval(newElapsed)
        } else {
            val newProgress =
                1f - (newRemaining.toFloat() / (current.intervals.getOrNull(current.currentIntervalIndex)?.time
                    ?: 1))

            _state.update {
                it.copy(
                    remainingIntervalTime = newRemaining,
                    elapsedTime = newElapsed,
                    timerCardState = it.timerCardState.copy(
                        remainingTimeFormatted = newRemaining.toTimeFormatted(),
                        elapsedTimeFormatted = newElapsed.toTimeFormatted(),
                        elapsedTime = newElapsed,
                        progress = newProgress
                    ),
                    intervals = it.intervals.mapIndexed { index, interval ->
                        if (index == it.currentIntervalIndex) {
                            interval.copy(
                                progress = 1f - (newRemaining.toFloat() / interval.time)
                            )
                        } else interval
                    }
                )
            }
        }
    }

    private fun moveToNextInterval(elapsedTime: Int) {
        val current = _state.value
        val nextIndex = current.currentIntervalIndex + 1

        if (nextIndex >= current.intervals.size) {
            viewModelScope.launch {
                soundManager.playFinish()
                delay(350)
                soundManager.playFinish()
            }

            timerJob?.cancel()
            _state.update {
                it.copy(
                    status = TimerStatus.Completed,
                    topBarState = it.topBarState.copy(status = TimerStatus.Completed),
                    timerCardState = it.timerCardState.copy(
                        status = TimerStatus.Completed,
                        remainingTimeFormatted = "0:00",
                        elapsedTimeFormatted = elapsedTime.toTimeFormatted(),
                        elapsedTime = elapsedTime,
                        progress = 1f
                    ),
                    elapsedTime = elapsedTime,
                    remainingIntervalTime = 0,
                    intervals = it.intervals.map { interval ->
                        interval.copy(status = IntervalStatus.Completed, progress = 1f)
                    }
                )
            }
            return
        }

        soundManager.playIntervalChange()

        _state.update {
            it.copy(
                elapsedTime = elapsedTime,
                currentIntervalIndex = nextIndex,
                remainingIntervalTime = it.intervals[nextIndex].time,
                timerCardState = it.timerCardState.copy(
                    status = TimerStatus.Running,
                    intervalTitle = it.intervals[nextIndex].title,
                    remainingTimeFormatted = it.intervals[nextIndex].time.toTimeFormatted(),
                    elapsedTimeFormatted = elapsedTime.toTimeFormatted(),
                    elapsedTime = elapsedTime,
                    progress = 0f
                ),
                intervals = it.intervals.mapIndexed { index, interval ->
                    interval.copy(
                        status = when {
                            index < nextIndex -> IntervalStatus.Completed
                            index == nextIndex -> IntervalStatus.Active
                            else -> IntervalStatus.Pending
                        },
                        progress = when {
                            index < nextIndex -> 1f
                            else -> 0f
                        }
                    )
                }
            )
        }
    }
}