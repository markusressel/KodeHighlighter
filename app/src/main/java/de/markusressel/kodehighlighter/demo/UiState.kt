package de.markusressel.kodehighlighter.demo

import androidx.annotation.RawRes
import de.markusressel.kodehighlighter.core.colorscheme.GitHubDarkTheme
import de.markusressel.kodehighlighter.core.colorscheme.GitHubLightTheme
import de.markusressel.kodehighlighter.core.colorscheme.TokenTheme

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

val kotlinExample = LanguageExample(
    name = "Kotlin",
    resource = R.raw.kotlin_example
)

val ocamlExample = LanguageExample(
    name = "OCaml",
    resource = R.raw.ocaml_example
)

data class UiState(
    val selectedLanguage: LanguageExample = markdownExample,
    val availableLanguages: List<LanguageExample> = listOf(
        markdownExample,
        javaExample,
        jsonExample,
        pythonExample,
        kotlinExample,
        ocamlExample,
    ),
    val selectedViewType: ViewType = ViewType.Text,
    val selectedTheme: DemoTheme = DemoTheme.LanguageDefault,
)

data class LanguageExample(
    val name: String,
    @RawRes
    val resource: Int,
)

enum class ViewType {
    Text, Editor
}

enum class DemoTheme(val title: String, val theme: TokenTheme?) {
    LanguageDefault("Language default", null),
    GitHubDark("GitHub Dark", GitHubDarkTheme),
    GitHubLight("GitHub Light", GitHubLightTheme),
}