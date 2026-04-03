package com.pereu.intervaltimer.domain.usecase

import com.pereu.intervaltimer.data.repository.TimerRepository
import com.pereu.intervaltimer.domain.model.Timer
import com.pereu.intervaltimer.util.Resource
import javax.inject.Inject

class GetTimerUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    suspend operator fun invoke(id: String): Resource<Timer> {
        return try {
            Resource.Success(repository.getTimer(id))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}