package de.markusressel.kodehighlighter.demo

sealed class UiEvent {
    data class ViewTypeSelected(val viewType: ViewType) : UiEvent()
    data class LanguageExampleSelected(val languageExample: LanguageExample) : UiEvent()
}
