package de.markusressel.kodehighlighter.language.cpp.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

object PreprocessorRule : LanguageRule {

    override val tokenType = TokenType.META

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?m)^[ \\t]*#[ \\t]*\\w+(?:[ \\t]*<[^>\\n]*>)?".toRegex()

}
