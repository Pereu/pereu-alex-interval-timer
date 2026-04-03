package com.pereu.intervaltimer.ui.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereu.intervaltimer.domain.usecase.GetTimerUseCase
import com.pereu.intervaltimer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadViewModel @Inject constructor(
    private val getTimerUseCase: GetTimerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoadUiState())
    val state: StateFlow<LoadUiState> = _state

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

            is LoadIntent.LoadClicked -> loadTimer(_state.value.timerIdInputState.value)
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
                            resource = result,
                            btnState = it.btnState.copy(isLoading = false)
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            resource = result,
                            btnState = it.btnState.copy(isLoading = false),
                            timerIdInputState = it.timerIdInputState.copy(isError = true)
                        )
                    }
                }
            }
        }
    }
}