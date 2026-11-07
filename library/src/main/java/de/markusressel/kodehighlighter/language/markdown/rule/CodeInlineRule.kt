package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.markdown.MarkdownRegions

object CodeInlineRule : LanguageRule {

    override val tokenType = TokenType.CODE

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return MarkdownRegions.scanner.findMatches(text, this)
    }

}
