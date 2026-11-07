package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.markdown.MarkdownRegions

/**
 * YAML front matter: a block at the very start of the text between a `---` line and the next `---` or `...` line.
 * In [de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook], this rule gets the two lines and the content
 * is highlighted as YAML. No Markdown rule matches inside of it.
 */
object FrontMatterRule : LanguageRule {

    override val tokenType = TokenType.MARKER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return MarkdownRegions.scanner.findMatches(text, this)
    }

}
