package de.markusressel.kodehighlighter.languages

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.language.Language
import de.markusressel.kodehighlighter.core.language.LanguageDetector
import de.markusressel.kodehighlighter.language.c.CLanguage
import de.markusressel.kodehighlighter.language.c.CRuleBook
import de.markusressel.kodehighlighter.language.cpp.CppLanguage
import de.markusressel.kodehighlighter.language.cpp.CppRuleBook
import de.markusressel.kodehighlighter.language.csharp.CSharpLanguage
import de.markusressel.kodehighlighter.language.csharp.CSharpRuleBook
import de.markusressel.kodehighlighter.language.css.CssLanguage
import de.markusressel.kodehighlighter.language.css.CssRuleBook
import de.markusressel.kodehighlighter.language.dart.DartLanguage
import de.markusressel.kodehighlighter.language.dart.DartRuleBook
import de.markusressel.kodehighlighter.language.diff.DiffLanguage
import de.markusressel.kodehighlighter.language.diff.DiffRuleBook
import de.markusressel.kodehighlighter.language.go.GoLanguage
import de.markusressel.kodehighlighter.language.go.GoRuleBook
import de.markusressel.kodehighlighter.language.html.HtmlLanguage
import de.markusressel.kodehighlighter.language.html.HtmlRuleBook
import de.markusressel.kodehighlighter.language.java.JavaLanguage
import de.markusressel.kodehighlighter.language.java.JavaRuleBook
import de.markusressel.kodehighlighter.language.javascript.JavaScriptLanguage
import de.markusressel.kodehighlighter.language.javascript.JavaScriptRuleBook
import de.markusressel.kodehighlighter.language.json.JsonLanguage
import de.markusressel.kodehighlighter.language.json.JsonRuleBook
import de.markusressel.kodehighlighter.language.kotlin.KotlinLanguage
import de.markusressel.kodehighlighter.language.kotlin.KotlinRuleBook
import de.markusressel.kodehighlighter.language.markdown.MarkdownLanguage
import de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook
import de.markusressel.kodehighlighter.language.ocaml.OCamlLanguage
import de.markusressel.kodehighlighter.language.ocaml.OCamlRuleBook
import de.markusressel.kodehighlighter.language.properties.PropertiesLanguage
import de.markusressel.kodehighlighter.language.properties.PropertiesRuleBook
import de.markusressel.kodehighlighter.language.python.PythonLanguage
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import de.markusressel.kodehighlighter.language.rust.RustLanguage
import de.markusressel.kodehighlighter.language.rust.RustRuleBook
import de.markusressel.kodehighlighter.language.swift.SwiftLanguage
import de.markusressel.kodehighlighter.language.swift.SwiftRuleBook
import de.markusressel.kodehighlighter.language.toml.TomlLanguage
import de.markusressel.kodehighlighter.language.toml.TomlRuleBook
import de.markusressel.kodehighlighter.language.xml.XmlLanguage
import de.markusressel.kodehighlighter.language.xml.XmlRuleBook
import de.markusressel.kodehighlighter.language.yaml.YamlLanguage
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook

/**
 * All languages of KodeHighlighter, to find the language of a file and create its rule book:
 *
 * ```kotlin
 * val ruleBook = KodeHighlighterLanguages.detectRuleBook(fileName = "build.gradle.kts", text = text)
 * ```
 */
object KodeHighlighterLanguages {
    private val ruleBooks: Map<Language, () -> LanguageRuleBook> = linkedMapOf(
        // C comes before C++, so a `.h` file without other hints is C
        CLanguage to ::CRuleBook,
        CppLanguage to ::CppRuleBook,
        CSharpLanguage to ::CSharpRuleBook,
        CssLanguage to ::CssRuleBook,
        DartLanguage to ::DartRuleBook,
        DiffLanguage to ::DiffRuleBook,
        GoLanguage to ::GoRuleBook,
        HtmlLanguage to ::HtmlRuleBook,
        JavaLanguage to ::JavaRuleBook,
        JavaScriptLanguage to ::JavaScriptRuleBook,
        JsonLanguage to ::JsonRuleBook,
        KotlinLanguage to ::KotlinRuleBook,
        MarkdownLanguage to { MarkdownRuleBook() },
        OCamlLanguage to ::OCamlRuleBook,
        PropertiesLanguage to ::PropertiesRuleBook,
        PythonLanguage to ::PythonRuleBook,
        RustLanguage to ::RustRuleBook,
        SwiftLanguage to ::SwiftRuleBook,
        TomlLanguage to ::TomlRuleBook,
        XmlLanguage to ::XmlRuleBook,
        YamlLanguage to ::YamlRuleBook,
    )

    /** every language that has a rule book */
    val all: List<Language> = ruleBooks.keys.toList()

    /** a detector that chooses from [all] */
    val detector: LanguageDetector = LanguageDetector(all)

    /**
     * @param language the language
     * @return a new rule book for it, null if KodeHighlighter has none
     */
    fun ruleBookFor(language: Language): LanguageRuleBook? = ruleBooks[language]?.invoke()

    /**
     * @param name the id, an alias or a file extension of a language, ignoring case, like `kotlin`, `kt` or `py`, as
     * in the info string of a fenced code block of Markdown
     * @return a new rule book for that language, null if none fits
     */
    fun ruleBookForName(name: String): LanguageRuleBook? {
        val language = detector.forId(name) ?: detector.detectOne(fileName = "file.${name.lowercase()}")
        return language?.let(::ruleBookFor)
    }

    /**
     * @param fileName the name of the file, a path is fine
     * @param text the content of the file
     * @param mimeType the MIME type of the file
     * @return a new rule book for the most likely language, null if none fits
     */
    fun detectRuleBook(fileName: String? = null, text: CharSequence? = null, mimeType: String? = null): LanguageRuleBook? =
        detector.detectOne(fileName, text, mimeType)?.let(::ruleBookFor)
}
