package de.markusressel.kodehighlighter.language.javascript.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object NumberRule : LanguageRule {

    override val tokenType = TokenType.NUMBER

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\w\$.])(?:0[xX][\\da-fA-F_]+n?|0[oO][0-7_]+n?|0[bB][01_]+n?|\\d[\\d_]*n|(?:\\d[\\d_]*(?:\\.[\\d_]*)?|\\.\\d[\\d_]*)(?:[eE][+-]?\\d[\\d_]*)?)(?![\\w\$])".toRegex()

}
