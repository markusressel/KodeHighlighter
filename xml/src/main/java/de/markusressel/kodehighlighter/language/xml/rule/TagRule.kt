package de.markusressel.kodehighlighter.language.xml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object TagRule : LanguageRule {

    override val tokenType = TokenType.TAG

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "</?[A-Za-z_][\\w:.-]*|/?>|<\\?[A-Za-z_][\\w:.-]*|\\?>".toRegex()

}
