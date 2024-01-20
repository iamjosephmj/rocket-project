package com.example.rocketproject.domain

import com.example.rocketproject.data.FlightsRepository
import com.example.rocketproject.domain.mapper.HttpExceptionMapper
import com.example.rocketproject.domain.mapper.PublishedAtMapper
import com.example.rocketproject.domain.model.SpaceFlightsItem
import javax.inject.Inject

internal class GetSpaceFlightsUseCase @Inject constructor(
    private val repository: FlightsRepository,
    private val publishedAtMapper: PublishedAtMapper,
    private val httpExceptionMapper: HttpExceptionMapper
) {
    suspend operator fun invoke(): Result<List<SpaceFlightsItem>> {
        return repository.getFlights().fold(
            onSuccess = { response ->
                Result.success(response.results.map { spaceFlightItem ->
                    SpaceFlightsItem(
                        title = spaceFlightItem.title,
                        summary = spaceFlightItem.summary,
                        publishedAt = publishedAtMapper(spaceFlightItem.publishedAt)
                    )
                })
            },
            onFailure = {
                Result.failure(httpExceptionMapper(it))
            }
        )
    }
}
