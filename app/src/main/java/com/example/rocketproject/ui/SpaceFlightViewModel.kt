package com.example.rocketproject.ui

import android.app.Application
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketproject.R
import com.example.rocketproject.domain.GetSpaceFlightsUseCase
import com.example.rocketproject.domain.model.SpaceFlightsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SpaceFlightViewModel @Inject constructor(
    private val getSpaceFlights: GetSpaceFlightsUseCase,
    private val application: Application
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    private val _uiEvent = MutableSharedFlow<UIEvent>().apply {
        tryEmit(UIEvent.Stale)
    }

    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()
    val uiEvent: SharedFlow<UIEvent> = _uiEvent.asSharedFlow()

    fun populateSpaceFlightsDataInUI() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.Stale)

            showLoading()

            startSpaceFlightsFetchApi()
        }
    }

    private suspend fun startSpaceFlightsFetchApi() {
        getSpaceFlights()
            .onSuccess(::handleSuccess)
            .onFailure(::handleError)
    }

    private fun showLoading() {
        _viewState.update {
            ViewState.Loading
        }
    }

    private fun handleSuccess(spaceFlights: List<SpaceFlightsItem>) {
        _viewState.update {
            ViewState.SpaceFlightsData(spaceFlights)
        }
    }

    private fun handleError(ex: Throwable) {
        viewModelScope.launch {
            _viewState.emit(ViewState.Error)

            val errorMessage =
                ex.localizedMessage ?: application.getString(R.string.something_went_wrong)
            _uiEvent.emit(UIEvent.ShowSnackBar(errorMessage))
        }
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


@Immutable
internal sealed class UIEvent {

    @Immutable
    data class ShowSnackBar(val snackbarMessage: String) : UIEvent()

    @Immutable
    object Stale : UIEvent()
}