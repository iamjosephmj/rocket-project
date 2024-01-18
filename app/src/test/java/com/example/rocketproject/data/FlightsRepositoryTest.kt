package com.example.rocketproject.data

import com.example.rocketproject.common.validSpaceFlightResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


internal class FlightsRepositoryTest {

    private val service = mock<RocketService>()

    private val repository = FlightsRepository(service = service)


    @Test
    fun `WHEN flight list is fetched AND api is successful, THEN should return the list`() =
        runTest {
            whenever(service.fetchFlights()) doReturn validSpaceFlightResponse

            val response = repository.getFlights()

            val result = response.getOrThrow()
            assertThat(response.isSuccess).isTrue()
            assertThat(result).isEqualTo(validSpaceFlightResponse)
        }

    @Test
    fun `WHEN flight list is fetched AND api is failure, THEN should return error`() = runTest {
        val expectedError = Exception("network error")
        whenever(service.fetchFlights()).then { throw expectedError }

        val response = repository.getFlights()

        val result = response.exceptionOrNull()
        assertThat(response.isFailure).isTrue()
        assertThat(result).isEqualTo(expectedError)
    }
}
