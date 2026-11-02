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

val xmlExample = LanguageExample(
    name = "XML",
    resource = R.raw.xml_example
)

val yamlExample = LanguageExample(
    name = "YAML",
    resource = R.raw.yaml_example
)

val tomlExample = LanguageExample(
    name = "TOML",
    resource = R.raw.toml_example
)

val propertiesExample = LanguageExample(
    name = "Properties",
    resource = R.raw.properties_example
)

val diffExample = LanguageExample(
    name = "Diff",
    resource = R.raw.diff_example
)

val cExample = LanguageExample(
    name = "C",
    resource = R.raw.c_example
)

val cppExample = LanguageExample(
    name = "C++",
    resource = R.raw.cpp_example
)

val csharpExample = LanguageExample(
    name = "C#",
    resource = R.raw.csharp_example
)

val goExample = LanguageExample(
    name = "Go",
    resource = R.raw.go_example
)

val rustExample = LanguageExample(
    name = "Rust",
    resource = R.raw.rust_example
)

val swiftExample = LanguageExample(
    name = "Swift",
    resource = R.raw.swift_example
)

val dartExample = LanguageExample(
    name = "Dart",
    resource = R.raw.dart_example
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
        xmlExample,
        yamlExample,
        tomlExample,
        propertiesExample,
        diffExample,
        cExample,
        cppExample,
        csharpExample,
        goExample,
        rustExample,
        swiftExample,
        dartExample,
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