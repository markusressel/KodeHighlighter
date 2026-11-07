package de.markusressel.kodehighlighter.language.csharp.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object NumberRule : LanguageRule {

    override val tokenType = TokenType.NUMBER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\w.])(?:0[xX][0-9a-fA-F_]+|0[oO][0-7_]+|0[bB][01_]+|\\d[\\d_]*(?:\\.\\d[\\d_]*)?(?:[eE][+-]?\\d[\\d_]*)?|\\.\\d[\\d_]*(?:[eE][+-]?\\d[\\d_]*)?)[uUlLfFdDmM]*(?![\\w.])".toRegex()

}
