package de.markusressel.kodehighlighter.language.css.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.css.CssSections

object ColorRule : LanguageRule {

    override val tokenType = TokenType.CONSTANT

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CssSections.findMatches(text, PATTERN, selectors = false)
    }

    private val PATTERN = "#(?:[\\da-fA-F]{8}|[\\da-fA-F]{6}|[\\da-fA-F]{3,4})(?![\\w-])".toRegex()

}
