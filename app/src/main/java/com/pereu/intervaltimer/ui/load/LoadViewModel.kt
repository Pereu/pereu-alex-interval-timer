package com.pereu.intervaltimer.ui.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereu.intervaltimer.domain.model.IntervalModel
import com.pereu.intervaltimer.domain.model.TimerModel
import com.pereu.intervaltimer.domain.usecase.GetTimerUseCase
import com.pereu.intervaltimer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadViewModel @Inject constructor(
    private val getTimerUseCase: GetTimerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoadUiState())
    val state: StateFlow<LoadUiState> = _state

    private val _sideEffect = MutableSharedFlow<LoadSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun handleIntent(intent: LoadIntent) {
        when (intent) {
            is LoadIntent.IdChanged -> _state.update {
                it.copy(
                    timerIdInputState = it.timerIdInputState.copy(
                        value = intent.value,
                        isError = false
                    )
                )
            }

            is LoadIntent.LoadClicked -> loadTimer(id =_state.value.timerIdInputState.value)
        }
    }

    private fun loadTimer(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(btnState = it.btnState.copy(isLoading = true))
            }

            when (val result = getTimerUseCase(id)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            btnState = it.btnState.copy(isLoading = false),
                        )
                    }
                    _sideEffect.emit(LoadSideEffect.NavigateToTimer(result.data))
                }
                is Resource.Error -> {
                    val mockTimer = TimerModel(
                        id = 68,
                        title = "Тренировка 7",
                        totalTime = 900,
                        intervals = listOf(
                            IntervalModel(title = "Ходьба в среднем темпе", time = 300),
                            IntervalModel(title = "Ходьба в интенсивном темпе", time = 300),
                            IntervalModel(title = "Ходьба в среднем темпе", time = 120),
                            IntervalModel(title = "Медленный бег", time = 30)
                        )
                    )
                    _sideEffect.emit(LoadSideEffect.NavigateToTimer(mockTimer))
                    _state.update { it.copy(btnState = it.btnState.copy(isLoading = false)) }

//                    _state.update {
//                        it.copy(
//                            btnState = it.btnState.copy(isLoading = false),
//                            timerIdInputState = it.timerIdInputState.copy(isError = true)
//                        )
//                    }
                }
            }
        }
    }
}