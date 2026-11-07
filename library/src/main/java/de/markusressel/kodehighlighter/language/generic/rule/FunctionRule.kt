package de.markusressel.kodehighlighter.language.generic.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch
import de.markusressel.kodehighlighter.core.rule.TokenType

/**
 * Names followed by `(`, like calls and declarations
 */
object FunctionRule : LanguageRule {

    override val tokenType = TokenType.FUNCTION

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\w\$])(?!(?:if|for|foreach|while|switch|catch|return|and|or|not|in|elif)\\b)[A-Za-z_\$][\\w\$]*(?=[ \\t]?\\()".toRegex()

}
