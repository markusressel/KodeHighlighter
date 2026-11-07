package de.markusressel.kodehighlighter.language.properties.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object ValueRule : LanguageRule {

    override val tokenType = TokenType.STRING

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexGroupMatches(text, PATTERN, 1)
    }

    private val PATTERN = "(?m)^[ \\t]*[^\\s#!=:][^=:\\n]*[=:][ \\t]*([^\\n]+)".toRegex()

}
