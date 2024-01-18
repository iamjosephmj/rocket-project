package com.example.rocketproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.rocketproject.ui.compose.SpaceFlightsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SpaceFlightActivity : ComponentActivity() {

    private val viewModel: SpaceFlightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewState by viewModel.viewState.collectAsState()

            LaunchedEffect(key1 = Unit) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.startFlightDataFetch()
                }
            }

            SpaceFlightsScreen(
                viewState = viewState,
                onRetry = viewModel::startFlightDataFetch,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}