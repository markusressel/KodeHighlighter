package de.markusressel.kodehighlighter.language.java.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object NumberRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    private val PATTERN = "(?<![\\w.])(?:0[xX][0-9a-fA-F](?:_*[0-9a-fA-F])*(?:\\.[0-9a-fA-F_]*)?(?:[pP][+-]?\\d+)?[fFdDlL]?|0[bB][01](?:_*[01])*[lL]?|\\d(?:_*\\d)*(?:\\.(?:\\d(?:_*\\d)*)?)?(?:[eE][+-]?\\d(?:_*\\d)*)?[fFdDlL]?|\\.\\d(?:_*\\d)*(?:[eE][+-]?\\d+)?[fFdD]?)(?![\\w.])".toRegex()

}
