package com.pereu.intervaltimer.ui.load

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.domain.model.TimerModel
import com.pereu.intervaltimer.ui.components.LoadButton
import com.pereu.intervaltimer.ui.components.LoadButtonState
import com.pereu.intervaltimer.ui.components.OutLinedTextField
import com.pereu.intervaltimer.ui.components.OutlinedTextFieldState
import com.pereu.intervaltimer.ui.theme.BodyStyle
import com.pereu.intervaltimer.ui.theme.H1Style
import com.pereu.intervaltimer.ui.theme.IntervalTimerTheme
import com.pereu.intervaltimer.ui.theme.Primary
import com.pereu.intervaltimer.ui.theme.Size
import com.pereu.intervaltimer.ui.theme.Spacing
import com.pereu.intervaltimer.ui.theme.TextPrimary
import com.pereu.intervaltimer.ui.theme.TextSecondary

@Composable
fun LoadScreen(
    onTimerLoaded: (TimerModel) -> Unit,
    viewModel: LoadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is LoadSideEffect.NavigateToTimer -> onTimerLoaded(effect.timer)
            }
        }
    }

    LoadScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun LoadScreenContent(
    state: LoadUiState,
    onIntent: (LoadIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.xl2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Иконка
        Box(
            modifier = Modifier
                .size(Size.iconSize)
                .background(color = Primary, shape = MaterialTheme.shapes.large),
            contentAlignment = Alignment.Center
        ) {
            Text("🕐", style = MaterialTheme.typography.headlineLarge)
        }

        Spacer(modifier = Modifier.height(Spacing.xl))

        // Заголовок
        Text(
            text = stringResource(R.string.app_title),
            style = H1Style,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.s))

        Text(
            text = stringResource(R.string.app_subtitle),
            style = BodyStyle,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.xl2 + Spacing.s))

        OutLinedTextField(
            state = state.timerIdInputState,
            onValueChange = { onIntent(LoadIntent.IdChanged(it)) }
        )

        Spacer(modifier = Modifier.height(Spacing.l))

        LoadButton(
            state = state.btnState,
            onClick = { onIntent(LoadIntent.LoadClicked) }
        )
    }
}

@Preview(showBackground = true, name = "Idle")
@Composable
private fun LoadScreenIdlePreview() {
    IntervalTimerTheme {
        LoadScreenContent(
            state = LoadUiState(),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
private fun LoadScreenLoadingPreview() {
    IntervalTimerTheme {
        LoadScreenContent(
            state = LoadUiState(
                btnState = LoadButtonState(
                    titleRes = R.string.load_button_loading,
                    isLoading = true
                )
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun LoadScreenErrorPreview() {
    IntervalTimerTheme {
        LoadScreenContent(
            state = LoadUiState(
                btnState = LoadButtonState(titleRes = R.string.load_button_retry),
                timerIdInputState = OutlinedTextFieldState(
                    value = "68",
                    isError = true,
                    labelRes = R.string.label_timer_id,
                    errorRes = R.string.error_timer_not_found
                )
            ),
            onIntent = {}
        )
    }
}