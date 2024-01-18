package com.example.rocketproject.ui

import com.example.rocketproject.common.MainDispatcherRule
import com.example.rocketproject.domain.GetSpaceFlightsUseCase
import com.example.rocketproject.domain.model.SpaceFlightsItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class SpaceFlightViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSpaceFlights = mock<GetSpaceFlightsUseCase>()

    private fun viewModel() = SpaceFlightViewModel(getSpaceFlights = getSpaceFlights)

    @Test
    fun `WHEN space flight data is available, THEN update the view state`() = runTest {
        val expectedItem = Result.success(
            listOf(
                SpaceFlightsItem(
                    title = "title",
                    summary = "summary",
                    publishedAt = "publishedAt"
                )
            )
        )
        whenever(getSpaceFlights()) doReturn expectedItem

        val viewModel = viewModel()

        val viewState = viewModel.viewState.value
        val result = (viewState as ViewState.SpaceFlightsData).spaceFlights
        assertThat(result).isEqualTo(expectedItem.getOrThrow())

    }

    @Test
    fun `WHEN space flight data fetching fails, THEN update the view state`() = runTest {
        val error = Result.failure<List<SpaceFlightsItem>>(Exception("network error"))
        whenever(getSpaceFlights()) doReturn error

        val viewModel = viewModel()

        val viewState = viewModel.viewState.value
        assertThat(viewState).isEqualTo(ViewState.Error)
    }


    @Test
    fun `WHEN retry button is clicked AND space flight data is available, THEN update the view state`() = runTest {
        val expectedItem = Result.success(
            listOf(
                SpaceFlightsItem(
                    title = "title",
                    summary = "summary",
                    publishedAt = "publishedAt"
                )
            )
        )
        whenever(getSpaceFlights()) doReturn expectedItem

        val viewModel = viewModel()
        viewModel.retry()

        val viewState = viewModel.viewState.value
        val result = (viewState as ViewState.SpaceFlightsData).spaceFlights
        assertThat(result).isEqualTo(expectedItem.getOrThrow())

    }

    @Test
    fun `WHEN retry button is clicked AND space flight data fetching fails, THEN update the view state`() = runTest {
        val error = Result.failure<List<SpaceFlightsItem>>(Exception("network error"))
        whenever(getSpaceFlights()) doReturn error

        val viewModel = viewModel()
        viewModel.retry()

        val viewState = viewModel.viewState.value
        assertThat(viewState).isEqualTo(ViewState.Error)
    }
}