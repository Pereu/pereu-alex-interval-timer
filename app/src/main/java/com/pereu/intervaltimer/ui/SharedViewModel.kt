package com.pereu.intervaltimer.ui

import androidx.lifecycle.ViewModel
import com.pereu.intervaltimer.domain.model.TimerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _timer = MutableStateFlow<TimerModel?>(null)
    val timer: StateFlow<TimerModel?> = _timer

    fun setTimer(timer: TimerModel) {
        _timer.value = timer
    }
}