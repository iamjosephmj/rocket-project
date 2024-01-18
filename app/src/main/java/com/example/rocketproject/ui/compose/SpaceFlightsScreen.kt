package com.example.rocketproject.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.rocketproject.ui.ViewState

@Composable
internal fun SpaceFlightsScreen(
    viewState: ViewState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (viewState) {
        ViewState.Loading -> {
            LoadingComponent(modifier = modifier)
        }

        is ViewState.Error -> {
            ErrorComponent(
                onRetry = onRetry, modifier = modifier
            )
        }

        is ViewState.SpaceFlightsData -> {
            SpaceFlightItemComponent(
                spaceFlightItems = viewState.spaceFlights,
                modifier = modifier
            )
        }
    }
}
