package de.markusressel.kodehighlighter.language.ocaml.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

class StringRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    override fun getApplyingOrder(): Int {
        return 3
    }

    companion object {
        val PATTERN = "\"(\\\\\"|[^\"\\n])*\"".toRegex()
    }

}