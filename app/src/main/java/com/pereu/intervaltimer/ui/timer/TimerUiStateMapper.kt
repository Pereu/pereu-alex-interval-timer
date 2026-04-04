package com.pereu.intervaltimer.ui.timer

import com.pereu.intervaltimer.domain.model.TimerModel
import com.pereu.intervaltimer.ui.timer.screen.TimerCardState
import com.pereu.intervaltimer.ui.timer.screen.TimerTopBarState
import javax.inject.Inject

class TimerUiStateMapper @Inject constructor() {

    fun toInitialUiState(timer: TimerModel): TimerUiState {
        val intervals = timer.intervals.mapIndexed { index, interval ->
            IntervalUiState(
                title = interval.title,
                time = interval.time,
                timeFormatted = interval.time.toTimeFormatted(),
                status = if (index == 0) IntervalStatus.Active else IntervalStatus.Pending
            )
        }

        val totalTime = timer.totalTime

        return TimerUiState(
            remainingIntervalTime = timer.intervals.first().time,
            currentIntervalIndex = 0,
            intervals = intervals,
            topBarState = TimerTopBarState(
                title = timer.title,
                elapsedTimeFormatted = totalTime.toTimeFormatted()
            ),
            timerCardState = TimerCardState(
                intervalTitle = intervals.first().title,
                remainingTimeFormatted = timer.intervals.first().time.toTimeFormatted(),
                totalTimeFormatted = totalTime.toTimeFormatted()
            )
        )
    }
}

fun Int.toTimeFormatted(): String {
    val minutes = this / 60
    val seconds = this % 60
    return "%d:%02d".format(minutes, seconds)
}