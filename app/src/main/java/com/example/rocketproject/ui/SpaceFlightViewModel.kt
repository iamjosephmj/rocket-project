package com.example.rocketproject.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketproject.domain.GetSpaceFlightsUseCase
import com.example.rocketproject.domain.model.SpaceFlightsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SpaceFlightViewModel @Inject constructor(
    private val getSpaceFlights: GetSpaceFlightsUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    init {
        fetchFlights()
    }

    private fun fetchFlights() {
        viewModelScope.launch {
            getSpaceFlights().onSuccess { spaceFlights ->
                _viewState.update {
                    ViewState.SpaceFlightsData(spaceFlights)
                }
            }.onFailure {
                _viewState.update {
                    ViewState.Error
                }
            }
        }
    }

    fun retry() {
        _viewState.update {
            ViewState.Loading
        }
        fetchFlights()
    }
}

@Immutable
internal sealed class ViewState {

    @Immutable
    data class SpaceFlightsData(val spaceFlights: List<SpaceFlightsItem>) : ViewState()

    @Immutable
    object Error : ViewState()

    @Immutable
    object Loading : ViewState()
}
