package com.pereu.intervaltimer.ui.load

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pereu.intervaltimer.domain.model.Timer
import com.pereu.intervaltimer.ui.theme.*
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

        // Поле ввода
        val isError = state.resource is Resource.Error

        Column(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "ID тренировки",
                style = CaptionStyle,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            OutlinedTextField(
                value = state.timerId,
                onValueChange = { onIntent(LoadIntent.IdChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    errorBorderColor = Error
                )
            )

            if (isError) {
                Spacer(modifier = Modifier.height(Spacing.xs))
                Text(
                    text = "Тренировка не найдена. Проверьте ID.",
                    style = CaptionStyle,
                    color = Error
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.l))

        // Кнопка
        val isLoading = state.resource is Resource.Loading
        Button(
            onClick = { onIntent(LoadIntent.LoadClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .height(Size.buttonHeight),
            enabled = !isLoading,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                disabledContainerColor = DisabledBg
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Spacing.xl),
                    color = Primary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(Spacing.s))
                Text("Загрузка...", style = ButtonStyle, color = TextTertiary)
            } else {
                Text(
                    text = if (isError) "Повторить" else "Загрузить",
                    style = ButtonStyle,
                    color = Surface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadScreenPreview() {
    IntervalTimerTheme {
        LoadScreenContent(
            state = LoadUiState(),
            onIntent = {},
            onTimerLoaded = {}
        )
    }
}