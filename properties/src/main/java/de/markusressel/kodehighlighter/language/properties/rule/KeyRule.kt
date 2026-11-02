package de.markusressel.kodehighlighter.language.properties.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object KeyRule : LanguageRule {

    override val tokenType = TokenType.PROPERTY

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexGroupMatches(text, PATTERN, 1)
    }

    private val PATTERN = "(?m)^[ \\t]*+([^\\s#!=:](?:[ \\t]*+[^\\s=:])*+)[ \\t]*+[=:]".toRegex()

}
