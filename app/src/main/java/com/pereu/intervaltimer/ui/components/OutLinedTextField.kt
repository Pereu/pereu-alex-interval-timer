package com.pereu.intervaltimer.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.CaptionStyle
import com.pereu.intervaltimer.ui.theme.Error
import com.pereu.intervaltimer.ui.theme.Primary
import com.pereu.intervaltimer.ui.theme.Size
import com.pereu.intervaltimer.ui.theme.Spacing
import com.pereu.intervaltimer.ui.theme.TextSecondary

@Immutable
data class OutlinedTextFieldState(
    val value: String = "",
    val isError: Boolean = false,
    @get:StringRes val labelRes: Int = R.string.empty,
    @get:StringRes val errorRes: Int? = null
)

@Composable
fun OutLinedTextField(
    state: OutlinedTextFieldState,
    onValueChange: (String) -> Unit
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Text(
            text = stringResource(state.labelRes),
            style = CaptionStyle,
            color = TextSecondary
        )

        OutlinedTextField(
            value = state.value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(Size.inputHeight),
            isError = state.isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                errorBorderColor = Error
            )
        )

        if (state.isError && state.errorRes != null) {
            Text(
                text = stringResource(state.errorRes),
                style = CaptionStyle,
                color = Error
            )
        }
    }
}