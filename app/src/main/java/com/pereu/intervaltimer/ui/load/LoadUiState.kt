package com.pereu.intervaltimer.ui.load

import androidx.compose.runtime.Immutable
import com.pereu.intervaltimer.R

import com.pereu.intervaltimer.domain.model.Timer
import com.pereu.intervaltimer.ui.components.LoadButtonState
import com.pereu.intervaltimer.ui.components.OutlinedTextFieldState
import com.pereu.intervaltimer.util.Resource

@Immutable
data class LoadUiState(
    val resource: Resource<Timer>? = null,
    val btnState: LoadButtonState = LoadButtonState(titleRes = R.string.load_button_load),
    val timerIdInputState: OutlinedTextFieldState = OutlinedTextFieldState(
        value = "68",
        labelRes = R.string.label_timer_id,
        errorRes = R.string.error_timer_not_found
    )
)