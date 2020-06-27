package de.markusressel.kodehighlighter.language.generic.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

class KeywordRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, FULL_PATTERN)
    }

    companion object {
        val PATTERN = "[=+.]".toRegex()
        val FULL_PATTERN = "(\\(|\\s|\\[|=|:|\\+|\\.|\\{|,)((['\"])([^\\\\1]|\\\\.)*?(\\3))".toRegex(RegexOption.MULTILINE)
    }

}