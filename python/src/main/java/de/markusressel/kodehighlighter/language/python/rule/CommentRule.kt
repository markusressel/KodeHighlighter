package de.markusressel.kodehighlighter.language.python.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object CommentRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, SINGLE_LINE_PATTERN) +
            RuleHelper.findRegexMatches(text, MULTI_LINE_PATTERN)
    }

    private val MULTI_LINE_PATTERN = "('{3}|\"{3})[\\s\\S]*?\\1".toRegex(RegexOption.MULTILINE)
    private val SINGLE_LINE_PATTERN = "\\s*#.*".toRegex(RegexOption.MULTILINE)

}