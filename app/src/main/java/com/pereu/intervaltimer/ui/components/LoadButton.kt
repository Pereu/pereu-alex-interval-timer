package com.pereu.intervaltimer.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.*

@Immutable
data class PrimaryButtonState(
    @get:StringRes val titleRes: Int = R.string.empty,
    val enabled: Boolean = true,
    val isLoading: Boolean = false,
    val containerColor: Color = Primary,
    val icon: ImageVector? = null
)

@Composable
fun PrimaryButton(
    state: PrimaryButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Size.buttonHeight),
        enabled = state.enabled && !state.isLoading,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = state.containerColor,
            disabledContainerColor = DisabledBg
        )
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(Spacing.xl),
                color = Primary,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(Spacing.s))
            Text(stringResource(state.titleRes), style = ButtonStyle, color = TextTertiary)
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                state.icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Surface,
                        modifier = Modifier.size(Spacing.xl)
                    )
                    Spacer(modifier = Modifier.width(Spacing.s))
                }
                Text(stringResource(state.titleRes), style = ButtonStyle, color = Surface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    IntervalTimerTheme {
        Column(
            modifier = Modifier.padding(Spacing.l),
            verticalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
            PrimaryButton(
                state = PrimaryButtonState(titleRes = R.string.load_button_load),
                onClick = {}
            )
            PrimaryButton(
                state = PrimaryButtonState(
                    titleRes = R.string.load_button_loading,
                    isLoading = true
                ), onClick = {}
            )
            PrimaryButton(
                state = PrimaryButtonState(
                    titleRes = R.string.load_button_retry,
                    enabled = false
                ), onClick = {}
            )
        }
    }
}