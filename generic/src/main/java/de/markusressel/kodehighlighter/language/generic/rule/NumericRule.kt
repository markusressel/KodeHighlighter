package de.markusressel.kodehighlighter.language.generic.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

class NumericRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    companion object {
        val PATTERN = "\\b(\\d+(\\.\\d+)?(e([+\\-])?\\d+)?([fd])?|0x[\\da-f]+)\\b".toRegex(RegexOption.IGNORE_CASE)
    }

}