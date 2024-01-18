package com.example.rocketproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.rocketproject.ui.compose.SpaceFlightsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SpaceFlightActivity : ComponentActivity() {

    private val viewModel: SpaceFlightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewState by viewModel.viewState.collectAsState()

            SpaceFlightsScreen(
                viewState = viewState,
                onRetry = viewModel::retry,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}