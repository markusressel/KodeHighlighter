package de.markusressel.kodehighlighter.language.csharp.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object TypeRule : LanguageRule {

    override val tokenType = TokenType.TYPE

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:bool|byte|sbyte|char|decimal|double|float|int|uint|long|ulong|short|ushort|string|object|void|nint|nuint|dynamic|var)\\b".toRegex()

}
