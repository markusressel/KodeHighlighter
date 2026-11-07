package de.markusressel.kodehighlighter.language.cpp.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object NumberRule : LanguageRule {

    override val tokenType = TokenType.NUMBER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\w.])(?:0[xX][0-9a-fA-F']+|0[bB][01']+|\\d[\\d']*(?:\\.[\\d']*)?(?:[eE][+-]?\\d+)?|\\.\\d[\\d']*(?:[eE][+-]?\\d+)?)[uUlLfFzZ]*(?![\\w.])".toRegex()

}
