package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.Border
import com.pereu.intervaltimer.ui.theme.CaptionStyle
import com.pereu.intervaltimer.ui.theme.H1Style
import com.pereu.intervaltimer.ui.theme.Radius
import com.pereu.intervaltimer.ui.theme.Spacing
import com.pereu.intervaltimer.ui.theme.Surface
import com.pereu.intervaltimer.ui.theme.TextPrimary
import com.pereu.intervaltimer.ui.theme.TextSecondary
import androidx.compose.ui.platform.LocalResources

@Composable
fun TimerCompletedStats(
    totalTimeFormatted: String,
    intervalsCount: Int,
    modifier: Modifier = Modifier
) {

    val intervalsText = LocalResources.current.getQuantityString(
        R.plurals.intervals_count,
        intervalsCount
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s)
    ) {
        StatCard(
            value = totalTimeFormatted,
            label = stringResource(R.string.timer_total_time_label),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            value = "$intervalsCount",
            label = intervalsText,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Radius.timerCard))
            .background(Surface)
            .border(1.dp, Border, RoundedCornerShape(Radius.timerCard))
            .padding(Spacing.l),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, style = H1Style, color = TextPrimary)
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(text = label, style = CaptionStyle, color = TextSecondary)
        }
    }
}