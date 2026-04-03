package com.pereu.intervaltimer.ui.load

import androidx.compose.runtime.Immutable
import com.pereu.intervaltimer.domain.model.Timer
import com.pereu.intervaltimer.util.Resource

@Immutable
data class LoadUiState(
    val timerId: String = "68",
    val resource: Resource<Timer>? = null
)