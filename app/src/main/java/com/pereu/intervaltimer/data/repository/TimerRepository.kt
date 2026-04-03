package com.pereu.intervaltimer.data.repository

import com.pereu.intervaltimer.data.api.ApiService
import com.pereu.intervaltimer.domain.model.Interval
import com.pereu.intervaltimer.domain.model.Timer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTimer(id: String): Timer {
        val response = apiService.getTimer(id)
        return with(response.timer) {
            Timer(
                id = this.id,
                title = this.title,
                totalTime = this.totalTime,
                intervals = this.intervals.map {
                    Interval(title = it.title, time = it.time)
                }
            )
        }
    }
}