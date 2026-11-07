package de.markusressel.kodehighlighter.language.markdown

import de.markusressel.kodehighlighter.core.region.PatternRegion
import de.markusressel.kodehighlighter.core.region.RegionScanner
import de.markusressel.kodehighlighter.language.markdown.rule.CodeBlockRule
import de.markusressel.kodehighlighter.language.markdown.rule.CodeInlineRule
import de.markusressel.kodehighlighter.language.markdown.rule.FrontMatterRule

/**
 * Front matter, fenced and inline code of Markdown, whose content is not formatted
 */
internal object MarkdownRegions {

    val scanner = RegionScanner(
        listOf(
            // only at the very start, and only when it is closed, so a horizontal rule in the first line stays one
            PatternRegion(FrontMatterRule, "-", "\\A---[ \\t]*\\r?\\n(?:[^\\n]*\\n)*?(?:---|\\.\\.\\.)[ \\t]*(?=\\r?\\n|\\z)".toRegex()),
            PatternRegion(CodeBlockRule, "`~", "(?m)(?<=^ {0,3})(`{3,}|~{3,})[^\\n]*\\n[\\s\\S]*?(?:^ {0,3}\\1[`~]*[ \\t]*\$|\\z)".toRegex()),
            PatternRegion(CodeInlineRule, "`", "(?<!`)(`+)(?!`)[\\s\\S]*?(?<!`)\\1(?!`)".toRegex()),
        )
    )

}
