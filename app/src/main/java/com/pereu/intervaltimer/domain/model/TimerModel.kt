package com.pereu.intervaltimer.domain.model

data class TimerModel(
    val id: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<IntervalModel>
)

data class IntervalModel(
    val title: String,
    val time: Int
)
