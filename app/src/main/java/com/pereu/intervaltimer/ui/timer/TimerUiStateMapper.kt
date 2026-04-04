package com.pereu.intervaltimer.ui.timer

import com.pereu.intervaltimer.domain.model.TimerModel
import javax.inject.Inject

class TimerUiStateMapper @Inject constructor() {
    fun toInitialUiState(timer: TimerModel): TimerUiState = TimerUiState(
        title = timer.title,
        totalTime = timer.totalTime,
        totalTimeFormatted = timer.totalTime.toTimeFormatted(),
        intervals = timer.intervals.mapIndexed { index, interval ->
            IntervalUiState(
                title = interval.title,
                time = interval.time,
                timeFormatted = interval.time.toTimeFormatted(),
                status = if (index == 0) IntervalStatus.Active else IntervalStatus.Pending
            )
        },
        currentIntervalIndex = 0,
        remainingIntervalTime = timer.intervals.first().time
    )
}

fun Int.toTimeFormatted(): String {
    val minutes = this / 60
    val seconds = this % 60
    return "%d:%02d".format(minutes, seconds)
}