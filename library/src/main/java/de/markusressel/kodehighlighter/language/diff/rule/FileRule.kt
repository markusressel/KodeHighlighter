package de.markusressel.kodehighlighter.language.diff.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object FileRule : LanguageRule {

    override val tokenType = TokenType.HEADING

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?m)^(?:\\+\\+\\+ |--- |diff |index |\\*\\*\\* )[^\\n]*".toRegex()

}
