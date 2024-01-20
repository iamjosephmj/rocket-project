package com.example.rocketproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.rocketproject.ui.compose.RenderSpaceFlightsUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SpaceFlightActivity : ComponentActivity() {

    private val viewModel: SpaceFlightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewState by viewModel.viewState.collectAsState()
            val uiEvent by viewModel.uiEvent.collectAsState(initial = UIEvent.Stale)
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(key1 = Unit) {
                viewModel.populateSpaceFlightsDataInUI()
            }

            LaunchedEffect(key1 = uiEvent) {
                when (uiEvent) {
                    is UIEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            (uiEvent as UIEvent.ShowSnackBar).snackbarMessage,
                            withDismissAction = true
                        )
                    }

                    UIEvent.Stale -> {
                        // Do nothing
                    }
                }
            }

            RenderSpaceFlightsUI(
                populateSpaceFlightsDataInUI = viewModel::populateSpaceFlightsDataInUI,
                viewState = viewState,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}