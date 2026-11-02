package de.markusressel.kodehighlighter.language.rust.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object MacroRule : LanguageRule {

    override val tokenType = TokenType.FUNCTION

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b[a-zA-Z_]\\w*!(?!=)".toRegex()

}
