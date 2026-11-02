package de.markusressel.kodehighlighter.language.xml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object AttributeValueRule : LanguageRule {

    override val tokenType = TokenType.STRING

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexGroupMatches(text, PATTERN, 1)
    }

    private val PATTERN = "=\\s*(\\\"[^\\\"]*\\\"|'[^']*')".toRegex()

}
