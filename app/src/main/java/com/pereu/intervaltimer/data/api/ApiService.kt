package com.pereu.intervaltimer.data.api

import com.pereu.intervaltimer.data.dto.TimerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("interval-timers/{id}")
    suspend fun getTimer(
        @Path("id") id: String
    ): TimerResponse
}