package com.pereu.intervaltimer.ui.load

import androidx.compose.runtime.Immutable
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.components.PrimaryButtonState
import com.pereu.intervaltimer.ui.components.OutlinedTextFieldState

@Immutable
data class LoadUiState(
    val btnState: PrimaryButtonState = PrimaryButtonState(titleRes = R.string.load_button_load),
    val timerIdInputState: OutlinedTextFieldState = OutlinedTextFieldState(
        value = "68",
        labelRes = R.string.label_timer_id,
        errorRes = R.string.error_timer_not_found
    )
)