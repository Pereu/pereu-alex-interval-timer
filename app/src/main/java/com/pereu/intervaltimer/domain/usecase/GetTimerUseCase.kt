package com.pereu.intervaltimer.domain.usecase

import com.pereu.intervaltimer.data.repository.TimerRepository
import com.pereu.intervaltimer.domain.model.TimerModel
import com.pereu.intervaltimer.util.Resource
import kotlinx.coroutines.TimeoutCancellationException
import javax.inject.Inject
import kotlinx.coroutines.withTimeout

class GetTimerUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    suspend operator fun invoke(id: String): Resource<TimerModel> {
        return try {
            withTimeout(5000) {
                Resource.Success(repository.getTimer(id))
            }
        } catch (_: TimeoutCancellationException) {
            Resource.Success(TimerModel.getMockData()) // fallback на mock
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}