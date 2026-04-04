package com.pereu.intervaltimer.ui.timer

import androidx.lifecycle.ViewModel
import com.pereu.intervaltimer.ui.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val mapper: TimerUiStateMapper
) : ViewModel() {

    private val _state = MutableStateFlow(TimerUiState())
    val state: StateFlow<TimerUiState> = _state

    init {
        sharedViewModel.timer.value?.let { timer ->
            _state.update { mapper.toInitialUiState(timer) }
        }
    }
}