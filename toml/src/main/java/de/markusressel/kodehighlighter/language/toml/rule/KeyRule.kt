package de.markusressel.kodehighlighter.language.toml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object KeyRule : LanguageRule {

    override val tokenType = TokenType.PROPERTY

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexGroupMatches(text, PATTERN, 1)
    }

    private val PATTERN = "(?m)^[ \\t]*([A-Za-z0-9_.-]+)(?=[ \\t]*=)".toRegex()

}
