package de.markusressel.kodehighlighter.language.kotlin.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object ModifierKeywordRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "\\b(?:abstract|actual|annotation|crossinline|enum|expect|external|final|infix|inline|inner|noinline|operator|out|override|reified|sealed|suspend|tailrec|value|vararg)(?=\\s+[a-zA-Z@(])".toRegex()

}
