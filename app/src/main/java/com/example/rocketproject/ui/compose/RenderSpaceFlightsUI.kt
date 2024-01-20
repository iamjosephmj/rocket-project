package com.example.rocketproject.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.rocketproject.ui.ViewState

@Composable
internal fun RenderSpaceFlightsUI(
    viewState: ViewState,
    snackbarHostState: SnackbarHostState,
    populateSpaceFlightsDataInUI: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->

        SpaceFlightsScreen(
            viewState = viewState,
            onRetry = populateSpaceFlightsDataInUI,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}
