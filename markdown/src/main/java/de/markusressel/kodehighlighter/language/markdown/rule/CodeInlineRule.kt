package de.markusressel.kodehighlighter.language.markdown.rule

import de.markusressel.kodehighlighter.core.rule.LanguageRule
import de.markusressel.kodehighlighter.core.rule.RuleHelper
import de.markusressel.kodehighlighter.core.rule.RuleMatch

object CodeInlineRule : LanguageRule {

    override fun findMatches(text: CharSequence): List<RuleMatch> {
        return RuleHelper.findRegexMatches(text, PATTERN)
    }

    // TODO: This seems to be very inefficient, maybe there is a better way to detect such strings
    private val PATTERN = "(`{1,3})([^`]+?)\\1".toRegex()

}