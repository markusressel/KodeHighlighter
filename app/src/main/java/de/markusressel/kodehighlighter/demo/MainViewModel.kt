package de.markusressel.kodehighlighter.demo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())

    fun onUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ViewTypeSelected -> {
                uiState.value = uiState.value.copy(
                    selectedViewType = event.viewType
                )
            }
            is UiEvent.LanguageExampleSelected -> {
                uiState.value = uiState.value.copy(
                    selectedLanguage = event.languageExample
                )
            }
        }
    }

}