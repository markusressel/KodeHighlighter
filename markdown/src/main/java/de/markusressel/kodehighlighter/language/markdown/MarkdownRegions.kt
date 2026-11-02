package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule

/**
 * Fenced and inline code of Markdown, whose content is not formatted
 */
internal object MarkdownRegions {

    val scanner = RegionScanner(
        listOf(
            PatternRegion(CodeBlockRule, "`~", "(?m)(?<=^ {0,3})(`{3,}|~{3,})[^\\n]*\\n[\\s\\S]*?(?:^ {0,3}\\1[`~]*[ \\t]*\$|\\z)".toRegex()),
            PatternRegion(CodeInlineRule, "`", "(?<!`)(`+)(?!`)[\\s\\S]*?(?<!`)\\1(?!`)".toRegex()),
        )
    )

}
