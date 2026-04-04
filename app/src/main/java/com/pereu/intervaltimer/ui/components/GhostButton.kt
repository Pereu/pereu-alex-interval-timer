package com.pereu.intervaltimer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pereu.intervaltimer.ui.theme.*

@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Size.ghostButtonHeight),
    ) {
        Text(
            text = text,
            style = ButtonStyle,
            color = Error
        )
    }
}