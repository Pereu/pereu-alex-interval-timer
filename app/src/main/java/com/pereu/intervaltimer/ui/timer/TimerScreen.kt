package com.pereu.intervaltimer.ui.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pereu.intervaltimer.ui.SharedViewModel

@Composable
fun TimerScreen(
    sharedViewModel: SharedViewModel,
    onBack: () -> Unit
) {
    val timer by sharedViewModel.timer.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("TimerScreen — ${timer?.title}")
    }
}