package com.example.rocketproject.domain

import com.example.rocketproject.common.validSpaceFlightResponse
import com.example.rocketproject.data.FlightsRepository
import com.example.rocketproject.data.model.SpaceflightNewsResponse
import com.example.rocketproject.domain.mapper.PublishedAtMapper
import com.example.rocketproject.domain.model.SpaceFlightsItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


internal class GetSpaceFlightsUseCaseTest {
    private val repository = mock<FlightsRepository>()
    private val publishedAtMapper = mock<PublishedAtMapper>()

    private val useCase = GetSpaceFlightsUseCase(
        repository = repository,
        publishedAtMapper = publishedAtMapper
    )

    @Test
    fun `WHEN the UseCase is invoked AND FlightList response is not empty, THEN return the mapped response`() =
        runTest {
            whenever(repository.getFlights()) doReturn Result.success(validSpaceFlightResponse)
            whenever(publishedAtMapper("publishedAt")) doReturn "mapped output"
            val expectedResult = Result.success(
                listOf(
                    SpaceFlightsItem(
                        title = "title",
                        summary = "summary",
                        publishedAt = "mapped output"
                    )
                )
            )

            val result = useCase()

            assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `WHEN the UseCase is invoked AND FlightList response is error, THEN return the error response`() =
        runTest {
            val networkError = Throwable("network error")
            val expectedResult = Result.failure<SpaceflightNewsResponse>(networkError)
            whenever(repository.getFlights()) doReturn expectedResult

            val result = useCase()

            assertThat(result).isEqualTo(expectedResult)
        }
}
