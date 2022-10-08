package de.markusressel.kodehighlighter.demo

sealed class UiEvent {
    data class ViewTypeSelected(val viewType: ViewType) : UiEvent()
}
