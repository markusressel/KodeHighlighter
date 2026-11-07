package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.LanguageRuleBook
import de.markusressel.kodehighlighter.core.region.Embedding
import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.FrontMatterRule
import de.markusressel.kodehighlighter.language.yaml.YamlRuleBook

/**
 * Front matter, fenced and inline code of Markdown, whose content is not formatted
 */
internal object MarkdownRegions {

    /** without highlighting of the content of code blocks, for the rules on their own */
    val scanner = scanner { null }

    private val yaml = YamlRuleBook()

    /**
     * @param codeBlockRuleBook the rule book for the content of a fenced code block, by the language of its info string
     */
    fun scanner(codeBlockRuleBook: (language: String) -> LanguageRuleBook?) = RegionScanner(
        listOf(
            // only at the very start, and only when it is closed, so a horizontal rule in the first line stays one
            PatternRegion(
                FrontMatterRule,
                "-",
                "\\A---[ \\t]*\\r?\\n((?:[^\\n]*\\n)*?)(?:---|\\.\\.\\.)[ \\t]*(?=\\r?\\n|\\z)".toRegex(),
                Embedding(contentGroup = 1) { yaml },
            ),
            PatternRegion(
                CodeBlockRule,
                "`~",
                "(?m)(?<=^ {0,3})(`{3,}|~{3,})([^\\n]*)\\n([\\s\\S]*?)(?:^ {0,3}\\1[`~]*[ \\t]*\$|\\z)".toRegex(),
                Embedding(contentGroup = 3) { groups -> infoLanguage(groups[2])?.let(codeBlockRuleBook) },
            ),
            PatternRegion(CodeInlineRule, "`", "(?<!`)(`+)(?!`)[\\s\\S]*?(?<!`)\\1(?!`)".toRegex()),
        )
    )

    /** @return the language of an info string like `kotlin`, `py title="a.py"` or `{.rust .numberLines}` */
    internal fun infoLanguage(info: String?): String? {
        val words = info?.trim()?.removePrefix("{")?.trimStart() ?: return null
        val word = words.takeWhile { !it.isWhitespace() && it != '{' && it != '}' && it != ',' }.removePrefix(".")
        return word.ifEmpty { null }
    }
}
