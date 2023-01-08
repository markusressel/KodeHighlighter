package de.markusressel.kodehighlighter.demo

import androidx.annotation.RawRes

val markdownExample = LanguageExample(
    name = "Markdown",
    resource = R.raw.markdown_sample
)

val javaExample = LanguageExample(
    name = "Java",
    resource = R.raw.java_sample
)

val jsonExample = LanguageExample(
    name = "JSON",
    resource = R.raw.json_example
)

val pythonExample = LanguageExample(
    name = "Python",
    resource = R.raw.python_example
)

data class UiState(
    val selectedLanguage: LanguageExample = markdownExample,
    val availableLanguages: List<LanguageExample> = listOf(
        markdownExample,
        javaExample,
        jsonExample,
        pythonExample,
    ),
    val selectedViewType: ViewType = ViewType.Text
)

data class LanguageExample(
    val name: String,
    @RawRes
    val resource: Int,
)

enum class ViewType {
    Text, Editor
}