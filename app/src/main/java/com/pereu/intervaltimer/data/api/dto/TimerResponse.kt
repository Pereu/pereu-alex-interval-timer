package com.pereu.intervaltimer.data.api.dto

import com.google.gson.annotations.SerializedName

data class TimerResponse(
    @SerializedName("timer")
    val timer: TimerDto
)

data class TimerDto(
    @SerializedName("timer_id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("total_time")
    val totalTime: Int,

    @SerializedName("intervals")
    val intervals: List<IntervalDto>
)

data class IntervalDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("time")
    val time: Int
)
