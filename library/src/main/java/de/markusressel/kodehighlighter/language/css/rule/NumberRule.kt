package de.markusressel.kodehighlighter.language.css.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType
import de.markusressel.kodehighlighter.language.css.CssSections

/**
 * Numbers with their unit, like `12px` or `50%`
 */
object NumberRule : LanguageRule {

    override val tokenType = TokenType.NUMBER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return CssSections.findMatches(text, PATTERN, selectors = false)
    }

    private val PATTERN = "(?<![\\w#.-])[+-]?(?:\\d+(?:\\.\\d+)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?:%|[A-Za-z]+)?".toRegex()

}
