package com.pereu.intervaltimer.ui.load

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pereu.intervaltimer.domain.model.Timer
import com.pereu.intervaltimer.ui.components.LoadButton
import com.pereu.intervaltimer.ui.components.LoadButtonState
import com.pereu.intervaltimer.ui.components.OutLinedTextField
import com.pereu.intervaltimer.ui.theme.BodyStyle
import com.pereu.intervaltimer.ui.theme.H1Style
import com.pereu.intervaltimer.ui.theme.IntervalTimerTheme
import com.pereu.intervaltimer.ui.theme.Primary
import com.pereu.intervaltimer.ui.theme.Size
import com.pereu.intervaltimer.ui.theme.Spacing
import com.pereu.intervaltimer.ui.theme.TextPrimary
import com.pereu.intervaltimer.ui.theme.TextSecondary
import com.pereu.intervaltimer.util.Resource

@Composable
fun LoadScreen(
    onTimerLoaded: (Timer) -> Unit,
    viewModel: LoadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoadScreenContent(
        state = state,
        onIntent = viewModel::handleIntent,
        onTimerLoaded = onTimerLoaded
    )
}

@Composable
private fun LoadScreenContent(
    state: LoadUiState,
    onIntent: (LoadIntent) -> Unit,
    onTimerLoaded: (Timer) -> Unit
) {

    LaunchedEffect(state.resource) {
        if (state.resource is Resource.Success) {
            onTimerLoaded((state.resource).data)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.xl2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Иконка
        Surface(
            modifier = Modifier.size(Size.iconSize),
            shape = MaterialTheme.shapes.large,
            color = Primary
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("🕐", style = MaterialTheme.typography.headlineLarge)
            }
        }

        Spacer(modifier = Modifier.height(Spacing.xl))

        // Заголовок
        Text(
            text = "Интервальный\nтаймер",
            style = H1Style,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.s))

        Text(
            text = "Введите ID тренировки для загрузки программы интервалов",
            style = BodyStyle,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.xl2 + Spacing.s))

        OutLinedTextField(
            state = state.inputState,
            onValueChange = { onIntent(LoadIntent.IdChanged(it)) }
        )

        Spacer(modifier = Modifier.height(Spacing.l))

        LoadButton(
            state = state.btnState,
            onClick = { onIntent(LoadIntent.LoadClicked) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadScreenPreview() {
    IntervalTimerTheme {
        LoadScreenContent(
            state = LoadUiState(
                btnState = LoadButtonState()
            ),
            onIntent = {},
            onTimerLoaded = {}
        )
    }
}