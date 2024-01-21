package com.example.rocketproject.data

import com.example.rocketproject.data.model.SpaceflightNewsResponse
import javax.inject.Inject

internal class FlightsRepository @Inject constructor(
    private val service: RocketService
) {
    suspend fun getFlights(): Result<SpaceflightNewsResponse> {
        return try {
            Result.success(service.fetchFlights())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
