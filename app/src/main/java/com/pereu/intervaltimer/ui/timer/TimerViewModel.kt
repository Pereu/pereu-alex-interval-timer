package com.pereu.intervaltimer.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereu.intervaltimer.ui.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val mapper: TimerUiStateMapper
) : ViewModel() {

    private val _state = MutableStateFlow(TimerUiState())
    val state: StateFlow<TimerUiState> = _state

    private var timerJob: Job? = null

    init {
        sharedViewModel.timer.value?.let { timer ->
            _state.update { mapper.toInitialUiState(timer) }
        }
    }

    fun handleIntent(intent: TimerIntent) {
        when (intent) {
            is TimerIntent.Start -> start()
            is TimerIntent.Pause -> pause()
            is TimerIntent.Resume -> start()
            is TimerIntent.Reset -> reset()
        }
    }

    private fun start() {
        timerJob?.cancel()
        _state.update { it.copy(status = TimerStatus.Running) }
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                tick()
            }
        }
    }

    private fun pause() {
        timerJob?.cancel()
        _state.update { it.copy(status = TimerStatus.Paused) }
    }

    private fun reset() {
        timerJob?.cancel()
        sharedViewModel.timer.value?.let { timer ->
            _state.update { mapper.toInitialUiState(timer) }
        }
    }

    private fun tick() {
        val current = _state.value
        val newRemaining = current.remainingIntervalTime - 1
        val newElapsed = current.elapsedTime + 1

        if (newRemaining <= 0) {
            moveToNextInterval(newElapsed)
        } else {
            _state.update {
                it.copy(
                    remainingIntervalTime = newRemaining,
                    elapsedTime = newElapsed
                )
            }
        }
    }

    private fun moveToNextInterval(elapsedTime: Int) {
        val current = _state.value
        val nextIndex = current.currentIntervalIndex + 1

        if (nextIndex >= current.intervals.size) {
            timerJob?.cancel()
            _state.update {
                it.copy(
                    status = TimerStatus.Completed,
                    elapsedTime = elapsedTime,
                    remainingIntervalTime = 0,
                    intervals = it.intervals.map { interval ->
                        interval.copy(status = IntervalStatus.Completed)
                    }
                )
            }
            return
        }

        _state.update {
            it.copy(
                elapsedTime = elapsedTime,
                currentIntervalIndex = nextIndex,
                remainingIntervalTime = it.intervals[nextIndex].time,
                intervals = it.intervals.mapIndexed { index, interval ->
                    interval.copy(
                        status = when {
                            index < nextIndex -> IntervalStatus.Completed
                            index == nextIndex -> IntervalStatus.Active
                            else -> IntervalStatus.Pending
                        }
                    )
                }
            )
        }
    }
}