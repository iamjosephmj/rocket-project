package com.example.rocketproject.ui

import android.app.Application
import app.cash.turbine.test
import com.example.rocketproject.common.MainDispatcherRule
import com.example.rocketproject.domain.GetSpaceFlightsUseCase
import com.example.rocketproject.domain.model.SpaceFlightsItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class SpaceFlightViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val customError = "customError"

    private val mockApplication = mock<Application> {
        on { getString(any()) } doReturn customError
    }

    private val getSpaceFlights = mock<GetSpaceFlightsUseCase>()

    private fun viewModel() = SpaceFlightViewModel(
        getSpaceFlights = getSpaceFlights,
        application = mockApplication
    )

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
        viewModel.populateSpaceFlightsDataInUI()

        val viewState = viewModel.viewState.value
        val result = (viewState as ViewState.SpaceFlightsData).spaceFlights
        assertThat(result).isEqualTo(expectedItem.getOrThrow())

    }

    @Test
    fun `WHEN space flight data fetching fails, THEN update the view state`() = runTest {
        val networkError = "network error"
        val error = Result.failure<List<SpaceFlightsItem>>(Exception(networkError))
        whenever(getSpaceFlights()) doReturn error

        val viewModel = viewModel()

        viewModel.uiEvent.test {
            viewModel.populateSpaceFlightsDataInUI()

            val viewState = viewModel.viewState.value

            assertThat(viewState).isEqualTo(ViewState.Error)
            assertThat(awaitItem()).isEqualTo(UIEvent.Stale)
            assertThat(awaitItem()).isEqualTo(UIEvent.ShowSnackBar(networkError))
        }
    }

    @Test
    fun `WHEN space flight data fetching fails and there is no error message from network layer, THEN update the view state`() =
        runTest {
            val error = Result.failure<List<SpaceFlightsItem>>(Exception())
            whenever(getSpaceFlights()) doReturn error

            val viewModel = viewModel()

            viewModel.uiEvent.test {
                viewModel.populateSpaceFlightsDataInUI()
                val viewState = viewModel.viewState.value

                assertThat(viewState).isEqualTo(ViewState.Error)
                assertThat(awaitItem()).isEqualTo(UIEvent.Stale)
                assertThat(awaitItem()).isEqualTo(UIEvent.ShowSnackBar(customError))
            }
        }


    @Test
    fun `WHEN viewModel is created, THEN the viewState should hold Loading state`() {
        val viewModel = viewModel()

        val viewState = viewModel.viewState.value
        assertThat(viewState).isEqualTo(ViewState.Loading)
    }
}
