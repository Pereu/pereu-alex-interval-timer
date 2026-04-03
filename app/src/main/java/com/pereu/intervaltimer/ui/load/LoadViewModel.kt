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
            is LoadIntent.IdChanged -> _state.update { it.copy(timerId = intent.id) }
            is LoadIntent.LoadClicked -> loadTimer(_state.value.timerId)
        }
    }

    private fun loadTimer(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(resource = Resource.Loading) }
            val result = getTimerUseCase(id)
            _state.update { it.copy(resource = result) }
        }
    }
}