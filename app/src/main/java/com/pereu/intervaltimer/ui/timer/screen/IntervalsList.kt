package com.pereu.intervaltimer.ui.timer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pereu.intervaltimer.R
import com.pereu.intervaltimer.ui.theme.*
import com.pereu.intervaltimer.ui.timer.IntervalStatus
import com.pereu.intervaltimer.ui.timer.IntervalUiState
import com.pereu.intervaltimer.ui.timer.TimerStatus

@Composable
fun IntervalsList(
    modifier: Modifier = Modifier,
    intervals: List<IntervalUiState>,
    timerStatus: TimerStatus,
    currentIndex: Int
) {

    val listState = rememberLazyListState()

    LaunchedEffect(currentIndex) {
        listState.animateScrollToItem(currentIndex)
    }

    Column(modifier = modifier) {
        // Заголовок
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.m),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.intervals_title),
                style = LabelStyle,
                color = TextPrimary
            )
            Text(
                text = "${currentIndex + 1} из ${intervals.size}",
                style = CaptionStyle,
                color = TextSecondary
            )
        }

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
            itemsIndexed(intervals) { index, interval ->
                IntervalItem(
                    index = index,
                    interval = interval,
                    timerStatus = timerStatus
                )
            }
        }
    }
}

@Composable
fun IntervalItem(
    index: Int,
    interval: IntervalUiState,
    timerStatus: TimerStatus = TimerStatus.Idle
) {
    val isCompleted = interval.status == IntervalStatus.Completed
    val isActive = interval.status == IntervalStatus.Active

    val activeColor = if (timerStatus == TimerStatus.Paused) Orange else Primary
    val activeBgColor = if (timerStatus == TimerStatus.Paused) Color(0x14E67E22) else PrimaryLight

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.intervalItem))
            .background(Surface)
            .then(
                if (isActive) Modifier.border(
                    1.5.dp, activeColor, RoundedCornerShape(Radius.intervalItem)
                ) else Modifier
            )
            .then(
                if (isActive) Modifier.drawBehind {
                    drawRect(
                        color = activeBgColor,
                        size = size.copy(width = size.width * interval.progress)
                    )
                } else Modifier
            )
            .alpha(if (isCompleted) 0.45f else 1f)
            .padding(horizontal = Spacing.l, vertical = Spacing.m),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Бейдж
        Box(
            modifier = Modifier
                .size(Size.badgeSize)
                .background(
                    color = when {
                        isActive -> activeColor
                        else -> DisabledBg
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(Spacing.l)
                )
            } else {
                Text(
                    text = "${index + 1}",
                    style = CaptionStyle,
                    color = if (isActive) Surface else TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.width(Spacing.m))

        Text(
            text = interval.title,
            style = LabelStyle,
            color = if (isCompleted) TextTertiary else TextPrimary,
            textDecoration = if (isCompleted) TextDecoration.LineThrough else null,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = interval.timeFormatted,
            style = MonoStyle,
            color = if (isActive) activeColor else TextTertiary
        )
    }
}

private val pendingInterval = IntervalUiState(
    title = "Ходьба в среднем темпе",
    time = 300,
    timeFormatted = "5:00",
    status = IntervalStatus.Pending
)

@Preview(showBackground = true, name = "Pending")
@Composable
private fun IntervalItemPendingPreview() {
    IntervalTimerTheme {
        IntervalItem(index = 0, interval = pendingInterval)
    }
}

@Preview(showBackground = true, name = "Active")
@Composable
private fun IntervalItemActivePreview() {
    IntervalTimerTheme {
        IntervalItem(
            index = 3,
            interval = pendingInterval.copy(
                status = IntervalStatus.Active,
                progress = 0.7f
            ),
        )
    }
}

@Preview(showBackground = true, name = "Completed")
@Composable
private fun IntervalItemCompletedPreview() {
    IntervalTimerTheme {
        IntervalItem(
            index = 0,
            interval = pendingInterval.copy(status = IntervalStatus.Completed)
        )
    }
}

@Preview(showBackground = true, name = "Paused")
@Composable
private fun IntervalItemPausedPreview() {
    IntervalTimerTheme {
        IntervalItem(
            index = 3,
            interval = pendingInterval.copy(
                status = IntervalStatus.Active,
                progress = 0.4f
            ),
            timerStatus = TimerStatus.Paused
        )
    }
}