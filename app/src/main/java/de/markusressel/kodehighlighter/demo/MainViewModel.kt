package de.markusressel.kodehighlighter.demo

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val uiState: UiState = UiState()

    fun onUiEvent(event: UiEvent) {
        when (event) {
            else -> {}
        }
    }

}