package com.pereu.intervaltimer.domain.usecase

import com.pereu.intervaltimer.data.repository.TimerRepository
import com.pereu.intervaltimer.domain.model.TimerModel
import com.pereu.intervaltimer.util.Resource
import javax.inject.Inject

class GetTimerUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    suspend operator fun invoke(id: String): Resource<TimerModel> {
        return try {
            Resource.Success(repository.getTimer(id))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}