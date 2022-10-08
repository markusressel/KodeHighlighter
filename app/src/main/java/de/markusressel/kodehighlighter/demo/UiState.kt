package de.markusressel.kodehighlighter.demo

data class UiState(
    val selectedLanguage: LanguageExample = LanguageExample.Markdown,
    val selectedViewType: ViewType = ViewType.Text
)

enum class LanguageExample {
    Java, Json, Kotlin, Markdown, Ocaml, Python
}

enum class ViewType {
    Text, Editor
}