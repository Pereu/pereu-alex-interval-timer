package com.pereu.intervaltimer.domain.model

data class TimerModel(
    val id: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<Interval>
)

data class Interval(
    val title: String,
    val time: Int
)
