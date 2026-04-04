package com.pereu.intervaltimer.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.*

@Immutable
data class GhostButtonState(
    @get:StringRes val titleRes: Int = R.string.empty,
    val borderColor: Color = Error,
    val textColor: Color = Error
)

@Composable
fun GhostButton(
    state: GhostButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Size.ghostButtonHeight),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, state.borderColor.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Surface
        )
    ) {
        Text(
            text = stringResource(state.titleRes),
            style = ButtonStyle,
            color = state.textColor
        )
    }
}