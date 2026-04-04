package com.pereu.intervaltimer.data.repository

import com.pereu.intervaltimer.data.api.ApiService
import com.pereu.intervaltimer.domain.model.IntervalModel
import com.pereu.intervaltimer.domain.model.TimerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTimer(id: String): TimerModel {
        val response = apiService.getTimer(id)
        return with(response.timer) {
            TimerModel(
                id = this.id,
                title = this.title,
                totalTime = this.totalTime,
                intervals = this.intervals.map {
                    IntervalModel(title = it.title, time = it.time)
                }
            )
        }
    }
}