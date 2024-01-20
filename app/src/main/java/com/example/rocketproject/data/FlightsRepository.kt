package com.example.rocketproject.data

import com.example.rocketproject.data.model.SpaceflightNewsResponse
import retrofit2.HttpException
import javax.inject.Inject

internal class FlightsRepository @Inject constructor(
    private val service: RocketService
) {
    suspend fun getFlights(): Result<SpaceflightNewsResponse> {
        return try {
            Result.success(service.fetchFlights())
        } catch (ex: HttpException) {
            Result.failure(ex)
        } catch (ex: Exception) {
            Result.failure(ex)

        }
    }
}
