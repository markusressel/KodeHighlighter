package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.markdown.MarkdownRegions

/**
 * YAML front matter: a block at the very start of the text between a `---` line and the next `---` or `...` line.
 * [de.markusressel.kodehighlighter.language.markdown.MarkdownRuleBook] highlights its content as YAML instead of
 * reporting this rule, and no Markdown rule matches inside of it.
 */
object FrontMatterRule : LanguageRule {

    override val tokenType = TokenType.META

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return MarkdownRegions.scanner.findMatches(text, this)
    }

}
